package id206214280_id316650399;

import java.io.*;
import java.util.ArrayList;

public class QuestionReservoir implements Serializable {

    private int numberOfQuestions = 0;
    private ArrayList<Questions> questionArray;
    private Exam manualExam;
    private Exam manualExamClone;
    private Exam automaticExam;
    private Exam automaticExamClone;


    public QuestionReservoir() {
        questionArray = new ArrayList<>();
        manualExam = new Exam();
        automaticExam = new Exam();

    }

//    public void resizeQuestionArray() {
//        Questions[] newQuestionsArray = new Questions[size * 2];
//        for (int i = 0; i < numberOfQuestions; i++) {
//            newQuestionsArray[i] = questionArray[i];
//        }
//        this.questionArray = newQuestionsArray;
//
//    }


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

    public void updateId() {
        for (int i = numberOfQuestions; i < questionArray.size(); i++) {
            if (this.questionArray.get(i) instanceof AmericanQuestions) {
                AmericanQuestions newAmericanQuestion = new AmericanQuestions((AmericanQuestions) this.questionArray.get(i));
                questionArray.set(i, newAmericanQuestion);
                numberOfQuestions++;
            }

            if (this.questionArray.get(i) instanceof OpenQuestions) {
                OpenQuestions newOpenQuestion = new OpenQuestions((OpenQuestions) this.questionArray.get(i));
                questionArray.set(i, newOpenQuestion);
                numberOfQuestions++;
            }

        }
        ;


    }

    public void saveBin() throws FileNotFoundException, IOException, ClassNotFoundException {
        ObjectOutputStream outFile = new ObjectOutputStream(new FileOutputStream("QuestionArray.dat"));
        Questions[] questionArray = new Questions[this.questionArray.size()];
        for (int i = 0; i < this.questionArray.size(); i++) {
            questionArray[i] = this.questionArray.get(i);
        }
        outFile.writeObject(questionArray);
        outFile.close();


    }

    public void readBin() throws FileNotFoundException, IOException, ClassNotFoundException {
        ObjectInputStream inFile = new ObjectInputStream(new FileInputStream("QuestionArray.dat"));
        Questions[] tempQuestionArr = (Questions[]) inFile.readObject();
        inFile.close();
        for (int i = 0; i < tempQuestionArr.length; i++) {
            if (tempQuestionArr[i] instanceof OpenQuestions) {
                OpenQuestions newOpenQuestion = new OpenQuestions((OpenQuestions) tempQuestionArr[i]);
                this.getQuestionArray().add(newOpenQuestion);
                numberOfQuestions++;

            }
            if (tempQuestionArr[i] instanceof AmericanQuestions) {
                AmericanQuestions newAmericanQuestion = new AmericanQuestions((AmericanQuestions) tempQuestionArr[i]);
                this.getQuestionArray().add(newAmericanQuestion);
                numberOfQuestions++;

            }
        }
//        updateId();

    }


    public boolean changeAnswerWordingOfAmericanQuestions(String newAnswerText, AmericanQuestions editorQuestionAnswer, int numOfAnswer, boolean opt) {


        if (editorQuestionAnswer instanceof AmericanQuestions) {

            for (int i = 0; i < (editorQuestionAnswer).getNumOfAmericanAnswers(); i++) {

                if (editorQuestionAnswer.getAnswerArray().get(i).getAnswerText().equalsIgnoreCase(newAnswerText)) {
                    System.out.println("Can't change Answer text-There's an Answer with the same text");
                    return false;
                }
            }

            (editorQuestionAnswer).getAnswerArray().get(numOfAnswer).setCorrectness(opt);
            (editorQuestionAnswer).getAnswerArray().get(numOfAnswer).setAnswerText(newAnswerText);
            return true;
        }
        return false;
    }


    public boolean changeQuestionWording(String newQuestionText, int choosenId) {
        // loop that checks if the question exists
        Questions testQuestionText = new Questions(newQuestionText);
        for (int i = 0; i < this.numberOfQuestions; i++) {
            if (testQuestionText.equals(this.questionArray.get(i))) {
                System.out.println("Can't change question text-There a Question with the same text");
                return false;
            }

        }

        for (int i = 0; i < this.numberOfQuestions; i++) {
            if (this.questionArray.get(i).questionId == choosenId) {
                this.questionArray.get(i).setQuestionText(newQuestionText);
            }

        }
        System.out.println("Question wording changed succesfully!");
        return true;
    }

    //פונקציה זו נועדה בעיקר לכפר על כך שלמחלקה Set לא קיימת פונקצייה get שמחזירה אובייקט לפי אינדקס שהוכנס לכן היא עוזר לפונקציות שנכתבו לפני מימושה
    public Set<AmericanAnswer> copyArrayListToSet(ArrayList arrayList){
        Set<AmericanAnswer> newSet=new Set<>();
        for(int i=0;i<arrayList.size();i++){
            newSet.add((AmericanAnswer) arrayList.get(i));
        }
        return newSet;

    }

    public void automaticExam(int automaticExamNumberOfQuestions) throws FileNotFoundException {
        automaticExam = new Exam<>();
        //new question array for the exam
        ArrayList<Questions> newQuestionArray = new ArrayList<>(automaticExamNumberOfQuestions);
        //the reservoir question array
        ArrayList<Questions> qrQuestionArr = this.questionArray;
        //random index array example:5,3,2,4,1
        int[] randomIndex = randomNumbersArray(this.numberOfQuestions);
        for (int i = 0; i < automaticExamNumberOfQuestions; i++) {

            //r random index
            int r = randomIndex[i];

            //checks if the question is open
            if (qrQuestionArr.get(r) instanceof OpenQuestions) {
                OpenQuestions newOpenQuestion = new OpenQuestions(qrQuestionArr.get(r).questionText, ((OpenQuestions) qrQuestionArr.get(r)).getAnswerText());
                newQuestionArray.add(newOpenQuestion);
                automaticExam.addQuestion(newOpenQuestion);
            }


            if (qrQuestionArr.get(r) instanceof AmericanQuestions) {
                int americanAnswersSize = ((AmericanQuestions) qrQuestionArr.get(r)).getNumOfAmericanAnswers();

                //random American question chosen from the reservoir
                AmericanQuestions randomAmericanQuestion = ((AmericanQuestions) qrQuestionArr.get(r));

                //automated american question
                AmericanQuestions automaticAmericanQuestion = new AmericanQuestions(randomAmericanQuestion.questionText, randomAmericanQuestion.getAnswerArray());

                //another random index array for the answers
                int[] randomAnswerIndex = randomNumbersArray(americanAnswersSize);
                //initializing answers array size of 4
                ArrayList<AmericanAnswer> automaticAmericanAnswerArrayList = new ArrayList<>();
                //counts how mnay true answer encountered
                int trueCounter = 0;
                //iteration of the answer array
                int t = 0;
                //loop will keep running as long as inside the array and there are less than 4 answer collected
                for (int j = 0; j < americanAnswersSize && t < 4; j++) {
                    //index for random anser array
                    int p = randomAnswerIndex[j];

                    //if current chosen answer is the first true answer encountered add to automated array
                    if (randomAmericanQuestion.getAnswerArray().get(p).getCorrectness() && trueCounter != 1) {
                        automaticAmericanAnswerArrayList.add(randomAmericanQuestion.getAnswerArray().get(p));
                        //automaticAmericanAnswersArray[t] = new AmericanAnswer(randomAmericanQuestion.getAnswerArray().get(p));
                        t++;
                        trueCounter = 1;
                    }
                    //if current answer is false then add to automated answer array
                    else if (!randomAmericanQuestion.getAnswerArray().get(p).getCorrectness()) {
                        automaticAmericanAnswerArrayList.add(randomAmericanQuestion.getAnswerArray().get(p));

                        //automaticAmericanAnswersArray[t] = new AmericanAnswer(randomAmericanQuestion.getAnswerArray().get(p));
                        t++;

                    }
                    //if current answer is true and answer array has already a true ansewr in it
                    else if (randomAmericanQuestion.getAnswerArray().get(p).getCorrectness() && trueCounter == 1) {
                        //initializing k to j value to iterating out of loop
                        int k = j;
                        //foundFalse turns into true if a false answer is found
                        boolean foundFalse = false;
                        //next random answer index
                        k++;
                        //inner while loop that finds a false answer
                        while (!foundFalse) {
                            p = randomAnswerIndex[k];
                            //if found false answer
                            if (!randomAmericanQuestion.getAnswerArray().get(p).getCorrectness()) {
                                automaticAmericanAnswerArrayList.add(randomAmericanQuestion.getAnswerArray().get(p));
                                t++;
                                j = k;
                                foundFalse = true;
                            } else {
                                k++;
                            }

                        }

                    }

                }

                Set<AmericanAnswer> automaticAmericanAnswerSet=copyArrayListToSet(automaticAmericanAnswerArrayList);
                automaticAmericanQuestion.setQuestionText(randomAmericanQuestion.getQuestionText());
                automaticAmericanQuestion.setNumOfAmericanAnswers(4);
                automaticAmericanQuestion.setAnswerArray(automaticAmericanAnswerSet);
                automaticExam.addQuestion(automaticAmericanQuestion);

            }


        }

        automaticExam.saveToText();
        automaticExam.sortExamByShortestAnswers();
        System.out.println(automaticExam.toString());

    }

    public boolean cloneExam(int whichExamOpt) throws CloneNotSupportedException {
        if (whichExamOpt == 1) {
            if (manualExam.getNumOfQuestions() == 0) {
                System.out.println("Can't clone the exam. Manual exam not created yet.");
                return false;
            }
            manualExamClone = manualExam.clone();
            System.out.println("Manual exam cloned");
            System.out.println(manualExamClone.toString());
            return true;
        }

        if (whichExamOpt == 2) {
            if (automaticExam.getNumOfQuestions() == 0) {
                System.out.println("Can't clone the exam. Automatic exam not created yet.");
                return false;
            }
            automaticExamClone = automaticExam.clone();
            System.out.println("Automatic exam cloned");
            System.out.println(automaticExamClone.toString());
            return true;

        }
        return true;

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
                if (questionArray.get(i).equals(newOpenQuestion)) {
                    System.out.println("Cannot add:This question is already in the reservoir");
                    //decreasing id counter by 1
                    newQuestion.decreaseIdCounter();
                    return false;
                }
            }
            questionArray.add(newOpenQuestion);
            numberOfQuestions++;
            return true;

        }
        return false;
    }

    public boolean addAmericanQuestion(String questionText, String[] answersArray, boolean[] correctnessArray) {
        Set<AmericanAnswer> answerArrayList = new Set<>();

        for (int i = 0; i < answersArray.length; i++) {
            answerArrayList.add(new AmericanAnswer(answersArray[i], correctnessArray[i]));
        }
        AmericanQuestions newAmericanQuestion = new AmericanQuestions(questionText, answerArrayList);

        questionArray.add(newAmericanQuestion);
        numberOfQuestions++;

        System.out.println("American Question added");
        return true;


    }

    public int takeNumOfAnswers(QuestionReservoir qr1, int index) {
        AmericanQuestions americanQuestion = (AmericanQuestions) this.getQuestionArray().get(index);

        return americanQuestion.getNumOfAmericanAnswers();


    }

    public void deleteAmericanAnswer(int indQuestion, int answerNumber) {
        ((AmericanQuestions) questionArray.get(indQuestion)).americanAnswerRemove(answerNumber);
    }

//    public boolean deleteAmericanAnswer(int indQuestion, int answerNumber) {
//
//        AmericanAnswer[] originalAnswerArr = ((AmericanQuestions) this.questionArray.get(indQuestion)).getAnswerArray();
//
//        AmericanQuestions americanQuestion = ((AmericanQuestions) this.questionArray.get(indQuestion));
//        int newNumberOfAnswers = originalAnswerArr.length;
//        if (americanQuestion.counterTrueFalse(originalAnswerArr) == 1) {
//            System.out.println("Can't delete must have 2 negated answers");
//            return false;
//        }
//
//        if (americanQuestion.getNumOfAmericanAnswers() <= 2) {
//            System.out.println("Cant delete answer minimum answers required");
//            return false;
//        }
//
//
//        //creating new array of answer for american question with reduced number of answers
//        newNumberOfAnswers--;
//        AmericanAnswer[] newAmericanAnswerArray = new AmericanAnswer[newNumberOfAnswers];
//
//        for (int i = 0; i < answerNumber; i++) {
//            newAmericanAnswerArray[i] = originalAnswerArr[i];
//            System.out.println(newAmericanAnswerArray[i].toString());
//        }
//
//        for (int j = answerNumber; j < newNumberOfAnswers; j++) {
//            newAmericanAnswerArray[j] = originalAnswerArr[j + 1];
//            System.out.println(newAmericanAnswerArray[j].toString());
//
//        }
//
//
//        //reducing number of american answer for the question by 1
//        americanQuestion.setNumOfAmericanAnswers(newNumberOfAnswers);
//
//        //configuring new answer array
//        americanQuestion.setAnswerArray(newAmericanAnswerArray);
//
//        return true;
//
//    }

    public void manualExamCreate(int numOfQuestInTest, int[][] indQuestion) throws FileNotFoundException {

        for (int arrayIndex = 0; arrayIndex < numOfQuestInTest; arrayIndex++) {
            for (int allQuestionsIndex = 0; allQuestionsIndex < numberOfQuestions; allQuestionsIndex++) {
                if (this.questionArray.get(allQuestionsIndex).questionId == indQuestion[arrayIndex][0] + 1) {

                    if (this.questionArray.get(allQuestionsIndex) instanceof OpenQuestions) {
                        OpenQuestions newOpenQuestions = new OpenQuestions((OpenQuestions) this.questionArray.get(allQuestionsIndex));
                        manualExam.addQuestion(newOpenQuestions);

                    }
                    if (this.questionArray.get(allQuestionsIndex) instanceof AmericanQuestions) {
                        //first im going to initialize an american Question
                        AmericanQuestions newAmericanQuestion;
                        //next Im going to initialize an answerArrayList
                        Set<AmericanAnswer> newAmericanAnswer = new Set<>();
                        //now im going to start a loop that its stopping index is the index of the [][1]
                        for (int i = 0; i < indQuestion[arrayIndex][1]; i++) {
                            newAmericanAnswer.add(((AmericanQuestions) this.getQuestionArray().get(allQuestionsIndex)).getAnswerArray().get(indQuestion[arrayIndex][i + 2] - 1));
                        }
                        newAmericanQuestion = new AmericanQuestions(this.getQuestionArray().get(allQuestionsIndex).getQuestionText(), newAmericanAnswer);
                        manualExam.addQuestion(newAmericanQuestion);
                    }

                }
            }
        }
        manualExam.saveToText();
        System.out.println("Manual exam created successfully !");
        manualExam.toString();


    }

    //checks if there is the same question in the array
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Questions)) {
            return false;
        }
        Questions p = (Questions) other;
        for (int i = 0; i < numberOfQuestions; i++) {

            return this.questionArray.get(i).equals(p);


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

    public Exam getManualExam() {
        return manualExam;
    }

    public Exam getAutomaticExam() {
        return manualExam;
    }

    public ArrayList<Questions> getQuestionArray() {
        return questionArray;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("The number of questions in the Reservoir is: " + numberOfQuestions + "\n" + "\nThe questions are:\n");
        for (int i = 0; i < numberOfQuestions; i++) {
            sb.append(questionArray.get(i).toString());
        }
        return sb.toString();
    }


}

