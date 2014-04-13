package com.myne.surveyapp;

public class Question {
	
	private String mQuestion; 
	private int mMaxRange; 
	private boolean mCompleted; // initialized to false
	private int mAnswer;   // initialized to null, same as all reference types
	private int mId; 
	
	
	public Question(String question, int maxRange){ 
		this.mQuestion = question; 
		this.mMaxRange = maxRange; 
	}
	
	public String getMsg(){
		return mQuestion;
	}
	
	public int getMax(){
		return mMaxRange; 
	}
	
	public void setMsg(String msg){ 
		mQuestion = msg; 
	}
	
	public void setMax(int max){ 
		mMaxRange = max; 
	}
	
	public int getAnswer(){ 
		return mAnswer; 
	} 
	
	public void setAnswer(int answer){ 
		mAnswer = answer; 
	}
	
	public boolean getCompleted(){ 
		return mCompleted; 
	}
	
	public void setCompleted(boolean completed){ 
		mCompleted = completed; 
	}

	public int getId(){ 
		return mId; 
	}
	
	public void setId(int n){ 
		mId = n; 
	}
}
