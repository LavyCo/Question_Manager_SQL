package id206214280_id316650399;

import java.util.Arrays;
import java.util.Objects;

public class AmericanQuestions extends Questions {

    private final int MAX_AMERICAN_ANSWERS = 10;
    private int numOfAmericanAnswers;
    private AmericanAnswer[] answerArray = new AmericanAnswer[MAX_AMERICAN_ANSWERS];



    public AmericanQuestions(String questionText, int numOfAmericanAnswers, AmericanAnswer[] answerArray) {
        super(questionText);
        this.numOfAmericanAnswers = numOfAmericanAnswers;
        setAnswerArray(answerArray);
    }

    public boolean isAnswerInArray(AmericanAnswer americanAnswer){
        for(int i=0;i<numOfAmericanAnswers;i++){
            if(answerArray[i].equals(americanAnswer))
                return true;
        }
        return false;
    }


    public AmericanQuestions(AmericanQuestions other) {
        super(other.questionText);
        this.numOfAmericanAnswers = other.numOfAmericanAnswers;
        this.answerArray = other.answerArray;

    }
    //שגיאות כתיב של לביא בפונקציה
    public void Add2Answers() {
        if(this.getNumOfAmericanAnswers()<=8){
            AmericanAnswer[] plus2AmericanAnswer=new AmericanAnswer[this.getNumOfAmericanAnswers()+2];
            for(int i=0;i<this.getNumOfAmericanAnswers();i++){
                plus2AmericanAnswer[i]=new AmericanAnswer(this.getAnswerArray()[i].getAnswerText(),this.getAnswerArray()[i].getCorrectness());
            }
            for(int i=0;i<numOfAmericanAnswers;i++){
                plus2AmericanAnswer[i].setCorrectness(false);
            }
            if(counterTrueFalse(this.getAnswerArray())==2){
                //לביא והשטויות שלו
                boolean correct= true;
                boolean notCorrect=false;
                String Ans2= "there is more than 1 right answer";
                String Ans1= "all the answers is false";
                this.answerArray=plus2AmericanAnswer;
                this.setNumOfAmericanAnswers(plus2AmericanAnswer.length);
                this.answerArray[numOfAmericanAnswers-1]=new AmericanAnswer( Ans1, correct);
                this.answerArray[numOfAmericanAnswers-2]=new AmericanAnswer( Ans2, notCorrect);
            }
            else if(counterTrueFalse(this.getAnswerArray())==3){
                boolean notCorrect=false;
                boolean correct= true;
                String Ans1= "all the answers is false";
                String Ans2= "there is more than 1 right answer";
                this.answerArray=plus2AmericanAnswer;
                this.setNumOfAmericanAnswers(plus2AmericanAnswer.length);
                this.answerArray[numOfAmericanAnswers-1]=new AmericanAnswer( Ans1,notCorrect );
                this.answerArray[numOfAmericanAnswers-2]=new AmericanAnswer( Ans2, correct);
            }
            else{
                String Ans1= "all the answers is false";
                String Ans2= "there is more than 1 right answer";
                this.answerArray=plus2AmericanAnswer;
                this.setNumOfAmericanAnswers(plus2AmericanAnswer.length);
                this.answerArray[numOfAmericanAnswers-1]=new AmericanAnswer( Ans1,false );
                this.answerArray[numOfAmericanAnswers-2]=new AmericanAnswer( Ans2, false);
            }



        }

    }


    //this function check if there are more than 1 true answer and adds "More than 1 true answer"
    //  הפונקצמיה בודקת את מערך התשובות לשאלה האמריקאית ומחלקת למצבים הבאים
    // ומטפלת לפי כל סיטואציה
    //יותר מתשובה אחת נכונה,כל התשובות נכונות,אף תשובה אינה נכונה,תושבה אחת נכונה ותשובה שנייה לא נכונה(לא מטפלת),יש רק תשובה אחת נכונה(לא מטפלת)
    //הפונקציה מחזירה מערך תשובות לאחר שטיפלה בו
    public boolean checkAnswerArrays(AmericanAnswer[] americanAnswers) {

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
                int newNumOfAmericanAnswer = numOfAmericanAnswers + 1;
                AmericanAnswer[] newAmericanAnswers = new AmericanAnswer[newNumOfAmericanAnswer];
                for (int i = 0; i < newNumOfAmericanAnswer - 1; i++) {
                    newAmericanAnswers[i] = americanAnswers[i];
                }
                newAmericanAnswers[newNumOfAmericanAnswer - 1] = new AmericanAnswer("All answers are false", true);
                this.setAnswerArray(newAmericanAnswers);
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
                americanAnswers[i].setCorrectness(false);
            }
            //creating copy array
            int newNumOfAmericanAnswer = this.numOfAmericanAnswers + 1;
            AmericanAnswer[] newAmericanAnswerArr = new AmericanAnswer[newNumOfAmericanAnswer];
            for (int i = 0; i < numOfAmericanAnswers; i++) {
                newAmericanAnswerArr[i] = americanAnswers[i];
            }
            newAmericanAnswerArr[newNumOfAmericanAnswer - 1] = new AmericanAnswer("More than 1 answer is true", true);
            this.setAnswerArray(newAmericanAnswerArr);
            numOfAmericanAnswers++;

        }
        return true;
    }


    //הפונקציה סופרת את את מספר התשובות הנכונות ולא נכונות ומחזירה אינדקס
    public int counterTrueFalse(AmericanAnswer[] americanAnswers) {

        //function that check how many true and false there are
        //check how many false or true answers there are for the question
        int trueCounter = 0, falseCounter = 0;
        for (int i = 0; i < americanAnswers.length; i++) {
            if (americanAnswers[i].getCorrectness()) {
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
            sb.append((i + 1) + ")" + answerArray[i].toString() + "\n");
        }
        sb.append("\n");
        return sb.toString();

    }

    public String printAnswersOnly() {
        StringBuffer sb = new StringBuffer();
        sb.append("The answers are:\n");
        for (int i = 0; i < numOfAmericanAnswers; i++) {
            sb.append((i + 1) + ")" + answerArray[i].toString() + "\n");
        }
        return sb.toString();

    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(answerArray);
        result = prime * result + Objects.hash(MAX_AMERICAN_ANSWERS, numOfAmericanAnswers);
        return result;
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



    public AmericanAnswer[] getAnswerArray() {
        return answerArray;
    }

    public boolean setAnswerArray(AmericanAnswer[] answerArray) {

        for (int i = 0; i < numOfAmericanAnswers; i++) {
            this.answerArray[i] = answerArray[i];
        }
        this.answerArray = answerArray;
        return true;
    }

    public int getMaxAmericanAnswer() {
        return MAX_AMERICAN_ANSWERS;
    }
}

