package com.myne.surveyapp;

import course.examples.touch.ViewTransitions.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class SplashActivity extends Activity {
	// splash screen variables
	protected static final int STOPSPLASH = 0;
	private static final long SPLASHTIME = 3000;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		
		new Handler().postDelayed(new Runnable(){ 
			
			@Override
			public void run(){ 
				Intent loginIntent = new Intent(SplashActivity.this, LoginActivity.class); 
				SplashActivity.this.startActivity(loginIntent); 
				SplashActivity.this.finish(); 
			}
		}, SPLASHTIME); 
		
	}
		
		

}
