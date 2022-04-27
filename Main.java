package id206214280_id316650399;

import java.util.InputMismatchException;
import java.util.Scanner;

//כדי להצ
// ליח לייצר עוד מקצועות עלינו ליצור מחלקות של מקצועות שמקבלות מאגרים
public class Main {

    public static void main(String[] args) throws Exception {
        QuestionReservoir qr1 = new QuestionReservoir();
        //open question #1
        qr1.addOpenQuestion("Who was Yitzhak Rabin?","Israeli prime minister");
        //open question #2
       qr1.addOpenQuestion("Who solved the Engima machine during WWII?","Alan Turing");
        //open question #3
        qr1.addOpenQuestion("What is the difference between Choclate cake and a Salad?","Salad wont give you Diabetes");

        //american question #1
        String aq1=("Which one of these is not dessert?");
        String[] aa1=new String[]{"Muffin","Choclate cake","Ice cream","Brownies","Salad","Burger","Shawrma","Pizza"};
        boolean[] tof1=new boolean[]{false,false,false,false,true,true,true,true};
        qr1.addAmericanQuestion(aq1,aa1,tof1);
        //american question #2
        String aq2="Which one of these next Programming languages is Low-Level programming language?";
        String[] aa2=new String[]{"Java","C++","Assembly","C","C#","Python","JavaScript","Swift","Machine Code"};
        boolean[] tof2=new boolean[]{false,false,true,false,false,false,false,false,true};
        qr1.addAmericanQuestion(aq2,aa2,tof2);



        int optMenu = 0;
        Scanner input = new Scanner(System.in);
        do {
            System.out.println("-----------------------------------------------------------------");
            System.out.println("Please choose an option from the menu");
            System.out.println("1-Show all questions and their answers in the in the resevoir");
            System.out.println("2-Add questions");
            System.out.println("3-Change the question wording");
            System.out.println("4-Update the wording of an answer");
            System.out.println("5-Delete an answer to a question");
            System.out.println("6-Create a test manually");
            System.out.println("7-Create a test automatically");
            System.out.println("8-Exit program");
            System.out.println("-----------------------------------------------------------------");
            try {
                optMenu = input.nextInt();
                if (optMenu < 1 || optMenu > 8) throw new Exception("Please choose Option from the range of 1-8");
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
                    System.out.println(qr1.toString());
                    break;
                }

                // option 2 add questions
                case 2: {
                    System.out.println("What type of question do you want?");
                    System.out.println("1)Open question \n2)American question");
                    int optAddQuestion = exception1Or2Select();
                    if (optAddQuestion == 1) {
                        System.out.println("please enter a Question text:");
                        input.nextLine();// cleans buffer
                        String questionText = input.nextLine();
                        System.out.println("please enter a Answer text:");
                        String OpenAnswer = input.nextLine();
                        //fixed case 2 sends the function Strings instead of OpenQuestion
                        boolean canOrNot = qr1.addOpenQuestion( questionText, OpenAnswer);
                        if (canOrNot) {
                            System.out.println("Added question ");

                        } else {
                            System.out.println("your question Already exists");
                        }

                    }
                    //add american answer
                    if (optAddQuestion == 2) {
                        System.out.println("Please enter a Question text:");
                        input.nextLine();
                        String americanQuestionText = input.nextLine();
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
                                input.nextLine();// cleans buffer
                            } catch (Exception e) {
                                System.out.println("Error " + e.getMessage());
                            }
                        }
                        String[] americanAnswersText=new String[numberOfAnswers];
                        boolean[] americanAnswersCorrectness=new boolean[numberOfAnswers];
                        for(int i=0;i<numberOfAnswers;i++){
                            //inputting text
                            System.out.println("Please enter answer text for answer number "+(i+1)+"/"+numberOfAnswers);
                            americanAnswersText[i]=input.next();
                            input.nextLine();
                            //inputting correctness
                            System.out.println("Please enter correctness of the answer for true press-1 for False press-0:");
                            int trueOrFalse=input.nextInt();
                            //TO DO try catch
                            if(trueOrFalse==1){
                                americanAnswersCorrectness[i]=true;
                            }
                            else{
                                americanAnswersCorrectness[i]=false;
                            }
                        }
                        qr1.addAmericanQuestion(americanQuestionText,americanAnswersText,americanAnswersCorrectness);

                    }
                    break;
                }

                // change the wording of a question
                case 3: {
                    System.out.println(
                            "Please choose which question would you like to change from the list below:");
                    // print all questions an show their Id
                    for (int i = 0; i < qr1.getNumberOfQuestions(); i++) {
                        System.out.print("id is:(" + qr1.getQuestionArray()[i].getQuestionId() + ")" + " ");
                        System.out.println("Question text is: " + qr1.getQuestionArray()[i].getQuestionText());
                    }
                    // let user choose a question id by input
                    int expId = exceptionId(qr1);
                    System.out.println("Please enter the new wording for the text:");
                    input.nextLine();
                    String newQuestionText = input.nextLine();
                    qr1.changeQuestionWording(newQuestionText, expId);
                    //check array to see if adding "All answers are false" or "more than 1 answer is true needs to be added"


                    break;

                }

                //change the wording of an answer
                case 4: {
                    System.out.println(
                            "Hi please choose from below the id of the Answer you want to change the wording of");
                    // print all questions and their answers and show their Ind
                    System.out.println(qr1.toString());
                    // let user choose a question id by input
                    int expId = exceptionId(qr1);
                    int indQuestion = 0;
                    int numOfAnswer = 0;

                    for (int i = 0; i < qr1.getNumberOfQuestions(); i++) {
                        if (expId == qr1.getQuestionArray()[i].getQuestionId()) {
                            indQuestion = i;
                        }
                    }
                    if (qr1.getQuestionArray()[indQuestion] instanceof AmericanQuestions) {
                        boolean flagNumOfAnswer = false;
                        ((AmericanQuestions) qr1.getQuestionArray()[indQuestion]).toString();
                        numOfAnswer = exceptionNumOfAnswer(qr1, expId);

                        System.out.println("You chose to change the answer" + (((AmericanQuestions) qr1.getQuestionArray()[indQuestion]).getAnswerArray()[numOfAnswer - 1]));
                        System.out.println("Please enter a new wording for the answer:");
                        input.nextLine();//cleans buffer
                        String newWordingForAmericanAnswer = input.nextLine();
                        boolean newAnswerOpt = exceptionIsAmericanTrueOrFalse();
                        qr1.changeAnswerWordingOfAmericanQuestions(newWordingForAmericanAnswer, (AmericanQuestions) qr1.getQuestionArray()[indQuestion], numOfAnswer - 1, newAnswerOpt);
                        askUserIftoAddAnswer((AmericanQuestions) qr1.getQuestionArray()[indQuestion]);

                    }
                    if ((qr1.getQuestionArray()[indQuestion] instanceof OpenQuestions)) {
                        System.out.println("type in a new answer wording ");
                        input.nextLine();// cleans buffer
                        String newAnswer = input.nextLine();
                        if (qr1.changeAnswerWordingOfOpenQuestion(newAnswer, qr1.getQuestionArray()[indQuestion], numOfAnswer)) {
                            System.out.println("the answer wording changed");
                        } else {
                            System.out.println("failed");
                        }
                    }
                }

                break;

                //delete an answer
                case 5: {
                    //Cant delete an answer for open question
                    //Can only delete up until 2 answers for american questions
                    //american question needs at least 1 true answer
                    //if american question has more than 1 correct answer than it needs at least 1 false answer
                    //if american question has 2 answers,1 of them needs to be true and the second needs to be true
                    //if american question has only 1 and user wants to delete it add another answer (none of the answers is correct)
                    //let user choose an id of the question it wants to delete
                    System.out.println(qr1.toString());
                    int userGivenQuestionId = exceptionId(qr1);
                    //find the question index and check its type
                    int indQuestion = 0;
                    boolean americanQuestionFlag = true;
                    for (int i = 0; i < qr1.getNumberOfQuestions(); i++) {
                        if (qr1.getQuestionArray()[i].getQuestionId() == userGivenQuestionId) {
                            if (qr1.getQuestionArray()[i] instanceof OpenQuestions) {
                                System.out.println("Can't delete the answer of an open question");
                                americanQuestionFlag = false;
                            } else {
                                indQuestion = i;
                                americanQuestionFlag = true;
                            }
                        }

                    }

                    if (americanQuestionFlag) {
                        int answerNumber = 0;
                        //choosing the answer number
                        boolean flagCountAmericanAns = false;
                        System.out.println(((AmericanQuestions) qr1.getQuestionArray()[indQuestion]).printAnswersOnly());
                        System.out.println("" + ((AmericanQuestions) qr1.getQuestionArray()[indQuestion]).getNumOfAmericanAnswers() + "");
                        while (!flagCountAmericanAns) {
                            try {
                                System.out.println("Please choose the answer you want to delete");
                                answerNumber = input.nextInt();
                                if (answerNumber > 0 && answerNumber <= ((AmericanQuestions) qr1.getQuestionArray()[indQuestion]).getNumOfAmericanAnswers()) {
                                    flagCountAmericanAns = true;
                                }

                            } catch (InputMismatchException e) {
                                System.out.println(e.getMessage());
                            } catch (Exception e) {
                                System.out.println("Error " + e.getMessage());
                            }
                        }
                        if (answerNumber > 0 && answerNumber <= ((AmericanQuestions) qr1.getQuestionArray()[indQuestion]).getNumOfAmericanAnswers()) {
                            System.out.println("Choosen answer to delete is" + ((AmericanQuestions) qr1.getQuestionArray()[indQuestion]).getAnswerArray()[answerNumber - 1]);
                        }

                        //sending parametes to the function (Question Index and Answer number)
                        qr1.deleteAmericanAnswer(indQuestion, (answerNumber - 1));
                        askUserIftoAddAnswer((AmericanQuestions) qr1.getQuestionArray()[indQuestion]);
                    }
                    break;
                }

                case 6: {

                    int numOfQuestionsInTheTest = 0;
                    boolean flagOfNumTest = false;
                    while (!flagOfNumTest) {
                        try {

                            System.out.println("type how many questions do you want in the test:");
                            numOfQuestionsInTheTest = input.nextInt();
                            if (numOfQuestionsInTheTest > 0 && numOfQuestionsInTheTest <= qr1.getNumberOfQuestions()) {
                                flagOfNumTest = true;
                            }

                        } catch (InputMismatchException e) {
                            System.out.println("Please enter a valid input(numbers only)");
                            input.nextLine();// cleans buffer
                        } catch (Exception e) {
                            System.out.println("Error " + e.getMessage());
                        }
                    }

                    int[][] indQuestion = new int[numOfQuestionsInTheTest][12];
                    // print all questions and their answers and show their Ind
                    System.out.println(qr1.toString());

                    for (int i = 0; i < numOfQuestionsInTheTest; i++) {

                        System.out.println(
                                "Hi please choose from below the id of the question from the list of the questions for the test:");
                        // let user choose a question id by input
                        int expId = exceptionId(qr1);
                        for (int j = 0; j < qr1.getNumberOfQuestions(); j++) {
                            if (expId == qr1.getQuestionArray()[j].getQuestionId()) {

                                if (qr1.getQuestionArray()[j] instanceof OpenQuestions) {
                                    indQuestion[i][0] = j;
                                }

                                if (qr1.getQuestionArray()[j] instanceof AmericanQuestions) {
                                    indQuestion[i][0]=j;
                                    System.out.println("Please enter how many answers from the american question you want");
                                    int numberOfAmericanAnswers = input.nextInt();
                                    indQuestion[i][1] = numberOfAmericanAnswers;
                                    for (int k = 0; k < numberOfAmericanAnswers; k++) {
                                        System.out.println("Choose the index of the answer you want to choose");
                                        indQuestion[i][k + 2] = input.nextInt();
                                        input.nextLine();
                                    }
                                }

                            }
                        }


                    }

                    qr1.manualExamCreate(numOfQuestionsInTheTest, indQuestion);
                    System.out.println(qr1.getExam().toString());


                    break;
                }

                case 7: {
                    int numberOfQuestions = 0;
                    try {
                        //Ask user how many questions he wants in the test
                        System.out.println("Please enter the number(between " + 1 + "-" + qr1.getNumberOfQuestions() + ")" + " of question you want in the test ");
                        try {
                            numberOfQuestions = input.nextInt();
                            while (numberOfQuestions < 0 || numberOfQuestions > qr1.getNumberOfQuestions()) {
                                System.out.println("chosen number of questions not within range please try again:");
                                input.nextLine();
                                numberOfQuestions = input.nextInt();
                            }

                        } catch (InputMismatchException e) {
                            System.out.println("Please enter a valid input(numbers only)");
                            input.nextLine();// cleans buffer

                        }


                    } catch (Exception e) {
                        System.out.println("Error " + e.getMessage());

                    }
                    QuestionReservoir automaticTest = new QuestionReservoir();
                   //TO DO
                    qr1.automaticExam(numberOfQuestions);

                    break;
                }


                case 8: {
                    System.out.println("Goodbye");
                    break;
                }

            }
        } while (optMenu != 8);
        input.close();
    }


    public static int exceptionId(QuestionReservoir qr) throws Exception {
        Scanner input = new Scanner(System.in);
        int expId = 0;
        boolean flagForWhile = false;

        while (!flagForWhile) {
            try {

                System.out.println("Please input a question id(from the list):");
                expId = input.nextInt();
                boolean flag = false;
                for (int i = 0; i < qr.getNumberOfQuestions(); i++) {
                    if (qr.getQuestionArray()[i].questionId == expId) {
                        flag = true;
                        return expId;
                    }

                }

                if (!flag) throw new Exception("Id not from the list");
                flagForWhile = true;
            } catch (InputMismatchException e) {
                System.out.println("Please put numbers only");
                input.nextLine();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                input.nextLine();
            }
        }
        return expId;
    }

    //a function that inputs a number of an american with exceptions
    public static int exceptionNumOfAnswer(QuestionReservoir qr, int expId) {
        Scanner input = new Scanner(System.in);
        int numberOfAnswer = 0;
        int QuestionInd = 0;

        //finding the chosen question by id
        for (int i = 0; i < qr.getQuestionArray().length; i++) {

            if (qr.getQuestionArray()[i] == null) {

            } else if (qr.getQuestionArray()[i].getQuestionId() == expId) {
                QuestionInd = i;
            }
        }
        //printing the answers to the user
        System.out.println("Please choose an answer by number");
        System.out.println(((AmericanQuestions) qr.getQuestionArray()[QuestionInd]).printAnswersOnly());
        //allowing user to choose within range (needs to be bigger than 0 and smaller than the answer array)
        while (numberOfAnswer <= 0 || numberOfAnswer > ((AmericanQuestions) qr.getQuestionArray()[QuestionInd]).getAnswerArray().length) {

            try {
                numberOfAnswer = input.nextInt();
                if (numberOfAnswer <= 0 || numberOfAnswer > (((AmericanQuestions) qr.getQuestionArray()[QuestionInd]).getAnswerArray().length))
                    throw new Exception("Number not within answers range");

            }
            //self explenatory
            catch (InputMismatchException e) {
                System.out.println("please enter an integer");
                input.nextLine();

            }
            //checks if enterd answer index is withing range
            catch (Exception e) {
                System.out.println("Entered number not within range of answer" + "(" + 1 + "-" + ((AmericanQuestions) qr.getQuestionArray()[QuestionInd]).getAnswerArray().length + ")");
                input.nextLine();
            }

        }
        return numberOfAnswer;
    }

    //a function that inputs if the question is 1 or 2 and return the select
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

    //a function that inputs if the question is true or false
    public static boolean exceptionIsAmericanTrueOrFalse() {
        Scanner input = new Scanner(System.in);

        boolean FlagOfAnswerOpt = false;
        int optB = 0;
        while (!FlagOfAnswerOpt) {
            try {
                System.out.println("type-1 for True/ type-0 for False");
                optB = input.nextInt();
                if (optB == 0 || optB == 1) {
                    FlagOfAnswerOpt = true;
                }


            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid input(numbers only)");
                input.nextLine();// cleans buffer
            } catch (Exception e) {
                System.out.println("Error " + e.getMessage());
            }
        }
        if (optB == 1) {

            return true;
        }
        if (optB == 0) {
            return false;

        }
        return true;
    }

    //function that asks user if to add an "all answers are false" or "more than 1 answer is true"
    public static boolean askUserIftoAddAnswer(AmericanQuestions americanQuestion) {
        Scanner input = new Scanner(System.in);
        //asks user if he want to add an "All answers are false" or "More than 1 answer is true option"
        if (americanQuestion.counterTrueFalse(americanQuestion.getAnswerArray()) == 2) {
            System.out.println("All answers are false would you like to add an 'All answers are false answers ?'");
            System.out.println("to add enter 1,otherwise enter 0");
            boolean validInputFlag = false;
            while (!validInputFlag) {
                try {
                    int toAdd = input.nextInt();
                    if (toAdd == 1 || toAdd == 0) {
                        validInputFlag = true;
                    }
                    if (toAdd == 1) {
                        americanQuestion.checkAnswerArrays(americanQuestion.getAnswerArray());
                        System.out.println("Added 'All answers are false' answer");
                        return true;
                    } else {
                        System.out.println("Didn't add 'all answers are false' answer");
                        return false;
                    }
                } catch (InputMismatchException e) {
                    System.out.println(e.getMessage());
                    input.nextLine();
                } catch (Exception e) {
                    System.out.println(e.getMessage());

                }
            }
        }
        //asks user if he wants to add an "more than 1 answer is true" answer
        if (americanQuestion.counterTrueFalse(americanQuestion.getAnswerArray()) == 3) {
            System.out.println("more than 1 answer is true would you like to add an 'More than 1 answer is true ?'");
            System.out.println("to add enter 1,otherwise enter 0");
            boolean validInputFlag = false;
            while (!validInputFlag) {
                try {
                    int toAdd = input.nextInt();
                    if (toAdd == 1 || toAdd == 0) {
                        validInputFlag = true;
                    }
                    if (toAdd == 1) {
                        americanQuestion.checkAnswerArrays(americanQuestion.getAnswerArray());
                        System.out.println("'More than 1 answer is true' added");
                        return true;
                    } else {
                        System.out.println("Didn't add 'More than 1 answer is true' answer");
                        return false;
                    }
                } catch (InputMismatchException e) {
                    System.out.println(e.getMessage());
                    input.nextLine();
                } catch (Exception e) {
                    System.out.println(e.getMessage());

                }
            }
        }
        return false;
    }


}
