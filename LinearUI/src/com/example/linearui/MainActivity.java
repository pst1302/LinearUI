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

public class MainActivity extends Activity implements OnClickListener, OnTouchListener{

	private LinearLayout dynamicLayout;

	ImageView profleImg;
	ScrollView sv;
	Bitmap bitmap = null;
	private int id = 0 ;
	back[] task = new back[100];
	Bitmap[] imgBitmap = new Bitmap[100];
	ImageView[] iv = new ImageView[100];
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
		
		drawer(sv.getScrollY());
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		//Log.i("test", Integer.toString(v.getScrollY()));
		drawer(sv.getScrollY());
		return false;
	}
	
	public void drawer(int currentPoint) {
		for(int i = currentPoint ; i < currentPoint + 20 ; i++) {
			
			task[i] = new back();
			iv[i] = new ImageView(this);
			
			task[i].executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"http://media-cdn.tripadvisor.com/media/photo-s/03/9b/2d/f2/new-york-city.jpg",iv[i]);
			
			lastPoint += (screenHeight/10);
			Log.i("lastPoint", Integer.toString(lastPoint));
			float lastValue = sv.getScaleY() + (screenHeight * 2);
			Log.i("sv.getScaleY() + (screenHeight * 2)", Float.toString(lastValue));
		}
	}
	
	
	public void pushBtn(ImageView iv) {
		
		// Inflater 생성 후 Inflater로 LinearLayout 생성 
		LayoutInflater inflater = (LayoutInflater)getSystemService(this.LAYOUT_INFLATER_SERVICE);
		LinearLayout ll = (LinearLayout)inflater.inflate(R.layout.added, null, false);
		
		// 레이아웃 속성 정의
		LayoutParams profleParams = new LayoutParams(screenWidth/4,screenHeight/10);
		
		// 이미지 뷰와 속성 연결
		iv.setLayoutParams(profleParams);
		
		// 레이아웃에 이미지 추가
		ll.addView(iv);

		ll.setOrientation(LinearLayout.HORIZONTAL);
		
		
		dynamicLayout.addView(ll,new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
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
				// 변경
				
				((ImageView)urls[1]).setImageBitmap(bitmap);
				
			} catch(IOException e) {
				e.printStackTrace();
			}
			
			return (ImageView)urls[1];
		}
		
		@Override
		protected void onPostExecute(ImageView iv) {
			pushBtn(iv);
		}
		
	}
	
}
