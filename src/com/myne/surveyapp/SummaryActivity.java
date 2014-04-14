package com.myne.surveyapp;

import course.examples.touch.ViewTransitions.R;
import android.app.ListActivity;
import android.view.View; 
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;;

public class SummaryActivity extends ListActivity {

	private static final String NUM_QUESTIONS = "numQuestions";
	private static final String ANSWER_LIST = "AnswerList";
	private static final String QUESTION_LIST = "QuestionList";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_summary);
		
		Bundle extras  = getIntent().getExtras(); 
		String[] questions = extras.getStringArray(QUESTION_LIST);
		final int[] answers = extras.getIntArray(ANSWER_LIST); 
		int numQuestions = extras.getInt(NUM_QUESTIONS);
		setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, questions)); 
		
		ListView lv = getListView(); 
		
		lv.setOnItemClickListener(new OnItemClickListener(){ 
			
			public void onItemClick(AdapterView<?> parent, View view, int position, long id){ 
				Toast.makeText(getApplicationContext(), Integer.toString(answers[position]), Toast.LENGTH_SHORT).show(); 
			}
		}); 
		
		
		
		
	}



}
