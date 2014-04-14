package com.myne.surveyapp;

import java.util.prefs.Preferences;

import course.examples.touch.ViewTransitions.R;
import course.examples.touch.ViewTransitions.R.id;
import course.examples.touch.ViewTransitions.R.layout;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;

public class LoginActivity extends Activity {
	
	private static final String PASSWORD = "Password";
	private static final String USERNAME = "Username";
	private Button mButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		EditText et1 = (EditText)findViewById(R.id.loginEditText1); 
		EditText et2 = (EditText)findViewById(R.id.loginEditText2); 
		
		SharedPreferences sp  = PreferenceManager.getDefaultSharedPreferences(this); 
		sp.edit().putString(USERNAME, et1.getText().toString()	).putString(PASSWORD, 
				et2.getText().toString()).commit(); 
		
		
		 mButton = (Button)findViewById(R.id.loginButton); 
		 
		 mButton.setOnClickListener(new OnClickListener(){ 
			 
			 @Override 
			 public void onClick(View v){ 
				 
				 Intent menuActivity = new Intent(getApplicationContext(), MenuActivity.class); 
				 startActivity(menuActivity); 
				 finish(); 
			 }
		 }); 
		
	}


}
