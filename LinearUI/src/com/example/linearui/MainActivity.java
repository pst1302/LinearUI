package com.example.linearui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

	private LinearLayout dynamicLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		dynamicLayout = (LinearLayout)findViewById(R.id.dynamic_area);
		
		DevListUi devUi = new DevListUi(this);
		
		for(int i = 0; i < 100; i++) {
			devUi.inputImageByUrl("http://placehold.it/120x120&text=image" + (i+1));
		}
		
		dynamicLayout.addView(devUi.getLayout());
		
	}
}
