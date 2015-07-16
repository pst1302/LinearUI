package com.example.linearui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.nostra13.universalimageloader.core.ImageLoader;

public class MainActivity extends Activity implements OnClickListener, OnTouchListener{

	private LinearLayout dynamicLayout;

	ImageView profleImg;
	ScrollView sv;
	Bitmap bitmap = null;
	private int id = 0 ;
	back[] task = new back[100];
	Bitmap[] imgBitmap = new Bitmap[100];
	ImageView iv;
	int screenWidth;
	int screenHeight;
	int lastPoint = 0;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		dynamicLayout = (LinearLayout)findViewById(R.id.dynamic_area);
		
		sv = (ScrollView)findViewById(R.id.scrollView1);
		sv.setOnTouchListener(this);
		
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		
		screenWidth = metrics.widthPixels;
		screenHeight = metrics.heightPixels;
		
		ImageLoader imageLoader = ImageLoader.getInstance();
		ImageView iv = new ImageView(this);
		
		imageLoader.displayImage("http://media-cdn.tripadvisor.com/media/photo-s/03/9b/2d/f2/new-york-city.jpg", iv);
		
		LayoutInflater inflater =  (LayoutInflater) getSystemService(this.LAYOUT_INFLATER_SERVICE);
		
		LinearLayout ll = (LinearLayout)inflater.inflate(R.layout.added, null, false);
				
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
		
		ll.setLayoutParams(params);

		ll.addView(iv);
		
		
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		//Log.i("test", Integer.toString(v.getScrollY()));
		return false;
	}
	
	private class back extends AsyncTask<Object,Integer,ImageView> {

		@Override
		protected ImageView doInBackground(Object ...urls) {
			// TODO Auto-generated method stub
			
			try {
				URL url = new URL((String)urls[0]);
				
				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
				conn.setDoInput(true);
				
				conn.connect();
				
				InputStream is = conn.getInputStream();
				
				bitmap = BitmapFactory.decodeStream(is);
				
				conn.disconnect();
				
				((ImageView)urls[1]).setImageBitmap(bitmap);
				
			} catch(IOException e) {
				e.printStackTrace();
			}
			
			return (ImageView)urls[1];
		}
		
		@Override
		protected void onPostExecute(ImageView iv) {
			
		}
		
	}
	
}
