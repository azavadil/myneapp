package com.myne.surveyapp;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class SurveyBuilder {
	
	LinkedHashMap<String, List<Question>> mSurvey; 
	
	public SurveyBuilder(){ 
		mSurvey = new LinkedHashMap<String, List<Question>>(); 
		
		List<Question> l0 = Arrays.asList(new Question("Sleep hours", 20),
				new Question("Sleep quality" ,100), 
				new Question("Nap mins", 100), 
				new Question( "Meditation mins", 100));
		
		List<Question> l1 = Arrays.asList(new Question("Energy",100),
				new Question("Confidence",100), 
				new Question("Anxiety",100), 
				new Question("Focus",100), 
				new Question("Motivation (how big is the chip on my shoulder",100), 
				new Question("Happiness",100)); 
		
		List<Question> l2 = Arrays.asList(new Question("Past",100), 
				new Question("Present",100), 
				new Question("Future",100), 
				new Question("Winning",100), 
				new Question("Losing", 100), 
				new Question("My Team",100), 
				new Question("Opponents",100), 
				new Question("To-Do List",100), 
				new Question("Personal Relationships",100), 
				new Question("Negative Thoughts",100), 
				new Question("Positive Thoughts",100));
		
		
		List<Question> l3 = Arrays.asList(new Question("Soreness",100), 
				new Question("Strength",100	));
		
		
		List<Question> l4 = Arrays.asList(new Question("Overall",100), 
				new Question("Professional",100), 
				new Question("Health", 100), 
				new Question("Financial",100), 
				new Question("Romantic", 100), 
				new Question("Friendships",100), 
				new Question("Family",100), 
				new Question("Location",100));
		
		
		List<Question> l5 = Arrays.asList(new Question("Overall",100), 
				new Question("Professional",100), 
				new Question("Health", 100), 
				new Question("Financial", 100), 
				new Question("Romantic",100), 
				new Question("Friendships",100), 
				new Question("Family",100)); 
		
		List<Question> l6 = Arrays.asList(new Question("Manager", 100), 
				new Question("Team",100), 
				new Question("Front Office",100), 
				new Question("Pitching Coach", 100)); 
		
		List<Question> l7 = Arrays.asList(new Question("How fired up to play opponent", 100), 
				new Question("How well do I think I'm going to pitch",100)); 
		
		mSurvey.put("Rest", l0); 
		mSurvey.put("Daily mental state", l1); 
		mSurvey.put("Subjects most on my mind", l2); 
		mSurvey.put("Physical condition", l3); 
		mSurvey.put("Life satisfaction", l4); 
		mSurvey.put("Stability/Security", l5); 
		mSurvey.put("Support network", l6); 
		mSurvey.put("Pre-game evaluation", l7); 
		
	}
	
	public LinkedHashMap<String, List<Question>> getSurvey(){
			return mSurvey; 
	}

}
