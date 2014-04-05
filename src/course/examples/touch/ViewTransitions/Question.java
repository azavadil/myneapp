package course.examples.touch.ViewTransitions;

public class Question {
	
	private String mQuestion; 
	private int mMaxRange; 
	
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

}
