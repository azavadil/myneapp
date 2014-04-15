package com.myne.surveyapp;

import course.examples.touch.ViewTransitions.R;
import android.app.ListActivity;
import android.util.Log;
import android.view.View; 
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;;

public class SummaryActivity extends ListActivity {

	private static final String TAG = SummaryActivity.class.getSimpleName(); 
	private static final String NUM_QUESTIONS = "numQuestions";
	private static final String ANSWER_LIST = "AnswerList";
	private static final String QUESTION_LIST = "QuestionList";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Log.d(TAG, "Summary Activity onCreate entered"); 
		
		Bundle extras  = getIntent().getExtras(); 
		Log.d(TAG,"bk0"); 
		String[] questions = extras.getStringArray(QUESTION_LIST);
		Log.d(TAG,"bk1" + questions[0]); 
		final int[] answers = extras.getIntArray(ANSWER_LIST); 
		Log.d(TAG,"bk2" + Integer.toString(answers[0])); 
		int numQuestions = extras.getInt(NUM_QUESTIONS);
		Log.d(TAG,"bk3" + Integer.toString(numQuestions)); 
		setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, questions)); 
		
		Log.d(TAG, "Bk1"); 
		ListView lv = getListView(); 
		
		lv.setOnItemClickListener(new OnItemClickListener(){ 
			
			public void onItemClick(AdapterView<?> parent, View view, int position, long id){ 
				Toast.makeText(getApplicationContext(), Integer.toString(answers[position]), Toast.LENGTH_SHORT).show(); 
			}
		}); 
		
		
		
		
	}



}
