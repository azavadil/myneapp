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
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import android.view.View.OnClickListener;



// uses the ViewFlipper class to handle the animations

public class MyneSurveyActivity extends Activity implements
		SeekBar.OnSeekBarChangeListener {
	
	
	
	private static final String NUM_QUESTIONS = "numQuestions";
	private static final String ANSWER_LIST = "AnswerList";
	private static final String QUESTION_LIST = "QuestionList";
	private ViewFlipper mFlipper;
	private TextView mTextView1, mTextView2, mTextViewSeekBar1,
			mTextViewSeekBar2, mTextViewHeader1, mTextViewHeader2;
	private int mCurrentLayoutState;
	private int mCount;  //default value is 0
	private GestureDetector mGestureDetector;
	private MyneSurvey mSurvey;
	private SeekBar mSeekBar1, mSeekBar2, mCurSeekBar;
	private RatingBar mRatingBar1, mRatingBar2, mCurRatingBar; 
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
		mTextViewHeader1 = (TextView)findViewById(R.id.textViewHeader1); 
		mTextViewHeader2 = (TextView)findViewById(R.id.textViewHeader2); 
		
		mTextViewSeekBar1 = (TextView) findViewById(R.id.textView1a);
		mTextViewSeekBar2 = (TextView) findViewById(R.id.textView2a);
		mSeekBar1 = (SeekBar) findViewById(R.id.seekBar1);
		mSeekBar2 = (SeekBar) findViewById(R.id.seekBar2);
		
		mRatingBar1 = (RatingBar)findViewById(R.id.rating_bar_1); 
		mRatingBar2 = (RatingBar)findViewById(R.id.rating_bar_2); 
		mCurRatingBar = mRatingBar1;
		mCurRatingBar.setMax(mSurvey.getQ(mCount).getCategorySize()); 
		mCurRatingBar.setNumStars(mSurvey.getQ(mCount).getCategorySize());

		// set the default values for the SeekBar
		// Get the the maximum value for the first question
		// Set the progress to be the midpoint of the range
		mSeekBar1.setMax(mSurvey.getQ(mCount).getMax());
		mSeekBar1.setProgress(mSurvey.getQ(mCount).getMax()/2);
		
	
		mSeekBar1.setOnSeekBarChangeListener(this);
		mSeekBar2.setOnSeekBarChangeListener(this	); 
		mCurSeekBar = mSeekBar1;
		mCurSeekBarDisplay = mTextViewSeekBar1; 
		 

		mTextView1.setText(mSurvey.getQ(mCount).getMsg());
		mTextViewSeekBar1.setText(String.valueOf(mSeekBar1.getProgress()));
		mTextViewHeader1.setText(mSurvey.getHeader(mCount)); 
		
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
							if( !mSurvey.getQ(mCount).getCompleted()) { 
								Toast.makeText(getApplicationContext(), 
										"Double tap to enter value before advancing" ,
										Toast.LENGTH_SHORT).show(); 
							} else { 
							
								if( mCount < mSurvey.size() - 1 ) { 
									mCurrentLayoutState = mCurrentLayoutState == 0 ? 1 : 0;
									advanceLayoutStateTo(mCurrentLayoutState);
								} else { 
									Toast.makeText(getBaseContext(), "End of Survey", Toast.LENGTH_SHORT).show(); 
									Log.d(APP_TAG, resultsToString()); 
									Intent summaryActivity = new Intent(getApplicationContext(), SummaryActivity.class);
									Bundle cur = new Bundle(2); 
									cur.putStringArray(QUESTION_LIST, mSurvey.getQuestionArray());
									cur.putIntArray(ANSWER_LIST, mSurvey.getAnswerArray()); 
									cur.putInt(NUM_QUESTIONS, mSurvey.size()); 
									summaryActivity.putExtra("summaryList", cur); 
									//startActivity(summaryActivity); 
								}
							}
							
						} else if (velocityX  > 10.0f) {
							
							if( mCount > 0) {
								mCurrentLayoutState = mCurrentLayoutState == 0 ? 1 : 0;
								backupLayoutStateTo(mCurrentLayoutState);
							} else { 
								Toast.makeText(getBaseContext(), "Start of survey", Toast.LENGTH_SHORT).show(); 
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
						mSurvey.getQ(mCount).setAnswer(seekBarVal);
						if( !mSurvey.getQ(mCount).getCompleted()){ 
							mSurvey.getQ(mCount).setCompleted(true);
							mSurvey.incrNumCompleted(mCount); 
						}
						mCurRatingBar.setRating( mSurvey.getNumCompleted(mCount)); 
						
						Toast.makeText(getApplicationContext(), "Value entered: " + Integer.toString(seekBarVal), Toast.LENGTH_SHORT	).show(); 

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
		
		SurveyBuilder sp = new SurveyBuilder(); 
	
		
		mSurveySize = 5; 
		mSurvey = new MyneSurvey(sp.getSurvey()); 
		
		
		
		Log.d(APP_TAG, "Survey size: " + Integer.toString(mSurvey.size())); 
		
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
			mTextView1.setText(mSurvey.getQ(mCount).getMsg());
			mTextViewHeader1.setText(mSurvey.getHeader(mCount));
			mSeekBar1.setMax(mSurvey.getQ(mCount).getMax());
			mCurSeekBar = mSeekBar1;
			mCurSeekBarDisplay = mTextViewSeekBar1;
			mCurRatingBar = mRatingBar1; 
		} else {
			mTextView2.setText(mSurvey.getQ(mCount).getMsg());
			mTextViewHeader2.setText(mSurvey.getHeader(mCount)); 
			mSeekBar2.setMax(mSurvey.getQ(mCount).getMax());
			mCurSeekBar = mSeekBar2;
			mCurSeekBarDisplay = mTextViewSeekBar2;
			mCurRatingBar = mRatingBar2; 
		}
		setSeekBarDisplay(mCurSeekBarDisplay);
		setSeekBarProgress(mCurSeekBar); 
		setRatingBarStars(mCurRatingBar); 

		mFlipper.showPrevious();
	}

	public void backupLayoutStateTo(int switchTo) {
		mCurrentLayoutState = switchTo;

		mFlipper.setInAnimation(inFromLeftAnimation());
		mFlipper.setOutAnimation(outToRightAnimation());

		mCount--;

		if (switchTo == 0) {
			mTextView1.setText(mSurvey.getQ(mCount).getMsg());
			mTextViewHeader1.setText(mSurvey.getHeader(mCount));
			mSeekBar1.setMax(mSurvey.getQ(mCount).getMax());
			mCurSeekBar = mSeekBar1;
			mCurSeekBarDisplay = mTextViewSeekBar1;
			mCurRatingBar = mRatingBar1; 
		} else {
			mTextView2.setText(mSurvey.getQ(mCount).getMsg());
			mTextViewHeader2.setText(mSurvey.getHeader(mCount));
			mSeekBar2.setMax(mSurvey.getQ(mCount).getMax());
			mCurSeekBar = mSeekBar2;
			mCurSeekBarDisplay = mTextViewSeekBar2;
			mCurRatingBar = mRatingBar2; 
		}

		setSeekBarDisplay(mCurSeekBarDisplay); 
		setSeekBarProgress(mCurSeekBar);
		setRatingBarStars(mCurRatingBar); 
		
		mFlipper.showPrevious();
	}

	private void setSeekBarDisplay(TextView tv){ 
		assert(mCount < mSurvey.size()); 
		
		if( mSurvey.getQ(mCount).getCompleted() ){ 
			tv.setText(Integer.toString(mSurvey.getQ(mCount).getAnswer())); 
		} else { 
			// This is the first time the user has seen the question
			//tv.setText("50");
			tv.setText( Integer.toString(mSurvey.getQ(mCount).getMax()/2 ) ) ; 
		}
	}
	
	private void setSeekBarProgress(SeekBar sb){ 
		assert(mCount < mSurveySize); 
		
		if( mSurvey.getQ(mCount).getCompleted() ){ 
			sb.setProgress(mSurvey.getQ(mCount).getAnswer()); 
		} else { 
			// This is the first time the user has seen the question
			sb.setProgress( mSurvey.getQ(mCount).getMax()/2 )  ; 
		}
	}
	
	private void setRatingBarStars(RatingBar rb){ 
		assert(mCount < mSurvey.size()); 
		
		rb.setMax(mSurvey.getQ(mCount).getCategorySize()); 
		rb.setNumStars(mSurvey.getQ(mCount).getCategorySize()); 
		rb.setRating(mSurvey.getNumCompleted(mCount)); 
		
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
			res += Integer.toString(mSurvey.getQ(i).getAnswer()) + " | " + Boolean.toString(mSurvey.getQ(i).getCompleted() ) + ", "; 
		} 
		return res; 
	}
	
	
};     
	
	
