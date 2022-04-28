package id206214280_id316650399;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

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
    public void save() throws FileNotFoundException{
        DateFormat dateFormat =new SimpleDateFormat("yyyy_MM_dd_HH_mm");
        Date today = Calendar.getInstance().getTime();
        String logDate= dateFormat.format(today);
        File createFileExam=new File("exam_"+logDate+".txt");
        File createFileSolution=new File("solution_"+logDate+".txt");
        PrintWriter writerExam=new PrintWriter(createFileExam);
        PrintWriter writerSolution=new PrintWriter(createFileSolution);
        if(questionsExamArray!=null){
            writerExam.println("the test without answers:\n");
            writerSolution.println("the test with answers:\n");
            for (int i=0;i<numOfQuestions;i++){
                writerExam.println("the question is : "+questionsExamArray[i].getQuestionText()+"\n");
                writerSolution.println("the question is : "+questionsExamArray[i].getQuestionText()+"\n");
                if(questionsExamArray[i] instanceof OpenQuestions){
                    OpenQuestions printOpanQuestion=(OpenQuestions) questionsExamArray[i];
                    writerSolution.println("solution for question "+i+1+") "+"is: "+printOpanQuestion.getAnswerText()+"\n");
                }
                if (questionsExamArray[i] instanceof AmericanQuestions){
                    AmericanQuestions printAmricnQuestion=(AmericanQuestions) questionsExamArray[i];
                    writerSolution.println("solutions for question "+i+1+") "+"is: "+"\n");
                    for(int j=0;j<printAmricnQuestion.getNumOfAmericanAnswers();j++){
                        writerSolution.println(j+1+") "+printAmricnQuestion.getAnswerArray()[j]+"\n");
                    }



                }
            }
        }
        writerExam.println("good luck");
        writerSolution.println("end");
        writerExam.close();
        writerSolution.close();
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
