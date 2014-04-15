package com.myne.surveyapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MyneSurvey {
	
	private LinkedHashMap<String, List<Question>> mSurvey;  
	private int mNumQuestions; 
	private HashMap<String, Integer> mCategories;
    private HashMap <Integer, String> mIndexToCategory; 
	private ArrayList<Question> mQuestions; 
	
	
	public MyneSurvey(LinkedHashMap<String,List<Question>> surveyQuestions){
		
		mSurvey = surveyQuestions;
		
		
		mCategories = new HashMap<String, Integer>();
		mIndexToCategory = new HashMap<Integer, String>(); 
		mQuestions = new ArrayList<Question>(); 
		
		
		int count = 0; 
		for(Map.Entry<String, List<Question>> entry : mSurvey.entrySet()){
			int categoryCount = 0; 
			List<Question>cur = entry.getValue(); 
			mNumQuestions += cur.size();
			for(Question q : cur){ 
				q.setId(count); 
				q.setCategoryIndex(categoryCount); 
				q.setCategorySize(cur.size()) ;
				q.setCategory(entry.getKey()); 
				mIndexToCategory.put(count, entry.getKey()); 
				mQuestions.add(q); 
				count++; 
				categoryCount++; 
			} 
			mCategories.put(entry.getKey(), 0); 
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
		return mIndexToCategory.get(questionIndex); 
	}
	
	public int getNumCompleted(int index){ 
		return mCategories.get(mIndexToCategory.get(index)); 
	}
	
	public void incrNumCompleted(int index){ 
		mCategories.put(mIndexToCategory.get(index),
				mCategories.get(mIndexToCategory.get(index)) + 1);  
	}
	
	public String[] getQuestionArray(){ 
		String[] res = new String[mQuestions.size()];
		for(int i = 0; i < mQuestions.size(); i++){ 
			res[i] = mQuestions.get(i).getMsg(); 
		}
		return res; 
	}
	
	public int[] getAnswerArray(){
		int[] answers = new int[mQuestions.size()]; 
		for(int i = 0; i < mQuestions.size(); i++){ 
			answers[i] = mQuestions.get(i).getAnswer(); 
		}
		return answers; 
	}
	
}
