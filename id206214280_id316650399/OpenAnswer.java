package id206214280_id316650399;

import java.io.Serializable;

public class OpenAnswer implements Serializable,Cloneable {
    private String answerText;
    private int answerId;

    public OpenAnswer(String answerText, int questionId){
        this.answerText=answerText;
        this.answerId=questionId;

    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    @Override
    public String toString() {
        return  answerText + '\'' +
                + answerId ;

    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }
}
