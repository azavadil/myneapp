package com.myne.surveyapp;

import java.util.ArrayList;

import course.examples.touch.ViewTransitions.R;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import android.view.View.OnClickListener;



// uses the ViewFlipper class to handle the animations

public class MyneSurveyActivity extends Activity implements
		SeekBar.OnSeekBarChangeListener {
	
	
	
	private ViewFlipper mFlipper;
	private TextView mTextView1, mTextView2, mTextViewSeekBar1,
			mTextViewSeekBar2;
	private int mCurrentLayoutState;
	private int mCount;  //default value is 0
	private GestureDetector mGestureDetector;
	private ArrayList<Question> mSurvey;
	private int[] mSurveyAnswers;
	private boolean[] mCompleted;  //default value is false 
	private SeekBar mSeekBar1, mSeekBar2;
	private SeekBar mCurSeekBar;
	private TextView mCurSeekBarDisplay;
	private int mSurveySize; 
	
	
	private static final String APP_TAG = "Myne-App"; 
	public static final String QUESTIONS_FILE_NAME = "questions.txt";
	public static final String ANSWERS_FILE_NAME = "answers_log.txt";
	
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		

        
		createSurvey();
		mCurrentLayoutState = 0;
	
		mFlipper = (ViewFlipper) findViewById(R.id.view_flipper);
		mTextView1 = (TextView) findViewById(R.id.textView1);
		mTextView2 = (TextView) findViewById(R.id.textView2);
		mTextViewSeekBar1 = (TextView) findViewById(R.id.textView1a);
		mTextViewSeekBar2 = (TextView) findViewById(R.id.textView2a);
		mSeekBar1 = (SeekBar) findViewById(R.id.seekBar1);
		mSeekBar2 = (SeekBar) findViewById(R.id.seekBar2);

		// set the default values for the SeekBar
		// Get the the maximum value for the first question
		// Set the progress to be the midpoint of the range
		mSeekBar1.setMax(mSurvey.get(mCount).getMax());
		mSeekBar1.setProgress(mSurvey.get(mCount).getMax()/2);
		
	
		mSeekBar1.setOnSeekBarChangeListener(this);
		mSeekBar2.setOnSeekBarChangeListener(this	); 
		mCurSeekBar = mSeekBar1;
		mCurSeekBarDisplay = mTextViewSeekBar1; 
		

		mTextView1.setText(mSurvey.get(mCount).getMsg());
		mTextViewSeekBar1.setText(String.valueOf(mSeekBar1.getProgress()));

		// create a new GestureDetector
		// in the constructor the code passes in a
		// GestureDetector.SimpleOnGestureListener() (the "SOGL")
		// the SOGL defines an onFling() method.
		// when a GestureDetector detects a fling, onFling() is called
		mGestureDetector = new GestureDetector(this, 
				new GestureDetector.SimpleOnGestureListener() {
					@Override
					public boolean onFling(MotionEvent e1, MotionEvent e2,
							float velocityX, float velocityY) {
						
						Log.d(APP_TAG,"onFling entered, mCount: " + Integer.toString(mCount)); 
						Log.d(APP_TAG, "onFling " + Float.toString(velocityX)); 
						// we check whether we have a valid left to right fling
						// if we do then we start the animations

						if (velocityX < -10.0f) {

							// this is just a toggle that flips mCurrentLayoutState
							// we just use a pool of 2 views that we update
							
							if( mCount < mSurvey.size() - 1 ) { 
								mCurrentLayoutState = mCurrentLayoutState == 0 ? 1 : 0;
								advanceLayoutStateTo(mCurrentLayoutState);
							} else { 
								Toast.makeText(getBaseContext(), "End of Survey", Toast.LENGTH_LONG).show(); 
								Log.d(APP_TAG, resultsToString()); 
							}
							
						} else if (velocityX  > 10.0f) {
							
							if( mCount > 0) {
								mCurrentLayoutState = mCurrentLayoutState == 0 ? 1 : 0;
								backupLayoutStateTo(mCurrentLayoutState);
							} else { 
								Toast.makeText(getBaseContext(), "Start of survey", Toast.LENGTH_LONG).show(); 
							}
						}

						// if the velocity doesn't meet our velocity criteria
						// then the fling gesture is ignored
						return true;
					}

					@Override
					public boolean onDoubleTap(MotionEvent e) {
						// read the value of the seek bar
						// store to answers
						Log.d(APP_TAG, "onDoubleTap entered"); 

   						int seekBarVal = 0;
						seekBarVal = mCurSeekBar.getProgress();
						mSurveyAnswers[mCount] = seekBarVal;
						mCompleted[mCount] = true;
						
						Toast.makeText(getApplicationContext(), "Value entered: " + Integer.toString(seekBarVal), Toast.LENGTH_LONG	).show(); 

						return true;
					}
					
					@Override
					public boolean onDown(MotionEvent e){ 
						return true; 
					}
				});
		
	}

	// create survey
	private void createSurvey() {
		Log.d(APP_TAG, "createSurvey entered"); 
		

	   

		// This is where we want to make an http request.
		
		SurveyPopulator sp = new SurveyPopulator(); 
	
		
		mSurveySize = 5; 
		mSurvey = new ArrayList<Question>(mSurveySize); 
		
		// here we're faking receiving data from a server
		mSurvey.add(new Question("0. What's your energy today?", 100));
		mSurvey.add( new Question("1. How many hours did you sleep last night", 20));
		mSurvey.add( new Question("2. How many glasses of water did you drink yesterday", 20));
		mSurvey.add(new Question( "3. How optimistic do you feel",100));
		mSurvey.add( new Question("4. How many ounces of alcohol did you drink yesterday", 50));
		
		Log.d(APP_TAG, "Survey size: " + Integer.toString(mSurveySize)); 
		
		mSurveyAnswers = new int[mSurveySize];
		// boolean java arrays default to false
		mCompleted = new boolean[mSurveySize];
		
	}

	private void setSeekBarDisplay(TextView tv){ 
		assert(mCount < mSurveySize); 
		
		if( mCompleted[mCount]){ 
			tv.setText(Integer.toString(mSurveyAnswers[mCount])); 
		} else { 
			// This is the first time the user has seen the question
			//tv.setText("50");
			tv.setText( Integer.toString(mSurvey.get(mCount).getMax()/2 ) ) ; 
		}
	}
	
	private void setSeekBarProgress(SeekBar sb){ 
		assert(mCount < mSurveySize); 
		
		if( mCompleted[mCount]){ 
			sb.setProgress(mSurveyAnswers[mCount]); 
		} else { 
			// This is the first time the user has seen the question
			sb.setProgress( mSurvey.get(mCount).getMax()/2 )  ; 
		}
	}
	
	// the onTouchEvent() (the "OTE") method for the Activity gets called
	// when a touch event occurs and no view handles it
	// when the OTE is called it will simply delegate the call to the
	// GestureDetector.
	// if the GestureDetector determines that its has seen a complete fling
	// gesture then the onFling method above gets called

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return mGestureDetector.onTouchEvent(event);
	}

	public void advanceLayoutStateTo(int switchTo) {
		mCurrentLayoutState = switchTo;

		mFlipper.setInAnimation(inFromRightAnimation());
		mFlipper.setOutAnimation(outToLeftAnimation());

		mCount++;

		if (switchTo == 0) {
			mTextView1.setText(mSurvey.get(mCount).getMsg());
			mSeekBar1.setMax(mSurvey.get(mCount).getMax());
		    setSeekBarProgress(mSeekBar1); 
			mCurSeekBar = mSeekBar1;
			setSeekBarDisplay(mTextViewSeekBar1); 
			mCurSeekBarDisplay = mTextViewSeekBar1;
		} else {
			mTextView2.setText(mSurvey.get(mCount).getMsg());
			mSeekBar2.setMax(mSurvey.get(mCount).getMax());
			setSeekBarProgress(mSeekBar2); 
			mCurSeekBar = mSeekBar2;
			setSeekBarDisplay(mTextViewSeekBar2); 
			mCurSeekBarDisplay = mTextViewSeekBar2;
		}

		mFlipper.showPrevious();
	}

	public void backupLayoutStateTo(int switchTo) {
		mCurrentLayoutState = switchTo;

		mFlipper.setInAnimation(inFromLeftAnimation());
		mFlipper.setOutAnimation(outToRightAnimation());

		mCount--;

		if (switchTo == 0) {
			mTextView1.setText(mSurvey.get(mCount).getMsg());
			mSeekBar1.setMax(mSurvey.get(mCount).getMax());
			setSeekBarProgress(mSeekBar1); 
			mCurSeekBar = mSeekBar1;
			setSeekBarDisplay(mTextViewSeekBar1); 
			mCurSeekBarDisplay = mTextViewSeekBar1;
		} else {
			mTextView2.setText(mSurvey.get(mCount).getMsg());
			mSeekBar2.setMax(mSurvey.get(mCount).getMax());
			setSeekBarProgress(mSeekBar2); 
			mCurSeekBar = mSeekBar2;
			setSeekBarDisplay(mTextViewSeekBar2); 
			mCurSeekBarDisplay = mTextViewSeekBar2;
		}

		mFlipper.showPrevious();
	}

	public void reverseLayoutStateTo(int switchTo) {
		mCurrentLayoutState = switchTo;

		mFlipper.setInAnimation(inFromRightAnimation());
		mFlipper.setOutAnimation(outToLeftAnimation());

		mCount++;

		if (switchTo == 0) {
			mTextView1.setText(String.valueOf(mCount));
		} else {
			mTextView2.setText(String.valueOf(mCount));
		}

		mFlipper.showPrevious();
	}

	private Animation inFromRightAnimation() {
		Animation inFromRight = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, +1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		inFromRight.setDuration(500);
		inFromRight.setInterpolator(new LinearInterpolator());
		return inFromRight;
	}

	private Animation outToLeftAnimation() {
		Animation outtoLeft = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, -1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		outtoLeft.setDuration(500);
		outtoLeft.setInterpolator(new LinearInterpolator());
		return outtoLeft;
	}

	private Animation inFromLeftAnimation() {
		Animation inFromLeft = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, -1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		inFromLeft.setDuration(500);
		inFromLeft.setInterpolator(new LinearInterpolator());
		return inFromLeft;
	}

	private Animation outToRightAnimation() {
		Animation outtoRight = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, +1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		outtoRight.setDuration(500);
		outtoRight.setInterpolator(new LinearInterpolator());
		return outtoRight;
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromTouch) {
		mCurSeekBarDisplay.setText(Integer.toString(progress));
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// Stub
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// Stub
	}
	
	private String resultsToString(){
		String res = ""; 
		for(int i = 0; i < mSurveySize; i++){ 
			res += Integer.toString(mSurveyAnswers[i]) + " | " + Boolean.toString(mCompleted[i] ) + ", "; 
		} 
		return res; 
	}
	
	
};     
	
	
