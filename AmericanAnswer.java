package id206214280_id316650399;

import java.io.Serializable;
import java.util.Objects;

public class AmericanAnswer implements Serializable {
	
	private boolean correctness;
	private String answerText;
	
	public AmericanAnswer(String answerText,boolean correctness) {
		this.answerText=answerText;
		this.correctness=correctness;
	}

	public AmericanAnswer(AmericanAnswer other){
		this.answerText=other.answerText;
		this.correctness=other.correctness;
	}




	@Override
	public String toString() {
		String sb; 
		if(correctness) {
			sb=" (True)";
		
		}
		else {
			sb=" (False)";
					
		}
		return " "+answerText+sb;
	}


	@Override
	public int hashCode() {
		return Objects.hash(answerText, correctness);
	}


	@Override
	public boolean equals(Object other) {
		if (!(other instanceof AmericanAnswer) ){
			return false;
		}
		AmericanAnswer p=(AmericanAnswer)other;
	  return  this.answerText.equalsIgnoreCase(p.answerText);
	}


	public boolean getCorrectness() {
		return correctness;
	}

	public boolean setCorrectness(boolean correctness) {
		this.correctness = correctness;
		return true;
	}

	public String getAnswerText() {
		return answerText;
	}

	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	}
	
	
	
	
	
	
	
	
}
