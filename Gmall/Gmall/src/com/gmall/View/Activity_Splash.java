package com.gmall.View;

import com.example.gmall.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
/*
 * Æô¶¯Ò³
 */
public class Activity_Splash extends Activity {
	Intent i;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		i=new Intent(this,LoginActivity.class);
		
		
		Thread t=new Thread() {
			@Override
			public void run() {
				try {
					Thread.sleep(2500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Activity_Splash.this.startActivity(i);
				Activity_Splash.this.finish();
			};
		};
		t.start();
	}
}
