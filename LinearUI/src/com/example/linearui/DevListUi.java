package com.example.linearui;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import android.content.Context;
import android.widget.AbsListView.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class DevListUi {
	
	// imageView���õ� ����
	private ImageView[] ivs = new ImageView[1000];
	
	// ���� ����ǰ� ���̾ƿ��� ���� �̹��� ī����
	private int imageViewCounter;

	private ImageLoader imageLoader;
	
	private Context context;

	private LinearLayout layout;
	
	// Constructor
	public DevListUi(Context context) {
		this.context = context;
		
		// �̹����δ� ���� �ʱ�ȭ 
		initUiConfiguration();
		imageLoader = ImageLoader.getInstance();
		
		// ImageView �迭 �ʱ�ȭ
		initImageView();
		
		initLayout();
		
		imageViewCounter = 0;
	}
	
	// �̹����䰡 �� ���̾ƿ� ����
	private void initLayout() {
		
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		layout = (LinearLayout)new LinearLayout(context);
		layout.setLayoutParams(params);
		layout.setOrientation(LinearLayout.VERTICAL);
	
	}

	// �̹��� �δ� configuration �ʱ�ȭ�ϰ� ����
	private void initUiConfiguration() {
		
		// ImageLoader ȯ�� ����
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs()
				.build();
		
		// ȯ�� ���� �����ϰ� �ʱ�ȭ
		ImageLoader.getInstance().init(config);
	}
	
	// �̹����� 1000�� �ʱ�ȭ
	private void initImageView() {
		
		for(int i = 0; i < 1000; i++) {
			ivs[i] = new ImageView(context);
		}
	}
	
	// ���̾ƿ��� �̹��� ����
	public void inputImageByUrl(String url) {
		
		imageLoader.displayImage(url, ivs[imageViewCounter]);
		
		LayoutParams viewParams = new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.MATCH_PARENT);
		ivs[imageViewCounter].setLayoutParams(viewParams);		
		layout.addView(ivs[imageViewCounter]);
		
		imageViewCounter++;
	}
	
	// ���̾ƿ� ��ȯ
	public LinearLayout getLayout() {
		return layout;
	}
}
