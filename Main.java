package id206214280_id316650399;

import java.io.*;
import java.util.*;

public class Main implements Examble {

    public static void main(String[] args)
            throws Exception, FileNotFoundException, IOException, ClassNotFoundException {
//		QuestionReservoir qr1 = new QuestionReservoir();
//		// open question #1
//		qr1.addOpenQuestion("A Who was Yitzhak Rabin?", "Israeli prime minister");
//		// open question #2
//		qr1.addOpenQuestion("B Who solved the Engima machine during WWII?", "Alan Turing");
//		// open question #3
//		qr1.addOpenQuestion("C What is the difference between Choclate cake and a Salad?",
//				"Salad wont give you Diabetes");
//		// american question #1
//		String aq1 = ("D Which one of these is not dessert?");
//		String[] aa1 = new String[] { "Muffin", "Choclate cake", "Ice cream", "Brownies", "Salad", "Burger", "Shawrma",
//				"Pizza" };
//		boolean[] tof1 = new boolean[] { false, false, false, false, true, true, true, true };
//		qr1.addAmericanQuestion(aq1, aa1, tof1);
//		// american question #2
//		String aq2 = "E Which one of these next Programming languages is Low-Level programming language?";
//		String[] aa2 = new String[] { "Java", "C++", "Assembly", "C", "C#", "Python", "JavaScript", "Swift",
//				"Machine Code" };
//		boolean[] tof2 = new boolean[] { false, false, true, false, false, false, false, false, true };
//		qr1.addAmericanQuestion(aq2, aa2, tof2);
//		System.out.println(qr1.getQuestionArray().size());
//		qr1.saveBin();
//
//		ArrayList<Integer> indexOfAnswer = new ArrayList<Integer>();
//		ArrayList<ArrayList<Integer>> indexOfQuestion = new ArrayList<>();

        QuestionReservoir qr1 = new QuestionReservoir();
        qr1.readBin();

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
            System.out.println("8-Clone an exam");
            System.out.println("9-Save changes in questions reservoir and exit program");
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
                    main1.printQuestions(qr1);
                    break;
                }

                // option 2 add questions
                case 2: {
                    main1.addQuestion(qr1, input);
                    break;
                }

                // change the wording of a question
                case 3: {
                    main1.updateQuestionWording(qr1);
                    break;

                }

                // change the wording of an answer
                case 4: {
                    main1.updateAnswerWording(qr1);
                }

                break;

                // delete an answer
                case 5: {
                    // Cant delete an answer for open question
                    // Can only delete up until 2 answers for american questions
                    // american question needs at least 1 true answer
                    // if american question has more than 1 correct answer than it needs at least 1
                    // false answer
                    // if american question has 2 answers,1 of them needs to be true and the second
                    // needs to be true
                    // if american question has only 1 and user wants to delete it add another
                    // answer (none of the answers is correct)
                    // let user choose an id of the question it wants to delete
                    main1.deleteAnswer(qr1);
                    break;
                }

                case 6: {

                    main1.createManualExam(qr1);

                    break;
                }

                case 7: {
                    main1.createAutomaticExam(qr1);

                    break;
                }

                case 8: {
                    main1.cloneExam(qr1);

                    break;
                }

                case 9: {
                    main1.saveBinary(qr1);
                    System.out.println("Goodbye");

                    break;
                }

            }
        } while (optMenu != 9);
        input.close();
    }

    @Override
    public void printQuestions(QuestionReservoir qr1) {
        System.out.println(qr1.toString());
    }

    @Override
    public void addQuestion(QuestionReservoir qr1, Scanner input) {
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
            boolean canOrNot = qr1.addOpenQuestion(questionText, OpenAnswer);
            if (canOrNot) {
                System.out.println("Added question ");

            } else {
                System.out.println("your question Already exists");
            }

        }
        // add american answer
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
            }
            qr1.addAmericanQuestion(americanQuestionText, americanAnswersText, americanAnswersCorrectness);
            askUserIftoAddAnswer(qr1, qr1.getNumberOfQuestions() - 1);

        }
    }

    @Override
    public void updateQuestionWording(QuestionReservoir qr1) throws Exception {
        System.out.println("Please choose which question would you like to change from the list below:");
        // print all questions an show their Id
        for (int i = 0; i < qr1.getNumberOfQuestions(); i++) {
            System.out.print("id is:(" + qr1.getQuestionArray().get(i).getQuestionId() + ")" + " ");
            System.out.println("Question text is: " + qr1.getQuestionArray().get(i).getQuestionText());
        }
        // let user choose a question id by input
        int expId = exceptionId(qr1);
        System.out.println("Please enter the new wording for the text:");

        String newQuestionText = input.nextLine();
        qr1.changeQuestionWording(newQuestionText, expId);
        // check array to see if adding "All answers are false" or "more than 1 answer
        // is true needs to be added"

    }

    @Override
    public void updateAnswerWording(QuestionReservoir qr1) throws Exception {
        System.out.println("Hi please choose from below the id of the Answer you want to change the wording of");
        // print all questions and their answers and show their Ind
        System.out.println(qr1.toString());
        // let user choose a question id by input
        int expId = exceptionId(qr1);
        int indQuestion = 0;
        int numOfAnswer = 0;

        for (int i = 0; i < qr1.getNumberOfQuestions(); i++) {
            if (expId == qr1.getQuestionArray().get(i).getQuestionId()) {
                indQuestion = i;
            }
        }
        if (qr1.getQuestionArray().get(indQuestion) instanceof AmericanQuestions) {
            boolean flagNumOfAnswer = false;
            qr1.getQuestionArray().get(indQuestion).toString();
            numOfAnswer = exceptionNumOfAnswer(qr1, expId);

            System.out.println("Please enter a new wording for the answer:");

            String newWordingForAmericanAnswer = input.nextLine();
            boolean newAnswerOpt = exceptionIsAmericanTrueOrFalse();
            qr1.changeAnswerWordingOfAmericanQuestions(newWordingForAmericanAnswer,
                    (AmericanQuestions) qr1.getQuestionArray().get(indQuestion), numOfAnswer - 1, newAnswerOpt);
            askUserIftoAddAnswer(qr1, indQuestion);

        }
        if ((qr1.getQuestionArray().get(indQuestion) instanceof OpenQuestions)) {
            System.out.println("type in a new answer wording ");

            String newAnswer = input.nextLine();
            if (qr1.changeAnswerWordingOfOpenQuestion(newAnswer, qr1.getQuestionArray().get(indQuestion),
                    numOfAnswer)) {
                System.out.println("the answer wording changed");
            } else {
                System.out.println("failed");
            }
        }
    }

    @Override
    public void deleteAnswer(QuestionReservoir qr1) throws Exception {
        // Cant delete an answer for open question
        // Can only delete up until 2 answers for american questions
        // american question needs at least 1 true answer
        // if american question has more than 1 correct answer than it needs at least 1
        // false answer
        // if american question has 2 answers,1 of them needs to be true and the second
        // needs to be true
        // if american question has only 1 and user wants to delete it add another
        // answer (none of the answers is correct)
        // let user choose an id of the question it wants to delete
        System.out.println(qr1.toString());
        int userGivenQuestionId = exceptionId(qr1);
        // find the question index and check its type
        int indQuestion = 0;
        boolean americanQuestionFlag = true;
        for (int i = 0; i < qr1.getNumberOfQuestions(); i++) {
            if (qr1.getQuestionArray().get(i).getQuestionId() == userGivenQuestionId) {
                if (qr1.getQuestionArray().get(i) instanceof OpenQuestions) {
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
            // choosing the answer number
            boolean flagCountAmericanAns = false;

            System.out.println(((AmericanQuestions) qr1.getQuestionArray().get(indQuestion)).printAnswersOnly());
            System.out.println(
                    "" + ((AmericanQuestions) qr1.getQuestionArray().get(indQuestion)).getNumOfAmericanAnswers() + "");
            while (!flagCountAmericanAns) {
                try {
                    System.out.println("Please choose the answer you want to delete");
                    answerNumber = input.nextInt();
                    if (answerNumber > 0
                            && answerNumber <= ((AmericanQuestions) qr1.getQuestionArray().get(indQuestion))
                            .getNumOfAmericanAnswers()) {
                        flagCountAmericanAns = true;
                    }

                } catch (InputMismatchException e) {
                    System.out.println(e.getMessage());
                } catch (Exception e) {
                    System.out.println("Error " + e.getMessage());
                }
            }

            // sending parametes to the function (Question Index and Answer number)
            qr1.deleteAmericanAnswer(indQuestion, (answerNumber - 1));
            askUserIftoAddAnswer(qr1, indQuestion);
        }
    }

    // seif6
    @Override
    public void createManualExam(QuestionReservoir qr1) throws Exception {
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
        // this Arraylist hold the index of the question in the head of the list
        // if the question is American what comes after the 0 elements are the answers
        // indexes
        ArrayList<Integer> indexesOfQuestionsAndIndexesOfAnswers = new ArrayList<Integer>();
        // this list holds arrays of the indexes
        ArrayList<ArrayList<Integer>> manualQuestionArrayList = new ArrayList<ArrayList<Integer>>();

        // print all questions and their answers and show their Ind
        System.out.println(qr1.toString());

        for (int i = 0; i < numOfQuestionsInTheTest; i++) {
            System.out.println(
                    "Hi please choose from below the id of the question from the list of the questions for the test:");
            // let user choose a question id by input
            int expId = exceptionId(qr1);
            for (int j = 0; j < qr1.getNumberOfQuestions(); j++) {
                if (expId == qr1.getQuestionArray().get(j).getQuestionId()) {

                    if (qr1.getQuestionArray().get(j) instanceof OpenQuestions) {
                        int questionIndex = j;
                        indexesOfQuestionsAndIndexesOfAnswers.add(questionIndex);
                        manualQuestionArrayList.add(i, indexesOfQuestionsAndIndexesOfAnswers);
                        indexesOfQuestionsAndIndexesOfAnswers = new ArrayList<Integer>();

                    }

                    if (qr1.getQuestionArray().get(j) instanceof AmericanQuestions) {
                        int questionIndex = j;
                        indexesOfQuestionsAndIndexesOfAnswers.add(questionIndex);

                        boolean numOfAns = false;
                        int numberOfAmericanAnswers = 0;
                        int numbersOfAns = qr1.takeNumOfAnswers(qr1, j);
                        while (!numOfAns) {
                            try {
                                System.out.println("Please enter how many answers from the american question you want");
                                numberOfAmericanAnswers = input.nextInt();
                                if (numberOfAmericanAnswers > 0 && numberOfAmericanAnswers <= numbersOfAns) {
                                    numOfAns = true;
                                }

                            } catch (InputMismatchException e) {
                                System.out.println("Please enter a valid input(numbers only)");
                                input.nextLine();// cleans buffer
                            } catch (Exception e) {
                                System.out.println("Error " + e.getMessage());
                            }
                        }

                        for (int k = 0; k < numberOfAmericanAnswers; k++) {
                            boolean flagIndexAns = false;
                            while (!flagIndexAns) {
                                ((AmericanQuestions) qr1.getQuestionArray().get(j)).printAnswersOnly();
                                try {
                                    System.out.println(
                                            "Choose index of the answer " + (k + 1) + "/" + numberOfAmericanAnswers);
                                    int americanAnswerIndex = input.nextInt();

                                    if (americanAnswerIndex > 0 && americanAnswerIndex <= ((AmericanQuestions) qr1
                                            .getQuestionArray().get(j)).getAnswerArray().size()) {
                                        indexesOfQuestionsAndIndexesOfAnswers.add(americanAnswerIndex - 1);
                                        flagIndexAns = true;
                                    }
                                } catch (InputMismatchException e) {
                                    System.out.println("Please enter a valid input(numbers only)");
                                    input.nextLine();// cleans buffer
                                } catch (Exception e) {
                                    System.out.println("Error " + e.getMessage());
                                }
                            }
                        }
                        manualQuestionArrayList.add(i, indexesOfQuestionsAndIndexesOfAnswers);
                        indexesOfQuestionsAndIndexesOfAnswers = new ArrayList<Integer>();
                    }
                }
            }
        }

        qr1.manualExamCreate(numOfQuestionsInTheTest, manualQuestionArrayList);
        System.out.println(qr1.getManualExam().toString());

    }

    @Override
    public void createAutomaticExam(QuestionReservoir qr1) throws FileNotFoundException, InputMismatchException {
        int numberOfQuestions = 0;
        boolean flagInput = false;
        while (!flagInput) {

            // Ask user how many questions he wants in the test
            try {

                System.out.println("Please enter the number(between " + 1 + "-" + qr1.getNumberOfQuestions() + ")"
                        + " of question you want in the test ");
                numberOfQuestions = input.nextInt();
                if (numberOfQuestions >= 0 && numberOfQuestions <= qr1.getNumberOfQuestions()) {
                    flagInput = true;

                } else {
                    System.out.println("chosen number of questions not within range please try again:");
                }

            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid input(numbers only)");
                input.nextLine();// cleans buffer

            }
        }

        qr1.automaticExam(numberOfQuestions);
    }

    @Override
    public void cloneExam(QuestionReservoir qr1) throws CloneNotSupportedException, FileNotFoundException {
        System.out.println("Please choose which exam to clone");
        System.out.println("1-manual exam");
        System.out.println("2-automatic exam");
        int option = exception1Or2Select();
        qr1.cloneExam(option);

    }

    @Override
    public void saveBinary(QuestionReservoir qr1) throws IOException, ClassNotFoundException {
        System.out.println("Question reservoir saved");
        qr1.saveBin();
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
                    if (qr.getQuestionArray().get(i).questionId == expId) {
                        flag = true;
                        return expId;
                    }

                }

                if (!flag)
                    throw new Exception("Id not from the list");
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

    // a function that inputs a number of an american with exceptions
    public static int exceptionNumOfAnswer(QuestionReservoir qr, int expId) {
        Scanner input = new Scanner(System.in);
        int numberOfAnswer = 0;
        int QuestionInd = 0;

        // finding the chosen question by id
        for (int i = 0; i < qr.getQuestionArray().size(); i++) {

            if (qr.getQuestionArray().get(i) == null) {

            } else if (qr.getQuestionArray().get(i).getQuestionId() == expId) {
                QuestionInd = i;
            }
        }
        // printing the answers to the user
        System.out.println("Please choose an answer by number");
        System.out.println(qr.getQuestionArray().get(QuestionInd));
        // allowing user to choose within range (needs to be bigger than 0 and smaller
        // than the answer array)
        while (numberOfAnswer <= 0 || numberOfAnswer > ((AmericanQuestions) qr.getQuestionArray().get(QuestionInd))
                .getAnswerArray().size()) {

            try {
                numberOfAnswer = input.nextInt();
                if (numberOfAnswer <= 0 || numberOfAnswer > ((AmericanQuestions) qr.getQuestionArray().get(QuestionInd))
                        .getAnswerArray().size())
                    throw new Exception("Number not within answers range");

            }
            // self explenatory
            catch (InputMismatchException e) {
                System.out.println("please enter an integer");
                input.nextLine();

            }
            // checks if enterd answer index is withing range
            catch (Exception e) {
                System.out.println("Entered number not within range of answer" + "(" + 1 + "-"
                        + ((AmericanQuestions) qr.getQuestionArray().get(QuestionInd)).getAnswerArray().size() + ")");
                input.nextLine();
            }

        }
        return numberOfAnswer;
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

    // a function that inputs if the question is true or false
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

    // function that asks user if to add an "all answers are false" or "more than 1
    // answer is true"
    public static boolean askUserIftoAddAnswer(QuestionReservoir qr1, int americanIndex) {
        Scanner input = new Scanner(System.in);
        // asks user if he want to add an "All answers are false" or "More than 1 answer
        // is true option"
        if (qr1.getQuestionArray().get(americanIndex) instanceof AmericanQuestions) {
            AmericanQuestions americanQuestion = (AmericanQuestions) qr1.getQuestionArray().get(americanIndex);

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
            // asks user if he wants to add an "more than 1 answer is true" answer
            if (americanQuestion.counterTrueFalse(americanQuestion.getAnswerArray()) == 3) {
                System.out
                        .println("more than 1 answer is true would you like to add an 'More than 1 answer is true ?'");
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
        }
        return false;
    }

}
