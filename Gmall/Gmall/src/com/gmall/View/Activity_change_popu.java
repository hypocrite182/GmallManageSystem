package com.gmall.View;


import com.example.gmall.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
/*
 * ÇÐ»»Í·ÏñÒ³Ãæ
 */
public class Activity_change_popu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_change_popu);
		TextView t=(TextView)findViewById(R.id.photo_take);
	}
}
