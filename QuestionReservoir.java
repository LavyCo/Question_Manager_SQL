package id206214280_id316650399;

import java.util.Arrays;
import java.util.Objects;

public class QuestionReservoir {

    private int numberOfQuestions = 0;
    private int size = 1;
    private Questions[] questionArray;
    private Exam exam;


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

    public void automaticExam(QuestionReservoir qr1,int numberOfQuestions){
        //the program will choose question randomly while for american question there will 4 answers
        //there can't be more than 1 true question but there can be "All answers are false" answer
        Questions[] randomQuestionsArray = new Questions[numberOfQuestions];
        QuestionReservoir automaticTest = new QuestionReservoir();
        //loop that generates random numbers array
        int[] randomNumberArray = randomNumbersArray(numberOfQuestions);
        //configuring questions according to the random array index's
        for (int i = 0; i < numberOfQuestions; i++) {
            if (qr1.getQuestionArray()[randomNumberArray[i]] instanceof AmericanQuestions) {
                //originalAmericanQuestion[i]
                AmericanQuestions randomAmericanQuestion = (AmericanQuestions) qr1.getQuestionArray()[randomNumberArray[i]];
                String autoAmericanQuestionText = randomAmericanQuestion.getQuestionText();

                //Configuring random index array for american answers
                int[] randomAnswersIndex = randomNumbersArray(randomAmericanQuestion.getNumOfAmericanAnswers());
                AmericanAnswer[] randomAmericanAnswers = randomAmericanQuestion.getAnswerArray();
                AmericanAnswer[] autoAmericanAnswers = new AmericanAnswer[6];
                boolean onlyOneTrueAnswer = false;
                int j=0;
                for (j = 0; j < 4&&j<randomAmericanAnswers.length; j++) {
                    if (!onlyOneTrueAnswer && randomAmericanAnswers[j].getCorrectness()) {
                        autoAmericanAnswers[j] = new AmericanAnswer(randomAmericanAnswers[j].getAnswerText(), randomAmericanAnswers[j].getCorrectness());
                        onlyOneTrueAnswer = true;
                    } else if (onlyOneTrueAnswer && randomAmericanAnswers[j].getCorrectness()) {
                        boolean foundFalse = false;
                        int k = j;
                        while (!foundFalse&&k<randomAmericanAnswers.length&&k<4) {
                            k++;
                            if (!randomAmericanAnswers[k].getCorrectness()) {
                                autoAmericanAnswers[j] = new AmericanAnswer(randomAmericanAnswers[k].getAnswerText(), randomAmericanAnswers[k].getCorrectness());
                                foundFalse = true;
                            }
                        }
                    }

                }if (onlyOneTrueAnswer = false) {
                    autoAmericanAnswers[j] = new AmericanAnswer("All answers are false", true);
                }else {
                    autoAmericanAnswers[j] = new AmericanAnswer("All answers are false", false);
                }
                autoAmericanAnswers[j+1]=new AmericanAnswer("More than 1 answer is true",false);
                AmericanQuestions autoAmericanQuestion=new AmericanQuestions(autoAmericanQuestionText,6,autoAmericanAnswers);
                randomQuestionsArray[i]=autoAmericanQuestion;
            }
            if (qr1.getQuestionArray()[randomNumberArray[i]] instanceof OpenQuestions) {
                OpenQuestions autoOpenQuestion=new OpenQuestions(((OpenQuestions)qr1.getQuestionArray()[randomNumberArray[i]]).getQuestionText(),((OpenQuestions)qr1.getQuestionArray()[randomNumberArray[i]]).getAnswerText());
                randomQuestionsArray[i]=autoOpenQuestion;

            }

        } for (int i=0;i<randomQuestionsArray.length;i++){
            addQuestion(randomQuestionsArray[i]);
        }


    }
    public  OpenQuestions createOpenQuestion(String questionText,String answerText){
        OpenQuestions newOpenQuestion =new OpenQuestions( questionText, answerText);

        return newOpenQuestion;
    }

    public boolean addQuestion(Questions newQuestion) {


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
        if (newQuestion instanceof AmericanQuestions) {
            Questions newAmericanQuestion = newQuestion;
            for (int i = 0; i < numberOfQuestions; i++) {
                if (questionArray[i].equals(newAmericanQuestion)) {
                    //decreasing id counter by 1
                    newQuestion.decreaseIdCounter();
                    System.out.println("Cannot add:This question is already in the reservoir");
                    return false;
                }

            }

            if (numberOfQuestions == size) {
                resizeQuestionArray();
                questionArray[numberOfQuestions++] = newAmericanQuestion;
                size *= 2;
            } else {
                questionArray[numberOfQuestions++] = newAmericanQuestion;
            }
            return true;
        }


        return false;

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

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public Questions[] getQuestionArray() {
        return questionArray;
    }

    public boolean setQuestionArray(Questions[] questionArray) {
        this.questionArray = questionArray;
        return true;
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
        System.out.println(Arrays.toString(numArray));
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

    public Exam manualExamCreate(int numOfQuestInTest, int[][] indQuestion) {
        Exam manualExam = new Exam(numOfQuestInTest);
        for (int i = 0; i < this.numberOfQuestions; i++) {

            if (this.questionArray[i].questionId == indQuestion[i][0]) {
                if (manualExam.getQuestionsExamArray()[i] instanceof OpenQuestions) {
                    OpenQuestions newOpenQuestions = new OpenQuestions((OpenQuestions) this.questionArray[i]);
                    manualExam.getQuestionsExamArray()[i] = newOpenQuestions;
                }
                if (manualExam.getQuestionsExamArray()[i] instanceof AmericanQuestions) {

                }


            }
        }
        return  manualExam;

    }
}

