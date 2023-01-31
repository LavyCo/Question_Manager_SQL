package id206214280_id316650399;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main implements questionReservoirSQL {

    public static void main(String[] args)
            throws Exception {


        QueryQuestionReservoir manager = new QueryQuestionReservoir();
        manager.conn = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306/exam";
            manager.conn = DriverManager.getConnection(url, "root", "asherb1993");
            manager.stmt = manager.conn.createStatement();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Main main1 = new Main();

        int optMenu = 0;
        Scanner input = new Scanner(System.in);
        do {
            System.out.println("-----------------------------------------------------------------");
            System.out.println("Welcome to the Exam generator program");
            System.out.println("Please choose an option from the menu below");
            System.out.println("1-Show all questions and their answers in the in the reservoir");
            System.out.println("2-Add questions");
            System.out.println("3-Change the question wording");
            System.out.println("4-Update the wording of an answer");
            System.out.println("5-Delete an answer to a question");
            System.out.println("6-Create a test manually");
            System.out.println("7-Create a test automatically");
            System.out.println("-----------------------------------------------------------------");
            try {
                optMenu = input.nextInt();
                if (optMenu < 1 || optMenu > 9)
                    throw new Exception("Please choose Option from the range of 1-9");
            } catch (InputMismatchException e) {
                System.out.println("Please input numbers only");
                input.nextLine();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                input.nextLine();
            }

            switch (optMenu) {
                // option 1 show all questions and answers

                case 1: {
                    manager.printQuestions();
                    break;
                }

                // option 2 add questions
                case 2: {
                    // main1.addQuestion(qr1, input);
                    main1.addQuestionToDataBase(manager, input);
                    break;
                }

                // change the wording of a question // Dor Does it
                case 3: {
                    main1.updateQuestionWordingDB(manager);
                    break;
                }

                // change the wording of an answer // Dor Does it
                case 4: {
                    main1.updateAnswerWordingDB(manager);
                }
                break;

                // delete an answer
                case 5: {
                    main1.deleteAnswerDB(manager);
                    break;
                }

                case 6: {

                    main1.createManualExamDB(manager);
                    break;
                }

                case 7: {

                    main1.createAutomaticExamDB(manager);
                    break;
                }


            }
        } while (optMenu != 9);
        input.close();
    }


    @Override
    public void addQuestionToDataBase(QueryQuestionReservoir qqr, Scanner input) throws SQLException {
        System.out.println("What type of question do you want?");
        System.out.println("1)Open question \n2)American question");
        int optAddQuestion = exception1Or2Select();
        if (optAddQuestion == 1) {
            System.out.println("please enter a Question text:");
            input.nextLine();
            String questionText = input.nextLine();
            System.out.println("please enter a Answer text:");
            String OpenAnswer = input.nextLine();
            // fixed case 2 sends the function Strings instead of OpenQuestion
            boolean canOrNot = qqr.addOpenQuestionDB(questionText, OpenAnswer);
            if (canOrNot)
                System.out.println("Added question ");

        } else {
            System.out.println("your question Already exists");
        }


        // add american answer
        if (optAddQuestion == 2) {
            System.out.println("Please enter a Question text:");
            input.nextLine();
            String americanQuestionText = input.nextLine();
            qqr.addAmericanQuestionDB(americanQuestionText);

            boolean AnswerOptTry = false;
            int numberOfAnswers = 10;
            while (!AnswerOptTry) {
                try {
                    System.out.println("enter a number of answers 2-10 only:");
                    numberOfAnswers = input.nextInt();

                    if (numberOfAnswers > 10 || numberOfAnswers <= 1) {
                        System.out.println("try again");

                    } else {
                        AnswerOptTry = true;
                    }

                } catch (InputMismatchException e) {
                    System.out.println("Please enter a valid input(numbers only)");
                    input.next();// cleans buffer
                } catch (Exception e) {
                    System.out.println("Error " + e.getMessage());
                }
            }
            String[] americanAnswersText = new String[numberOfAnswers];
            boolean[] americanAnswersCorrectness = new boolean[numberOfAnswers];
            for (int i = 0; i < numberOfAnswers; i++) {
                // inputting text
                System.out.println("Please enter answer text for answer number " + (i + 1) + "/" + numberOfAnswers);
                input.nextLine();
                americanAnswersText[i] = input.nextLine();


                // inputting correctness
                int trueOrFalse = 2;
                boolean flagZeroOrOne = false;
                while (!flagZeroOrOne) {
                    try {
                        System.out
                                .println("Please enter correctness of the answer for true press-1 for False press-0:");
                        trueOrFalse = input.nextInt();
                        if (trueOrFalse == 0 || trueOrFalse == 1) {
                            flagZeroOrOne = true;
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Please enter a valid input(numbers only)");
                        input.nextLine();// cleans buffer
                    } catch (Exception e) {
                        System.out.println("Error " + e.getMessage());
                    }
                }

                if (trueOrFalse == 1) {
                    americanAnswersCorrectness[i] = true;
                } else {
                    americanAnswersCorrectness[i] = false;
                }
                qqr.addAnswer(americanAnswersText[i], americanAnswersCorrectness[i]);
            }

        }
    }


    public void deleteAnswerDB(QueryQuestionReservoir manager) throws Exception {
        System.out.println("please enter the question ID and the answer ID which you would like to delete");
        manager.printAmericanQuestions();
        Scanner s = new Scanner(System.in);
        System.out.println("Please enter question Id number");
        int questionID = s.nextInt();
        manager.rs = manager.stmt.executeQuery("SELECT COUNT(QID) FROM questionTable WHERE isMulti=1");
        System.out.println("Please enter answer Id number");
        int answerId = s.nextInt();
        if (manager.deleteAnswer(questionID, answerId) == true)
            System.out.println("Deleted successfully");
        else
            System.out.println("Error");
    }

    @Override
    public void updateQuestionWordingDB(QueryQuestionReservoir manager) throws Exception {
        manager.printQuestionWithoutAnswers();
        System.out.println("Please choose a question ID to change it's text:\n");
        Scanner s = new Scanner(System.in);
        int questionId = s.nextInt();
        System.out.println("Please enter a new text for this question");
        s.nextLine();
        String newText = s.nextLine();
        if (manager.changeQuestionText(questionId, newText) == true)
            System.out.println("Question text changed successfully ");
        else
            System.out.println("Error");
    }

    @Override
    public void updateAnswerWordingDB(QueryQuestionReservoir manager) throws Exception {
        manager.printQuestions();
        Scanner s = new Scanner(System.in);
        System.out.println("Please choose question ID");
        int questionID = s.nextInt();
        if (manager.getQuestionTypeDB(questionID) == 0) {
            System.out.println("Please enter new answer text");
            s.nextLine();
            String newAnswerText = s.nextLine();
            if (manager.changeOpenAnswerWording(questionID, newAnswerText) == true)
                System.out.println("Text Changed succesfully");
            else
                System.out.println("Error");
        } else {
            System.out.println("Please choose by ID which answer you want to change:");
            int answerID = s.nextInt();
            s.nextLine();
            System.out.println("Please enter new answer text");
            String newAnswerText = s.nextLine();
            if (manager.changeAmericanAnswerWording(questionID, answerID, newAnswerText) == true)
                System.out.println("Text Changed Succesfully");
            else
                System.out.println("Error");
        }

    }

    @Override
    public int createManualExamDB(QueryQuestionReservoir manager) throws Exception {
        int numberOfQuestions = manager.getNumOfQuestions();
        if (numberOfQuestions > 0) {
            Scanner s = new Scanner(System.in);
            System.out.printf("Please Choose how many questions you want to add to the Exam (1-%d)\n", numberOfQuestions);
            int numberOfQuestionsForExam = s.nextInt();
            if (numberOfQuestionsForExam < 0 || numberOfQuestionsForExam >= numberOfQuestions) {
                System.out.println("Error invalid number of questions");
                return 0;
            }
            int examId = manager.createExam(numberOfQuestionsForExam);
            System.out.println(examId);
            int questionId;
            manager.printQuestions();
            System.out.println(numberOfQuestionsForExam);
            for (int i = 0; i < numberOfQuestionsForExam; i++) {
                System.out.println("i=" + i + "");
                System.out.println("Please choose which questions you want to add to the exam");
                questionId = s.nextInt();
                if (manager.doesQuestionExists(questionId) == true && manager.isExistsInExam(examId, questionId) == false)
                    manager.addQuestionToExam(examId, questionId);
                else {
                    System.out.println("Question either not in table or Already in exam");
                }
                manager.printExam(examId);
            }
        } else {
            System.out.println("Can't create exam no questions in the database");
            return 0;
        }
        return 1;
    }

    @Override
    public int createAutomaticExamDB(QueryQuestionReservoir manager) throws Exception {
        Scanner s = new Scanner(System.in);
        if(manager.getNumOfQuestions()==0)
        {
            System.out.println("ERROR No questions in DATABASE");
            return 0;
        }
        System.out.println("Please choose how many question your want in your exam");
        int numberOfQuestionsInExam = s.nextInt();
        int numberOfQuestions = manager.getNumOfQuestions();
        if (numberOfQuestionsInExam < 0 || numberOfQuestionsInExam > numberOfQuestions) {
            System.out.println("ERROR Invalid input");
            return 0;
        }
        int examId = manager.createExam(numberOfQuestionsInExam);
        manager.addRandomQuestionsToExam(examId,numberOfQuestionsInExam);
        manager.printExam(examId);
        return 1;
    }


    // a function that inputs if the question is 1 or 2 and return the select
    public static int exception1Or2Select() {
        Scanner input = new Scanner(System.in);
        int optAddQuestion = 0;
        boolean flag = false;
        while (!flag) {
            try {

                System.out.println("Please choose 1 or 2");
                optAddQuestion = input.nextInt();
                if (optAddQuestion == 2 || optAddQuestion == 1) {

                    flag = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid input(numbers only)");
                input.nextLine();// cleans buffer
            } catch (Exception e) {
                System.out.println("Error " + e.getMessage());
            }

        }
        return optAddQuestion;
    }


}