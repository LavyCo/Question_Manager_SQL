package id206214280_id316650399;

import java.util.Arrays;
import java.util.Objects;

public class QuestionReservoir {

    private int numberOfQuestions = 0;
    private int size = 1;
    private Questions[] questionArray;
    private Exam manualExam;
    private Exam automaticExam;


    public QuestionReservoir() {

        questionArray = new Questions[size];

    }

    public void resizeQuestionArray() {
        Questions[] newQuestionsArray = new Questions[size * 2];
        for (int i = 0; i < numberOfQuestions; i++) {
            newQuestionsArray[i] = questionArray[i];
        }
        this.questionArray = newQuestionsArray;
    }


    public boolean changeAnswerWordingOfOpenQuestion(String newAnswerText, Questions editorQuestionAnswer, int numOfAnswer) {
        if (editorQuestionAnswer instanceof OpenQuestions) {
            if (((OpenQuestions) editorQuestionAnswer).getAnswerText().equalsIgnoreCase(newAnswerText)) {
                System.out.println("Can't change Answer text-There a Answer with the same text");

                return false;
            } else {
                ((OpenQuestions) editorQuestionAnswer).setAnswerText(newAnswerText);
                return true;
            }

        }
        return false;
    }

    public void sortQuestionsByLexicographicOrder() {
        boolean noChange = false;
        for (int i = this.getNumberOfQuestions() - 1; i > 0 && (noChange == false); i--) {
            for (int j = 0; j < i; j++) {
                noChange = true;
                if (questionArray[j].getQuestionText().compareToIgnoreCase(questionArray[j + 1].getQuestionText()) > 0) {
                    Questions tempQuestion = questionArray[j + 1];
                    questionArray[j + 1] = questionArray[j];
                    questionArray[j] = tempQuestion;
                    noChange = false;
                }
            }
        }

    }


    public boolean changeAnswerWordingOfAmericanQuestions(String newAnswerText, AmericanQuestions editorQuestionAnswer, int numOfAnswer, boolean opt) {


        if (editorQuestionAnswer instanceof AmericanQuestions) {

            for (int i = 0; i < (editorQuestionAnswer).getNumOfAmericanAnswers(); i++) {

                if ((editorQuestionAnswer).getAnswerArray()[i].getAnswerText().equalsIgnoreCase(newAnswerText)) {
                    System.out.println("Can't change Answer text-There's an Answer with the same text");
                    return false;
                }
            }

            (editorQuestionAnswer).getAnswerArray()[numOfAnswer].setCorrectness(opt);
            (editorQuestionAnswer).getAnswerArray()[numOfAnswer].setAnswerText(newAnswerText);
            return true;
        }
        return false;
    }


    public boolean changeQuestionWording(String newQuestionText, int choosenId) {
        // loop that checks if the question exists
        Questions testQuestionText = new Questions(newQuestionText);
        for (int i = 0; i < this.numberOfQuestions; i++) {
            if (testQuestionText.equals(this.questionArray[i])) {
                System.out.println("Can't change question text-There a Question with the same text");
                return false;
            }

        }

        for (int i = 0; i < this.numberOfQuestions; i++) {
            if (this.questionArray[i].questionId == choosenId) {
                this.questionArray[i].setQuestionText(newQuestionText);
            }

        }
        System.out.println("Question wording changed succesfully!");
        return true;
    }

    public void automaticExam(int numberOfQuestions) {
        Exam randomExam = new Exam(numberOfQuestions);
        Questions[] newQuestionArray = new Questions[numberOfQuestions];
        Questions[] qrQuestionArr = this.getQuestionArray();
        int[] randomIndex = randomNumbersArray(numberOfQuestions);
        for (int i = 0; i < numberOfQuestions; i++) {
            //r random index
            int r = randomIndex[i];

            if (qrQuestionArr[r] instanceof OpenQuestions) {
                newQuestionArray[i] = new OpenQuestions((OpenQuestions) qrQuestionArr[r]);
                randomExam.getQuestionsExamArray()[i] = newQuestionArray[i];
            }


             if (qrQuestionArr[r] instanceof AmericanQuestions) {
                int americanAnswersSize = ((AmericanQuestions) qrQuestionArr[r]).getNumOfAmericanAnswers();
                AmericanQuestions randomAmericanQuestion = ((AmericanQuestions) qrQuestionArr[r]);
                AmericanQuestions automaticAmericanQuestion = new AmericanQuestions(randomAmericanQuestion.questionText, randomAmericanQuestion.getNumOfAmericanAnswers(), randomAmericanQuestion.getAnswerArray());

                int[] randomAnswerIndex = randomNumbersArray(americanAnswersSize);
                AmericanAnswer[] automaticAmericanAnswersArray = new AmericanAnswer[4];
                int loopSize=4;
                int trueCounter = 0;
                int t=0;
                for (int j = 0; j < americanAnswersSize&&t<4; j++) {
                        int p = randomAnswerIndex[j];

                        if (randomAmericanQuestion.getAnswerArray()[p].getCorrectness() && trueCounter != 1) {
                            System.out.println(randomAmericanQuestion.getAnswerArray()[p]);
                            automaticAmericanAnswersArray[t] = new AmericanAnswer(randomAmericanQuestion.getAnswerArray()[p]);
                            t++;
                            trueCounter = 1;
                        } else if (!randomAmericanQuestion.getAnswerArray()[p].getCorrectness()) {
                            System.out.println(randomAmericanQuestion.getAnswerArray()[p]);
                            automaticAmericanAnswersArray[t] = new AmericanAnswer(randomAmericanQuestion.getAnswerArray()[p]);
                            t++;

                        } else if (randomAmericanQuestion.getAnswerArray()[p].getCorrectness() && trueCounter == 1) {
                            int k=j;
                            boolean foundFalse=false;
                            k++;
                            while(!foundFalse){
                                p=randomAnswerIndex[k];
                                if(!randomAmericanQuestion.getAnswerArray()[p].getCorrectness()){
                                    automaticAmericanAnswersArray[t]=new AmericanAnswer(randomAmericanQuestion.getAnswerArray()[p]);
                                    t++;
                                    j=k;
                                    foundFalse=true;
                                }
                                else{
                                k++;}

                            }

                        }

                }

                automaticAmericanQuestion.setQuestionText(randomAmericanQuestion.getQuestionText());
                automaticAmericanQuestion.setNumOfAmericanAnswers(4);
                automaticAmericanQuestion.setAnswerArray(automaticAmericanAnswersArray);
                automaticAmericanQuestion.Add2Answers();
                newQuestionArray[i] = new AmericanQuestions(automaticAmericanQuestion);


                 randomExam.getQuestionsExamArray()[i] = newQuestionArray[i];
            }


        }
        this.automaticExam = new Exam(randomExam);
        System.out.println(automaticExam.toString());

    }


    public boolean isAnswerInArray(AmericanAnswer americanAnswer, AmericanAnswer[] americanAnswers) {
        for (int i = 0; americanAnswers[i] != null; i++) {
            if (americanAnswers[i].equals(americanAnswer)) {
                System.out.println("Answer is in the array");
                return true;
            }
        }
        System.out.println("Answer isn't in the array");
        return false;
    }

    public boolean addOpenQuestion(String questionText, String answerText) {
        OpenQuestions newQuestion = new OpenQuestions(questionText, answerText);
        if (this.equals(newQuestion.getQuestionText())) {
            System.out.println("Cannot add:This question is already in the reservoir");
            //decreasing id counter by 1
            newQuestion.decreaseIdCounter();
            return false;
        }


        if (newQuestion instanceof OpenQuestions) {
            Questions newOpenQuestion = newQuestion;
            for (int i = 0; i < numberOfQuestions; i++) {
                if (questionArray[i].equals(newOpenQuestion)) {
                    System.out.println("Cannot add:This question is already in the reservoir");
                    //decreasing id counter by 1
                    newQuestion.decreaseIdCounter();
                    return false;
                }

            }

            if (numberOfQuestions == size) {
                resizeQuestionArray();
                questionArray[numberOfQuestions++] = newOpenQuestion;
                size *= 2;
            } else {
                questionArray[numberOfQuestions++] = newOpenQuestion;
            }
            return true;

        }
        return false;
    }

    public void sortAmericanAnswers(AmericanQuestions americanQuestions) {

    }

    public boolean addAmericanQuestion(String questionText, String[] answersArray, boolean[] correctnessArray) {

        AmericanAnswer[] newAmericanAnswersArray = new AmericanAnswer[answersArray.length];
        for (int i = 0; i < newAmericanAnswersArray.length; i++) {
            newAmericanAnswersArray[i] = new AmericanAnswer("temp", true);
        }
        for (int i = 0; i < answersArray.length; i++) {
            newAmericanAnswersArray[i].setAnswerText(answersArray[i]);
            newAmericanAnswersArray[i].setCorrectness(correctnessArray[i]);
        }
        AmericanQuestions newAmericanQuestion = new AmericanQuestions(questionText, newAmericanAnswersArray.length, newAmericanAnswersArray);

        if (numberOfQuestions == size) {
            resizeQuestionArray();
            questionArray[numberOfQuestions++] = newAmericanQuestion;
            size *= 2;
        } else {
            questionArray[numberOfQuestions++] = newAmericanQuestion;
        }
        System.out.println("American Question added");
        return true;


    }


    public boolean deleteAmericanAnswer(int indQuestion, int answerNumber) {
        AmericanAnswer[] originalAnswerArr = ((AmericanQuestions) this.getQuestionArray()[indQuestion]).getAnswerArray();

        AmericanQuestions americanQuestion = ((AmericanQuestions) this.getQuestionArray()[indQuestion]);
        int newNumberOfAnswers = originalAnswerArr.length;
        if (americanQuestion.counterTrueFalse(originalAnswerArr) == 1) {
            System.out.println("Can't delete must have 2 negated answers");
            return false;
        }

        if (americanQuestion.getNumOfAmericanAnswers() <= 2) {
            System.out.println("Cant delete answer minimum answers required");
            return false;
        }


        //creating new array of answer for american question with reduced number of answers
        newNumberOfAnswers--;
        AmericanAnswer[] newAmericanAnswerArray = new AmericanAnswer[newNumberOfAnswers];

        for (int i = 0; i < answerNumber; i++) {
            newAmericanAnswerArray[i] = originalAnswerArr[i];
            System.out.println(newAmericanAnswerArray[i].toString());
        }

        for (int j = answerNumber; j < newNumberOfAnswers; j++) {
            newAmericanAnswerArray[j] = originalAnswerArr[j + 1];
            System.out.println(newAmericanAnswerArray[j].toString());

        }


        //reducing number of american answer for the question by 1
        americanQuestion.setNumOfAmericanAnswers(newNumberOfAnswers);

        //configuring new answer array
        americanQuestion.setAnswerArray(newAmericanAnswerArray);

        return true;

    }


    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }


    public Questions[] getQuestionArray() {
        return questionArray;
    }


    public void manualExamCreate(int numOfQuestInTest, int[][] indQuestion) {
        Exam manualExam = new Exam(numOfQuestInTest);

        for (int arrayIndex = 0; arrayIndex < numOfQuestInTest; arrayIndex++) {
            for (int allQuestionsIndex = 0; allQuestionsIndex < numberOfQuestions; allQuestionsIndex++) {

                if (this.questionArray[allQuestionsIndex].questionId == indQuestion[arrayIndex][0] + 1) {
                    if (this.questionArray[allQuestionsIndex] instanceof OpenQuestions) {
                        OpenQuestions newOpenQuestions = new OpenQuestions((OpenQuestions) this.questionArray[allQuestionsIndex]);
                        manualExam.getQuestionsExamArray()[arrayIndex] = newOpenQuestions;
                    }
                    if (this.questionArray[allQuestionsIndex] instanceof AmericanQuestions) {

                        AmericanQuestions americanQuestion = ((AmericanQuestions) this.getQuestionArray()[allQuestionsIndex]);
                        AmericanAnswer[] newAnswerArr = new AmericanAnswer[indQuestion[arrayIndex][1]];

                        for (int americanAnswerIndex = 0; americanAnswerIndex < indQuestion[arrayIndex][1]; americanAnswerIndex++) {
                            AmericanAnswer newAnswer = new AmericanAnswer(americanQuestion.getAnswerArray()[indQuestion[arrayIndex][americanAnswerIndex + 2] - 1]);
                            newAnswerArr[americanAnswerIndex] = newAnswer;
                        }
                        AmericanQuestions newAmericanQuestion = new AmericanQuestions(americanQuestion.questionText, newAnswerArr.length, newAnswerArr);
                        manualExam.getQuestionsExamArray()[arrayIndex] = newAmericanQuestion;
                    }

                }
            }
        }
        this.manualExam = new Exam(manualExam);
        System.out.println("Manual exam created successfully !");

    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(questionArray);
        result = prime * result + Objects.hash(numberOfQuestions);
        return result;
    }

    //checks if there is the same question in the array
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Questions)) {
            return false;
        }
        Questions p = (Questions) other;
        for (int i = 0; i < numberOfQuestions; i++) {

            return this.questionArray[i].equals(p);


        }
        return false;
    }

    public static int[] randomNumbersArray(int size) {
        int[] numArray = new int[size];
        int i = 0;
        //creating number array
        while (i < size) {
            numArray[i] = (i);
            i++;
        }
        //creating random number array
        boolean hasChanged = false;
        int[] randomNumberArray = new int[size];
        //configuring random numbers array
        for (i = 0; i < size; i++) {
            hasChanged = false;
            int randomIndex = (int) (Math.random() * size);
            do {
                if (numArray[randomIndex] != -1) {
                    randomNumberArray[i] = numArray[randomIndex];
                    numArray[randomIndex] = -1;
                    hasChanged = true;
                } else {
                    randomIndex = (int) (Math.random() * size);
                }
            } while (!hasChanged);
        }
        return randomNumberArray;


    }

    public Exam getExam() {
        return manualExam;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("The number of questions in the Reservoir is: " + numberOfQuestions + "\n"
                + "\nThe questions are:\n");
        for (int i = 0; i < numberOfQuestions; i++) {
            sb.append(questionArray[i].toString());
        }
        return sb.toString();
    }


    {

    }

}

