package id206214280_id316650399;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class Exam<examQuestionArray> implements Cloneable, Serializable {

    private String examName;
    private static int counter=0;
    private int examId;
    private int numOfQuestions;
    private ArrayList<Questions> examQuestionArray;

    public Exam() {
        examQuestionArray = new ArrayList<>();
    }

    public boolean addQuestion(Questions newQuestion) {
        examId=++counter;
        this.examQuestionArray.add(newQuestion);
        numOfQuestions++;
        return true;
    }

    public Exam(Exam other) {
        this.numOfQuestions = other.numOfQuestions;
        for (int i = 0; i < other.numOfQuestions; i++) {
            this.examQuestionArray.set(i, (Questions) other.examQuestionArray.get(i));
        }
    }

    public int getNumOfQuestions() {
        return numOfQuestions;
    }

    public ArrayList<Questions> getQuestionsExamArray() {
        return examQuestionArray;
    }



    public void sortExamByShortestAnswers(){
        boolean noChange=false;
        for(int i=numOfQuestions-1;i>0 &&(noChange==false);i--){
            noChange=true;
            for(int j=0;j<i;j++){
                if(lengthOfAnswer(examQuestionArray.get(j+1))<lengthOfAnswer(examQuestionArray.get(j))){
                    Questions tempQuestion = examQuestionArray.get(j + 1);
                    examQuestionArray.set(j + 1, examQuestionArray.get(j));
                    examQuestionArray.set(j, tempQuestion);
                    noChange = false;
                }
            }
        }

    }

    //function that calculates length of an answer user by sortExamByShortestAnswers function
    public int lengthOfAnswer(Questions question){
        if(question instanceof OpenQuestions){
            return ((OpenQuestions) question).getAnswerText().length();
        }
        if(question instanceof  AmericanQuestions){
            int lengthOfAnswer=0;
            Object[] americanAnswerArr=((AmericanQuestions) question).getAnswerArray().toArray();
            for(int i=0;i<((AmericanQuestions) question).getNumOfAmericanAnswers();i++){

                lengthOfAnswer+=((AmericanAnswer)americanAnswerArr[i]).getAnswerText().length();
            }
            return lengthOfAnswer;
        }
        return 0;
    }

    @Override
    protected Exam clone() throws CloneNotSupportedException {
        Exam temp=(Exam)super.clone();
        ArrayList<Questions> copyQuestionArray = new ArrayList<>();
        for(int i=0;i<this.numOfQuestions;i++) {
            if (this.examQuestionArray.get(i) instanceof AmericanQuestions) {
                copyQuestionArray.add(((AmericanQuestions) this.examQuestionArray.get(i)).clone());
            } else if (this.examQuestionArray.get(i) instanceof OpenQuestions) {
                copyQuestionArray.add(((OpenQuestions) this.examQuestionArray.get(i)).clone());
            }
        }
        return temp;
    }

    public void saveToText() throws FileNotFoundException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm");
        Date today = Calendar.getInstance().getTime();
        String logDate = dateFormat.format(today);
        File createFileExam = new File("exam_" + logDate + ".txt");
        File createFileSolution = new File("solution_" + logDate + ".txt");
        PrintWriter writerExam = new PrintWriter(createFileExam);
        PrintWriter writerSolution = new PrintWriter(createFileSolution);
        if (examQuestionArray != null) {
            writerExam.println("the test without answers:\n");
            writerSolution.println("the test with answers:\n");
            for (int i = 0; i < numOfQuestions; i++) {
                writerExam.println((i + 1) + ") " + "the question is : " + examQuestionArray.get(i).getQuestionText() + "\n");
                writerSolution.println((i + 1) + ") " + "the question is : " + examQuestionArray.get(i).getQuestionText() + "\n");
                if (examQuestionArray.get(i) instanceof OpenQuestions) {
                    OpenQuestions printOpenQuestion = (OpenQuestions) examQuestionArray.get(i);
                    writerSolution.println("solution for question " + (i + 1) + ") " + "is: " + printOpenQuestion.getAnswerText() + "\n");
                }
                if (examQuestionArray.get(i) instanceof AmericanQuestions) {

                    AmericanQuestions printAmericanQuestion = (AmericanQuestions) examQuestionArray.get(i);
                    Object[] americanAnswerArr=printAmericanQuestion.getAnswerArray().toArray();
                    writerSolution.println("solutions for question " + (i + 1) + ") " + "is: " + "\n");
                    for (int j = 0; j < printAmericanQuestion.getNumOfAmericanAnswers(); j++) {
                        writerSolution.println((j + 1) + ") " + americanAnswerArr[j] + "\n");
                        writerExam.println((j + 1) + ") " + ((AmericanAnswer)americanAnswerArr[j]).getAnswerText() + "\n");
                    }
                }
            }
        }
        writerExam.println("Good Luck");
        writerSolution.println("End");
        writerExam.close();
        writerSolution.close();
    }


    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        Exam.counter = counter;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public void setNumOfQuestions(int numOfQuestions) {
        this.numOfQuestions = numOfQuestions;
    }

    public ArrayList<Questions> getExamQuestionArray() {
        return examQuestionArray;
    }


    @Override
    public String toString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        StringBuffer sb = new StringBuffer("The date is:");
        sb.append(dtf.format(now) + "\n");
        sb.append("The exam is:" + "\n");
        for (int i = 0; i < numOfQuestions; i++) {
            if (examQuestionArray.get(i) instanceof AmericanQuestions) {
                sb.append("Question number " + (i + 1) + "\n");
                sb.append(examQuestionArray.get(i).getQuestionText() + "\n");
                sb.append(((AmericanQuestions) examQuestionArray.get(i)).printAnswersOnly() + "\n");
                sb.append("\n");

            }
            if (examQuestionArray.get(i) instanceof OpenQuestions) {
                sb.append("Question number " + (i + 1) + "\n");
                sb.append("Question: "+examQuestionArray.get(i).getQuestionText() + "\n");
                sb.append("Question Answer "+((OpenQuestions) examQuestionArray.get(i)).getAnswerText() + "\n");
                sb.append("\n");

            }
        }
        return sb.toString();
    }
}
