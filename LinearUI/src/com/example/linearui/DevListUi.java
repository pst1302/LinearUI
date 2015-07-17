package com.example.linearui;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import android.content.Context;
import android.widget.AbsListView.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class DevListUi {
	
	// imageView관련된 선언
	private ImageView[] ivs = new ImageView[1000];
	
	// 현재 저장되고 레이아웃에 붙은 이미지 카운터
	private int imageViewCounter;

	private ImageLoader imageLoader;
	
	private Context context;

	private LinearLayout layout;
	
	// Constructor
	public DevListUi(Context context) {
		this.context = context;
		
		// 이미지로더 관련 초기화 
		initUiConfiguration();
		imageLoader = ImageLoader.getInstance();
		
		// ImageView 배열 초기화
		initImageView();
		
		initLayout();
		
		imageViewCounter = 0;
	}
	
	// 이미지뷰가 들어갈 레이아웃 생성
	private void initLayout() {
		
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		layout = (LinearLayout)new LinearLayout(context);
		layout.setLayoutParams(params);
		layout.setOrientation(LinearLayout.VERTICAL);
	
	}

	// 이미지 로더 configuration 초기화하고 적용
	private void initUiConfiguration() {
		
		// ImageLoader 환경 설정
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs()
				.build();
		
		// 환경 설정 저장하고 초기화
		ImageLoader.getInstance().init(config);
	}
	
	// 이미지뷰 1000개 초기화
	private void initImageView() {
		
		for(int i = 0; i < 1000; i++) {
			ivs[i] = new ImageView(context);
		}
	}
	
	// 레이아웃에 이미지 삽입
	public void inputImageByUrl(String url) {
		
		imageLoader.displayImage(url, ivs[imageViewCounter]);
		
		LayoutParams viewParams = new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.MATCH_PARENT);
		ivs[imageViewCounter].setLayoutParams(viewParams);		
		layout.addView(ivs[imageViewCounter]);
		
		imageViewCounter++;
	}
	
	// 레이아웃 반환
	public LinearLayout getLayout() {
		return layout;
	}
}
