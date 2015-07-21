package com.example.linearui;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import android.content.Context;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DevListUi {
	
	// imageView���õ� ����
	private ImageView[] ivs = new ImageView[1000];
	private ImageView textBgImage;
	
	// �ܸ��� ���� �ʺ� 
	private int deviceWidth;
	private int deviceHeight;
	
	// ���� ����ǰ� ���̾ƿ��� ���� �̹��� ī����
	private int imageViewCounter;

	private ImageLoader imageLoader;
	
	private Context context;

	private LinearLayout layout;
	private LinearLayout[] innerLayout = new LinearLayout[1000];
	
	// Constructor
	public DevListUi(Context context) {
		this.context = context;
		
		// �̹����δ� ���� �ʱ�ȭ 
		initUiConfiguration();
		imageLoader = ImageLoader.getInstance();
		
		// ImageView �迭 �ʱ�ȭ
		initImageView();
		
		// ���̾ƿ� �ʱ�ȭ �ؽ�Ʈ �̹��� ���ȭ�� �ʱ�ȭ
		initLayout();
		
		// ����̽� width/height �ε�
		loadDeviceInfo();
		
		imageViewCounter = 0;
	}
	
	private void loadDeviceInfo() {
		
		DisplayMetrics dm = new DisplayMetrics();
		
		((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		
		deviceWidth = dm.widthPixels;
		deviceHeight = dm.heightPixels;
		
	}
	
	// �̹����䰡 �� ���̾ƿ� ����
	private void initLayout() {
		
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		layout = (LinearLayout)new LinearLayout(context);
		layout.setLayoutParams(params);
		layout.setOrientation(LinearLayout.VERTICAL);
		//layout.setBackgroundColor();
	
	}

	// �̹��� �δ� configuration �ʱ�ȭ�ϰ� ����
	private void initUiConfiguration() {
		
		// DisplayImageOption ����
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheInMemory(true)	// �޸� ĳ�� ���
				.cacheOnDisc(true)		// ��ũ ĳ�� ���
				.build();
		
		// ImageLoader ȯ�� ����
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.defaultDisplayImageOptions(defaultOptions)		// �ɼ� ����
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs()
				.build();
				
		// ȯ�� ���� �����ϰ� �ʱ�ȭ
		ImageLoader.getInstance().init(config);
	}
	
	// �̹����� 1000�� �ʱ�ȭ
	private void initImageView() {
		
		for(int i = 0; i < 1000; i++) {
			innerLayout[i] = new LinearLayout(context);
			ivs[i] = new ImageView(context);
		}
	}
	
	// ���̾ƿ��� �̹��� ����
	public void inputProfile(String url,String name,String status) {
		
		imageLoader.displayImage(url, ivs[imageViewCounter]);
		
		LayoutParams viewParams = new LayoutParams(deviceWidth/5,LayoutParams.MATCH_PARENT);
		ivs[imageViewCounter].setLayoutParams(viewParams);
		
		// 
		LayoutParams innerParams = new LayoutParams(LayoutParams.MATCH_PARENT,deviceHeight/11);
		innerLayout[imageViewCounter].setLayoutParams(innerParams);
		innerLayout[imageViewCounter].setOrientation(LinearLayout.HORIZONTAL);
		innerLayout[imageViewCounter].addView(ivs[imageViewCounter]);
		
		// ����� �̸� ����
		TextView nameTextView = new  TextView(context);
		nameTextView.setLayoutParams(new LayoutParams(deviceWidth/5,LayoutParams.MATCH_PARENT));
		nameTextView.setText(name);
		nameTextView.setGravity(Gravity.CENTER_VERTICAL);
		nameTextView.setTextSize(16);
		innerLayout[imageViewCounter].addView(nameTextView);
		
		// ���� �޼��� ó���κ�
		TextView statusMsg = new TextView(context);
		statusMsg.setLayoutParams(new LayoutParams(deviceWidth/5*3,LayoutParams.MATCH_PARENT));
		statusMsg.setText("Hello World!!!!!!!!!!");
		statusMsg.setGravity(Gravity.RIGHT);
		statusMsg.setBackgroundResource(R.drawable.text_balloon);
		statusMsg.setTextSize(12);
		innerLayout[imageViewCounter].addView(statusMsg);
		innerLayout[imageViewCounter].setPadding(0, 2, 0, 2);
		//innerLayout[imageViewCounter].setGravity(Gravity.CENTER);
		//innerLayout[imageViewCounter].setBackgroundDrawable((Drawable)context.getResources().getDrawable(R.drawable.profile_background));
		layout.addView(innerLayout[imageViewCounter]);
		
		imageViewCounter++;
	}
	
	// �޸�and��ũ ĳ�� clear �� �� ���
	public void clearCache() {
		imageLoader.clearMemoryCache();
	}
	
	// ���̾ƿ� ��ȯ
	public LinearLayout getLayout() {
		return layout;
	}
}
