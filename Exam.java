package id206214280_id316650399;

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
        StringBuffer sb = new StringBuffer("the exam is:\n");
        for (int i = 0; i < numOfQuestions; i++) {
            if (questionsExamArray[i] instanceof AmericanQuestions) {
                sb.append(questionsExamArray[i].toString() + "\n");
            }
            if (questionsExamArray[i] instanceof OpenQuestions) {
                sb.append(questionsExamArray[i].toString() + "\n");
            }
        }
        return sb.toString();
    }
}
