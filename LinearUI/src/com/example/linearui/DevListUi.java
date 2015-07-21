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
	
	// imageView관련된 선언
	private ImageView[] ivs = new ImageView[1000];
	private ImageView textBgImage;
	
	// 단말기 높이 너비 
	private int deviceWidth;
	private int deviceHeight;
	
	// 현재 저장되고 레이아웃에 붙은 이미지 카운터
	private int imageViewCounter;

	private ImageLoader imageLoader;
	
	private Context context;

	private LinearLayout layout;
	private LinearLayout[] innerLayout = new LinearLayout[1000];
	
	// Constructor
	public DevListUi(Context context) {
		this.context = context;
		
		// 이미지로더 관련 초기화 
		initUiConfiguration();
		imageLoader = ImageLoader.getInstance();
		
		// ImageView 배열 초기화
		initImageView();
		
		// 레이아웃 초기화 텍스트 이미지 배경화면 초기화
		initLayout();
		
		// 디바이스 width/height 로딩
		loadDeviceInfo();
		
		imageViewCounter = 0;
	}
	
	private void loadDeviceInfo() {
		
		DisplayMetrics dm = new DisplayMetrics();
		
		((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		
		deviceWidth = dm.widthPixels;
		deviceHeight = dm.heightPixels;
		
	}
	
	// 이미지뷰가 들어갈 레이아웃 생성
	private void initLayout() {
		
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		layout = (LinearLayout)new LinearLayout(context);
		layout.setLayoutParams(params);
		layout.setOrientation(LinearLayout.VERTICAL);
		//layout.setBackgroundColor();
	
	}

	// 이미지 로더 configuration 초기화하고 적용
	private void initUiConfiguration() {
		
		// DisplayImageOption 설정
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheInMemory(true)	// 메모리 캐시 사용
				.cacheOnDisc(true)		// 디스크 캐시 사용
				.build();
		
		// ImageLoader 환경 설정
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.defaultDisplayImageOptions(defaultOptions)		// 옵션 적용
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs()
				.build();
				
		// 환경 설정 저장하고 초기화
		ImageLoader.getInstance().init(config);
	}
	
	// 이미지뷰 1000개 초기화
	private void initImageView() {
		
		for(int i = 0; i < 1000; i++) {
			innerLayout[i] = new LinearLayout(context);
			ivs[i] = new ImageView(context);
		}
	}
	
	// 레이아웃에 이미지 삽입
	public void inputProfile(String url,String name,String status) {
		
		imageLoader.displayImage(url, ivs[imageViewCounter]);
		
		LayoutParams viewParams = new LayoutParams(deviceWidth/5,LayoutParams.MATCH_PARENT);
		ivs[imageViewCounter].setLayoutParams(viewParams);
		
		// 
		LayoutParams innerParams = new LayoutParams(LayoutParams.MATCH_PARENT,deviceHeight/11);
		innerLayout[imageViewCounter].setLayoutParams(innerParams);
		innerLayout[imageViewCounter].setOrientation(LinearLayout.HORIZONTAL);
		innerLayout[imageViewCounter].addView(ivs[imageViewCounter]);
		
		// 사용자 이름 설정
		TextView nameTextView = new  TextView(context);
		nameTextView.setLayoutParams(new LayoutParams(deviceWidth/5,LayoutParams.MATCH_PARENT));
		nameTextView.setText(name);
		nameTextView.setGravity(Gravity.CENTER_VERTICAL);
		nameTextView.setTextSize(16);
		innerLayout[imageViewCounter].addView(nameTextView);
		
		// 상태 메세지 처리부분
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
	
	// 메모리and디스크 캐시 clear 할 때 사용
	public void clearCache() {
		imageLoader.clearMemoryCache();
	}
	
	// 레이아웃 반환
	public LinearLayout getLayout() {
		return layout;
	}
}
