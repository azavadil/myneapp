package com.myne.surveyapp;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MyneSurvey {
	
	private LinkedHashMap<String, List<Question>> mSurvey; 
	private int mNumQuestions; 
	private ArrayList<HeaderPair> mHeaders; 
	private ArrayList<Question> mQuestions; 
	
	
	public MyneSurvey(LinkedHashMap<String,List<Question>> surveyQuestions){
		
		mSurvey = surveyQuestions;
		
		
		mHeaders = new ArrayList<HeaderPair>(); 
		mQuestions = new ArrayList<Question>(); 
		
		
		int count = 0; 
		for(Map.Entry<String, List<Question>> entry : mSurvey.entrySet()){ 
			List<Question>cur = entry.getValue(); 
			mNumQuestions += cur.size();
			for(Question q : cur){ 
				q.setId(count); 
				mQuestions.add(q); 
				count++; 
			} 
			mHeaders.add(new HeaderPair(entry.getKey(), count)); 
		}
			
	}
	
	/**
	 * Returns the total number of questions in the survey
	 * @return int 
	 */
	
	public int size(){ 
		return mNumQuestions; 
	}
	
	/**
	 * 
	 * @param questionIndex index specifying a question
	 * @return the question object at the specified index
	 */
	
	public Question getQ(int questionIndex){ 
		return mQuestions.get(questionIndex); 
	}
	
	/**
	 * Gets the header that corresponds to a question in the survey 
	 * Implementation note: we will never reach the end of the for 
	 * loop without returning
	 * 
	 * @param questionNum: index number of the question
	 * @return String header for the question
	 */
	public String getHeader(int questionIndex){ 
		for(int i = 0; i < mHeaders.size();  i++){ 
			if( questionIndex <= mHeaders.get(i).getIndex()) { 
				return mHeaders.get(i).getText(); 
			}
		}
		return ""; 
	}
}
