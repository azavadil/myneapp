package com.myne.surveyapp;

public class HeaderPair {
	
	private int mIndex; 
	private String mText; 
	
	public HeaderPair(String headerText, int index){ 
		mText = headerText; 
		mIndex = index; 
	}
	
	public String getText(){ 
		return mText; 
	}
	
	public int getIndex(){ 
		return mIndex; 
	}

}
