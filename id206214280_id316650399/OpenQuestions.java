package id206214280_id316650399;

import java.util.Objects;

public class OpenQuestions extends Questions implements Cloneable {

	private String answerText;
	private OpenAnswer questionAnswer;
	
	public OpenQuestions(String questionText,String answerText) {
		super(questionText);
		this.questionAnswer=new OpenAnswer(answerText,this.questionId);
	}

	public OpenQuestions(OpenQuestions other){
		super(other.questionText);
		this.questionAnswer=new OpenAnswer(other.getAnswerText(), other.questionId);

	}

	public OpenAnswer getQuestionAnswer() {
		return questionAnswer;
	}

	public void setQuestionAnswer(OpenAnswer questionAnswer) {
		this.questionAnswer = questionAnswer;
	}

	public String getAnswerText() {
		return this.getQuestionAnswer().getAnswerText();
	}

	public void setAnswerText(String answerText) {
		this.getQuestionAnswer().setAnswerText(answerText);
		
	}
	protected OpenQuestions clone() throws CloneNotSupportedException{
	return	 (OpenQuestions)  super.clone();

	}

	@Override
	public int hashCode() {
		return Objects.hash(answerText);
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof OpenQuestions) ){
			return false;
		}
		if(!(super.equals(other))) {
			return false;
		}

		return  true;

	}
	
	@Override
	public String toString() {
		return super.toString()+"and the answer is: "+this.questionAnswer.toString()+"\n\n";
	}


	
	

	
	
	
	
}
