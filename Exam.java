package id206214280_id316650399;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Exam {
    private int numOfQuestions;
    private Questions[] questionsExamArray ;

    public Exam(int numOfQuestions) {
        this.numOfQuestions = numOfQuestions;
        this.questionsExamArray = new Questions[numOfQuestions];
    }

    public Exam(Exam other){
        this.numOfQuestions=other.numOfQuestions;
        this.questionsExamArray=other.questionsExamArray;
    }

    public int getNumOfQuestions() {
        return numOfQuestions;
    }

    public Questions[] getQuestionsExamArray() {
        return questionsExamArray;
    }
    public void sortQuestionsByLexicographicOrderExem() {
        boolean noChange = false;
        for (int i = this.getNumOfQuestions() - 1; i > 0 && (noChange == false); i--) {
            for (int j = 0; j < i; j++) {
                noChange = true;
                if (questionsExamArray[j].getQuestionText().compareToIgnoreCase(questionsExamArray[j + 1].getQuestionText()) > 0) {
                    Questions tempQuestion = questionsExamArray[j + 1];
                    questionsExamArray[j + 1] = questionsExamArray[j];
                    questionsExamArray[j] = tempQuestion;
                    noChange = false;
                }
            }
        }

    }


    @Override
    public String toString() {
        DateTimeFormatter dtf=DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now= LocalDateTime.now();
        StringBuffer sb = new StringBuffer("The date is:");
        sb.append(dtf.format(now)+"\n");
        sb.append("The exam is:"+"\n");
        for (int i = 0; i < numOfQuestions; i++) {
            if (questionsExamArray[i] instanceof AmericanQuestions) {
                sb.append("Question number "+(i+1)+"\n");
                sb.append(this.getQuestionsExamArray()[i].getQuestionText()+"\n");
                sb.append(((AmericanQuestions)this.getQuestionsExamArray()[i]).printAnswersOnly()+"\n");
                sb.append("\n");

            }
            if (questionsExamArray[i] instanceof OpenQuestions) {
                sb.append("Question number "+(i+1)+"\n");
                sb.append(this.getQuestionsExamArray()[i].getQuestionText()+"\n");
                sb.append(((OpenQuestions)this.getQuestionsExamArray()[i]).getAnswerText()+"\n");
                sb.append("\n");

            }
        }
        return sb.toString();
    }
}
