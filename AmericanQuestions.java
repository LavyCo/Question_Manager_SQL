package id206214280_id316650399;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class AmericanQuestions extends Questions {

    private final int MAX_AMERICAN_ANSWERS = 10;
    private int numOfAmericanAnswers;
    private ArrayList<AmericanAnswer> answerArrayList;


    public AmericanQuestions(String questionText, ArrayList<AmericanAnswer> answerArrayList) {
        super(questionText);
        this.numOfAmericanAnswers = answerArrayList.size();
        this.answerArrayList = answerArrayList;
    }

    public boolean isAnswerInArray(AmericanAnswer americanAnswer) {
        for (int i = 0; i < numOfAmericanAnswers; i++) {
            if (answerArrayList.get(i).equals(americanAnswer))
                return true;
        }
        return false;
    }

    public boolean addAnswer(AmericanAnswer americanAnswer) {
        if (numOfAmericanAnswers == MAX_AMERICAN_ANSWERS) {
            System.out.println("Capacity full cannot add answer");
            return false;
        } else {
            this.answerArrayList.add(americanAnswer);
            numOfAmericanAnswers++;
            return true;
        }
    }

    public boolean americanAnswerRemove(int answerNumber) {
        System.out.println(numOfAmericanAnswers);
        if (numOfAmericanAnswers > 1) {
            numOfAmericanAnswers--;
            System.out.println(answerArrayList.get(answerNumber).toString());
            return true;
        }
        return false;
    }

    public AmericanQuestions(AmericanQuestions other) {
        super(other.questionText);
        this.numOfAmericanAnswers = other.numOfAmericanAnswers;
        answerArrayList.addAll(other.answerArrayList);

    }

//    public void Add2Answers() {
//        if(this.getNumOfAmericanAnswers()<=8){
//            AmericanAnswer[] plus2AmericanAnswer=new AmericanAnswer[this.getNumOfAmericanAnswers()+2];
//            for(int i=0;i<this.getNumOfAmericanAnswers();i++){
//                plus2AmericanAnswer[i]=new AmericanAnswer(this.getAnswerArray()[i].getAnswerText(),this.getAnswerArray()[i].getCorrectness());
//            }
//            for(int i=0;i<numOfAmericanAnswers;i++){
//                plus2AmericanAnswer[i].setCorrectness(false);
//            }
//            if(counterTrueFalse(this.getAnswerArray())==2){
//
//                boolean correct= true;
//                boolean notCorrect=false;
//                String Ans2= "there is more than 1 right answer";
//                String Ans1= "all the answers is false";
//                this.answerArray=plus2AmericanAnswer;
//                this.setNumOfAmericanAnswers(plus2AmericanAnswer.length);
//                this.answerArray[numOfAmericanAnswers-1]=new AmericanAnswer( Ans1, correct);
//                this.answerArray[numOfAmericanAnswers-2]=new AmericanAnswer( Ans2, notCorrect);
//            }
//            else if(counterTrueFalse(this.getAnswerArray())==3){
//                boolean notCorrect=false;
//                boolean correct= true;
//                String Ans1= "all the answers is false";
//                String Ans2= "there is more than 1 right answer";
//                this.answerArray=plus2AmericanAnswer;
//                this.setNumOfAmericanAnswers(plus2AmericanAnswer.length);
//                this.answerArray[numOfAmericanAnswers-1]=new AmericanAnswer( Ans1,notCorrect );
//                this.answerArray[numOfAmericanAnswers-2]=new AmericanAnswer( Ans2, correct);
//            }
//            else{
//                for(int i=0;i<numOfAmericanAnswers;i++){
//                    plus2AmericanAnswer[i].setCorrectness(this.getAnswerArray()[i].getCorrectness());
//                }
//                String Ans1= "all the answers is false";
//                String Ans2= "there is more than 1 right answer";
//                this.answerArray=plus2AmericanAnswer;
//                this.setNumOfAmericanAnswers(plus2AmericanAnswer.length);
//                this.answerArray[numOfAmericanAnswers-1]=new AmericanAnswer( Ans1,false );
//                this.answerArray[numOfAmericanAnswers-2]=new AmericanAnswer( Ans2, false);
//            }
//
//
//
//        }
//
//    }


    //this function check if there are more than 1 true answer and adds "More than 1 true answer"
    //  הפונקצמיה בודקת את מערך התשובות לשאלה האמריקאית ומחלקת למצבים הבאים
    // ומטפלת לפי כל סיטואציה
    //יותר מתשובה אחת נכונה,כל התשובות נכונות,אף תשובה אינה נכונה,תושבה אחת נכונה ותשובה שנייה לא נכונה(לא מטפלת),יש רק תשובה אחת נכונה(לא מטפלת)
    //הפונקציה מחזירה מערך תשובות לאחר שטיפלה בו
    public boolean checkAnswerArrays(ArrayList<AmericanAnswer> americanAnswers) {

        //tC=1 and fC=1
        if (this.counterTrueFalse(americanAnswers) == 1) {
            return true;
        }

        //all answers are false
        //adds an "All answers are false" answer
        if (this.counterTrueFalse(americanAnswers) == 2) {
            try {
                if (numOfAmericanAnswers == MAX_AMERICAN_ANSWERS) {
                    System.out.println("Maximal number of answers exceeded");
                    return false;
                }
                this.answerArrayList.add(new AmericanAnswer("All answers are false", true));
                numOfAmericanAnswers++;

                return true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return false;
            }

        }

        //tC>1  more than 1 true answer
        //turns all true answers into true and adds "More than answer is true" Answer
        if (this.counterTrueFalse(americanAnswers) == 3) {
            if (numOfAmericanAnswers == MAX_AMERICAN_ANSWERS) {
                System.out.println("Maximal number of answers exceeded");
                return false;
            }
            //loop that turns all true answers into false answers
            for (int i = 0; i < numOfAmericanAnswers; i++) {
                this.answerArrayList.get(i).setCorrectness(false);
            }
            this.answerArrayList.add(new AmericanAnswer("All answers are true", true));
            numOfAmericanAnswers++;

        }
        return true;
    }


    //הפונקציה סופרת את את מספר התשובות הנכונות ולא נכונות ומחזירה אינדקס
    public int counterTrueFalse(ArrayList<AmericanAnswer> americanAnswers) {

        //function that check how many true and false there are
        //check how many false or true answers there are for the question
        int trueCounter = 0, falseCounter = 0;
        for (int i = 0; i < americanAnswers.size(); i++) {
            if (americanAnswers.get(i).getCorrectness()) {
                trueCounter++;
            } else {
                falseCounter++;
            }
        }
        //if tC=1 and fC=1 you can't delete any answer(only 2 answers for the question)
        if (trueCounter == 1 && falseCounter == 1) {
            return 1;
        }
        //if tC=0 and fC>0 if true answer deleted than add another answer (All answers are false)
        if ((trueCounter == 0) && (falseCounter > 0)) {
            return 2;
        }
        //if tC>1 (there is more than 1 right answer)
        if ((trueCounter > 1)) {
            return 3;
        }
        return 0;
    }


    public int getNumOfAmericanAnswers() {
        return numOfAmericanAnswers;
    }

    public void setNumOfAmericanAnswers(int numOfAmericanAnswers) {
        this.numOfAmericanAnswers = numOfAmericanAnswers;
    }

    @Override
    public String toString() {

        StringBuffer sb = new StringBuffer();
        sb.append(super.toString() + "the answers are: \n");
        for (int i = 0; i < numOfAmericanAnswers; i++) {
            sb.append((i + 1) + ")" + answerArrayList.get(i).toString() + "\n");
        }
        sb.append("\n");
        return sb.toString();

    }

    public String printAnswersOnly() {
        StringBuffer sb = new StringBuffer();
        sb.append("The answers are:\n");
        for (int i = 0; i < numOfAmericanAnswers; i++) {
            sb.append((i + 1) + ")" + answerArrayList.get(i).toString() + "\n");
        }
        return sb.toString();

    }


    @Override
    public boolean equals(Object other) {
        if (!(other instanceof AmericanQuestions)) {
            return false;
        }
        if (!(super.equals(other))) {
            return false;
        }
        return true;
    }


    public ArrayList<AmericanAnswer> getAnswerArray() {
        return answerArrayList;
    }

    public boolean setAnswerArray(ArrayList<AmericanAnswer> answerArrayList) {

        for (int i = 0; i < numOfAmericanAnswers; i++) {
            this.answerArrayList.set(i, answerArrayList.get(i));
        }
        return true;
    }

    public int getMaxAmericanAnswer() {
        return MAX_AMERICAN_ANSWERS;
    }
}

