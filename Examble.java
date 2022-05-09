package id206214280_id316650399;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public interface Examble {
    Scanner input = new Scanner(System.in);

    //case 1-show all answer
    void printQuestions (QuestionReservoir qr1);
    //Add questions
    void addQuestion (QuestionReservoir qr1,Scanner input);
    //Change the question wording
    void updateQuestionWording(QuestionReservoir qr1) throws Exception;
    //Update the wording of an answer
    void updateAnswerWording(QuestionReservoir qr1) throws Exception;
    //Delete an answer to a question
    void deleteAnswer(QuestionReservoir qr1) throws Exception;
    //Create a test manually
    void createManualExam(QuestionReservoir qr1) throws Exception;
    //Create a test automatically
    void createAutomaticExam(QuestionReservoir qr1) throws FileNotFoundException;
    //clone an exam
    void cloneExam(QuestionReservoir qr1) throws CloneNotSupportedException;

    //  save binary program
    void saveBinary(QuestionReservoir qr1) throws IOException, ClassNotFoundException;
}
