package id206214280_id316650399;

import java.io.Serializable;
import java.util.Objects;

 public  class  Questions implements Serializable {
	private static int counter=1;
	protected int questionId;
	protected String questionText;
	
	public Questions(String questionText) {
		this.questionText=questionText;
		setQuestionId();
	}


	protected void decreaseIdCounter(){
		if(counter>0){
			--counter;
		}
	}
	public String getQuestionText() {
		return questionText;
	}
	public boolean setQuestionText(String questionText) {
		this.questionText = questionText;
		return true;
	}
	public int getQuestionId() {
		return questionId;
	}
	@Override
	public String toString() {
		
		return "ID ("+questionId+") "+"the question is: "+questionText+"\n";
	}
	@Override
	public int hashCode() {
		return Objects.hash(questionId, questionText);
	}

	@Override
	public  boolean  equals(Object other) {

		if (!(other instanceof Questions) ){
			return false;
		}
		Questions p=(Questions)other;
		return  this.questionText.equalsIgnoreCase(p.questionText);
	}
	 public void setQuestionId() {
		 this.questionId=counter++;
		}

 }
