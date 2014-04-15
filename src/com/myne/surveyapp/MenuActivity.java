package com.myne.surveyapp;

import course.examples.touch.ViewTransitions.R;
import course.examples.touch.ViewTransitions.R.layout;
import course.examples.touch.ViewTransitions.R.menu;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MenuActivity extends Activity {

	private final static String APP_TAG = "Myne-App"; 
	private Button mSurveyButton; 
	private RelativeLayout mRelativeLayout;  
	private TextView mGraphicLabel; 
	private ImageView mProgressGraphic; 
	private Bitmap mBitmap; 
	
	private int mHeight = 0; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		Log.d(APP_TAG, "MenuActivity onCreate entered"); 
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		
		// get a handle to the main layout
		mRelativeLayout = (RelativeLayout)findViewById(R.id.menu_activity); 
		
		// get a handle to the survey button
		mSurveyButton =(Button) getLayoutInflater().inflate(R.layout.survey_button, mRelativeLayout, false); 
		mSurveyButton.setId(1); 
		
		// get a handle to the TextView we're using as a label 
		mGraphicLabel = (TextView)getLayoutInflater().inflate(R.layout.graphic_label, mRelativeLayout,false); 
		mGraphicLabel.setId(2); 
	    
		// read in the bitmap
		Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.progress0); 
		
		// get a handle to the ImageView we'll use to display the progress graphic
		mProgressGraphic = (ImageView)getLayoutInflater().inflate(R.layout.progress_graphic, mRelativeLayout, false); 
		mProgressGraphic.setImageBitmap(mBitmap); 
		mProgressGraphic.setId(3); 
		
				
		
		mSurveyButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d(APP_TAG, "button clicked"); 
				// TODO Auto-generated method stub
				try { 
					Intent surveyActivity = new Intent(MenuActivity.this, MyneSurveyActivity.class); 
					startActivity(surveyActivity); 	
				} catch (Exception e){ 
					Log.d(APP_TAG, e.toString()); 
				}
			}
		}); 
		
		
		mRelativeLayout.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener(){ 
					
					@SuppressLint("NewApi")
					@SuppressWarnings("deprecation")
					@Override
					public void onGlobalLayout(){ 
						
						mHeight = mRelativeLayout.getHeight(); 
						mSurveyButton.getLayoutParams().height = mHeight/3; 
						mSurveyButton.getLayoutParams().width = mHeight/3;  
					    
						// set the label to be below the button
						RelativeLayout.LayoutParams labelLp = new RelativeLayout.LayoutParams(mHeight/3, mHeight/10); 
						labelLp.addRule(RelativeLayout.BELOW, mSurveyButton.getId()); 
						labelLp.addRule(RelativeLayout.CENTER_HORIZONTAL);
						mGraphicLabel.setText("Survey"); 
						
						// set the progress graphic to be below the graphic label 
						RelativeLayout.LayoutParams graphicLp = new RelativeLayout.LayoutParams(mHeight/4, mHeight/10); 
						graphicLp.addRule(RelativeLayout.BELOW, mGraphicLabel.getId()); 
						graphicLp.addRule(RelativeLayout.CENTER_HORIZONTAL);
						
						mRelativeLayout.addView(mSurveyButton);
						mRelativeLayout.addView(mGraphicLabel, labelLp); 
						mRelativeLayout.addView(mProgressGraphic, graphicLp);
						
						
						if( android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) { 
							mRelativeLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this); 
						} else{ 
							mRelativeLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this); 
						}
					}
				}); 
	
				
		 
		
		
	}
	
	@Override
	public void onResume(){ 
		super.onResume();
		Log.d(APP_TAG, "onResume | linearLayout" + mHeight);
		
	}

	

}
