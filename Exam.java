package id206214280_id316650399;

public class Exam {
    private int numOfQuestions;
    private  Questions[] questionsExamArray =new Questions[numOfQuestions];
    public Exam(int numOfQuestions){
        this.numOfQuestions=numOfQuestions;
        this.questionsExamArray =new Questions[numOfQuestions];
    }
    public int getNumOfQuestions(){
        return numOfQuestions;
    }
    public Questions[] getQuestionsExamArray(){
        return questionsExamArray;
    }

    @Override
    public String toString() {
        StringBuffer sb =new StringBuffer("the exam is:\n");
        for(int i=0;i<numOfQuestions;i++){
            if (questionsExamArray[i] instanceof AmericanQuestions){
                sb.append(((AmericanQuestions) questionsExamArray[i]).toString()+"\n");
            }
            if (questionsExamArray[i] instanceof OpenQuestions){
                sb.append(((OpenQuestions) questionsExamArray[i]).toString()+"\n");
            }
        }
        return sb.toString();
    }
}
