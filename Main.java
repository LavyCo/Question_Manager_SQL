package id206214280_id316650399;

import java.io.*;
import java.util.*;
import java.util.Set;

import static id206214280_id316650399.Menu.exception1Or2Select;


//כדי להצ
// ליח לייצר עוד מקצועות עלינו ליצור מחלקות של מקצועות שמקבלות מאגרים
public class Main   {

    public static void main(String[] args) throws Exception , FileNotFoundException, IOException,ClassNotFoundException {
//        QuestionReservoir qr1 = new QuestionReservoir();
//        //open question #1
//        qr1.addOpenQuestion("A Who was Yitzhak Rabin?","Israeli prime minister");
//        //open question #2
//        qr1.addOpenQuestion("B Who solved the Engima machine during WWII?","Alan Turing");
//        //open question #3
//        qr1.addOpenQuestion("C What is the difference between Choclate cake and a Salad?","Salad wont give you Diabetes");
//        //american question #1
//        String aq1=("D Which one of these is not dessert?");
//        String[] aa1=new String[]{"Muffin","Choclate cake","Ice cream","Brownies","Salad","Burger","Shawrma","Pizza"};
//        boolean[] tof1=new boolean[]{false,false,false,false,true,true,true,true};
//        qr1.addAmericanQuestion(aq1,aa1,tof1);
//        //american question #2
//        String aq2="E Which one of these next Programming languages is Low-Level programming language?";
//        String[] aa2=new String[]{"Java","C++","Assembly","C","C#","Python","JavaScript","Swift","Machine Code"};
//        boolean[] tof2=new boolean[]{false,false,true,false,false,false,false,false,true};
//        qr1.addAmericanQuestion(aq2,aa2,tof2);
//        System.out.println(qr1.getQuestionArray().size());
//        qr1.saveBin();


        QuestionReservoir qr1= new QuestionReservoir();
        qr1.readBin();

        Menu menu1=new Menu();







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
            System.out.println("8-clone an exam");
            System.out.println("9-Exit program");
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
                    menu1.printQuestions(qr1);
                    break;
                }

                // option 2 add questions
                case 2: {
                    menu1.addQuestion(qr1);
                    break;
                }

                // change the wording of a question
                case 3: {
                    menu1.updateQuestionWording(qr1);
                    break;

                }

                //change the wording of an answer
                case 4: {
                    menu1.updateAnswerWording(qr1);
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
                  menu1.deleteAnswer(qr1);
                    break;
                }

                case 6: {

                   menu1.createManualExam(qr1);

                    break;
                }

                case 7: {
                    menu1.createAutomaticExam(qr1);

                    break;
                }

                case 8:{
                    menu1.cloneExam(qr1);

                    break;
                }


                case 9: {
                    System.out.println("Question reservoir saved");
                    menu1.saveBinary(qr1);
                    System.out.println("Goodbye");
                    break;
                }

            }
        } while (optMenu != 9);
        input.close();
    }






}
