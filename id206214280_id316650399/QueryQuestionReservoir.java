package id206214280_id316650399;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.Scanner;



public class QueryQuestionReservoir extends QuestionReservoir {


    Connection conn;
    Statement qrStmt;
    ResultSet rs;
    public int rsUPDATE;

    private static final long serialVersionUID = 1L;




    public void printQuestions(Connection con) throws SQLException {
        String queryQuestions = "SELECT QID, QText, isMulti FROM QuestionTable";
        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(queryQuestions);
            while (rs.next()) {

                int questionID = rs.getInt("QID");

                String questionText = rs.getString("QText");

                int isMulti = rs.getInt("isMulti");

                System.out.printf("Question ID : %d \nQuestion : %s \nIs it multiple choice? : %d\n" , questionID , questionText , isMulti);
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }


    public boolean addOpenQuestionDB(String question , String answer )throws SQLException {
        String queryInsertInto = ("INSERT INTO questionTable VALUE (NULL,'" + question + "', 0);");
        qrStmt.executeUpdate(queryInsertInto);
        rs=qrStmt.executeQuery("select QID FROM questionTable where QText='"+question+"';");
        if(rs.next()){
            int QID=rs.getInt("QID");
            qrStmt.executeUpdate("INSERT INTO openquestiontable VALUES ("+QID+",'"+answer+"');");

        }

        return true;

    }
    public void addAmricanQuestionDB(String question )throws SQLException {
        qrStmt.executeUpdate("INSERT INTO questionTable VALUES (NULL,'" + question + "', 1);");
        rs = qrStmt.executeQuery("SELECT QID FROM questionTable WHERE QText = '" + question + "';");
        if (rs.next()) {
            int QID = rs.getInt("QID");
            qrStmt.executeUpdate("INSERT INTO americanquestiontable VALUE ('" + QID + "');");
        }


    }
    public void addAnswer(String answer, boolean correctness) throws SQLException {
        rs = qrStmt.executeQuery("SELECT QID FROM questionTable left join  americanquestiontable ON QID = AQID WHERE QID = AQID;");
        if (rs.next()) {
            ResultSet rs2 = qrStmt.executeQuery("SELECT MAX(QID) from questiontable;");
            if (rs2.next()) {
                int QID = rs2.getInt(1);
                qrStmt.executeUpdate("INSERT INTO americananswerstable VALUES ('" + QID + "',NULL," + correctness + ",'" + answer + "');");
            }
        }
    }
//    public int getQuestionById(int QID) throws SQLException {
//        try {
//            rs = qrStmt.executeQuery("SELECT QID,QText FROM questionTable WHERE " + QID + " = QID ");
//            while(rs.next())
//            {
//                int foundID =rs.getInt("QID");
//                if(foundID==QID){
//                    return QID;
//
//                }
//            }
//
////            rs.next();
////            String question = rs.getString(1);//question text col
////
////            rs = qrStmt.executeQuery("SELECT OAText FROM openQuestionTable WHERE " + QID + " = OQID");
////            if (rs.next()) {
////                String answerText = rs.getString(1);
////                OpenQuestions res = new OpenQuestions(question, answerText);
////                return (Questions) res;
////            } else {
////                AmericanQuestions res = new AmericanQuestions(question,null);
////                return (Questions) res;
////            }
//
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return -1;
//    }
//    public boolean isAmericanQuestion(int QID) throws SQLException {
//
//        return getQuestionById(QID) instanceof AmericanQuestions;
//    }
    public int getNumOfAnswers(int QID) throws SQLException {
        rs = qrStmt.executeQuery("SELECT COUNT(*) FROM americananswerstable WHERE AQID = "+QID+";");
        rs.next();
        return rs.getInt(1);
    }
    public int printAllAmericanAnswers(int id) throws SQLException {
        rs = qrStmt.executeQuery("SELECT * FROM americananswerstable WHERE AQID = " + id + "");
        int i = 1;
        while (rs.next()) {
            int bool = rs.getInt("isCorrect");
            if (bool == 1)
                System.out.println(i + ". " + rs.getString("AAnsText") + " (True)");
            else {
                System.out.println(i + ". " + rs.getString("AAnsText") + " (False)");
            }
            i++;

        }
        return i;
    }
    public void deleteAnswer(int QID, int answerNum) throws SQLException {
        rs = qrStmt.executeQuery("SELECT * FROM americananswerstable WHERE AQID = "+QID+";");
        for(int i =0;i<=answerNum;i++){
            rs.next();
        }
        String beforeAns = rs.getString("AAnsText");
        qrStmt.executeUpdate("DELETE FROM multichoiceanswerstable WHERE (AQID = " + QID + " AND AAnsText = '" + beforeAns + "');");
    }

    String queryQuestions = "SELECT QID,QText,isMulti";

    String queryOpenQuestions  = "SELECT EID, NumOfQuestions";
    String queryAmericanQuestions = "SELECT AQID";
    String queryAmericanAnswers = "SELECT, AQID, AID , isCorrect , AAnsText";




    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return super.toString();
    }


    public void chackQIDfromDataBase(int expId) {

    }

    public boolean chackQID(int expId) {
        try {
            rs = qrStmt.executeQuery("SELECT QID,QText FROM questionTable WHERE " + expId + " = QID ");
            while (rs.next()) {
                int foundID = rs.getInt("QID");
                if (foundID == expId) {
                    return true;

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return false;
    }

    public Questions chackTypeOfQuestion(int userGivenQuestionId) {
        try {
            rs = qrStmt.executeQuery("SELECT QID, isMulti FROM questiontable WHERE QID=" + userGivenQuestionId);
            while (rs.next()) {
                int isMulti = rs.getInt("isMulti");
                String questionText = rs.getString("QText");

                if (isMulti == 0) {
                    String answerText = rs.getString("OAText");
                    OpenQuestions res = new OpenQuestions(questionText, answerText);
                    return res;
                } else {

                    AmericanQuestions res = new AmericanQuestions(questionText, null);
                    return res;
                }


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void updateQuestionWordingFromDB(int ID, String newQuestion) throws SQLException {
        String getQuestion = "SELECT QID,QText FROM QuestionTable WHERE QID =" + ID;
        String putNewQuestion = "UPDATE QuestionTable SET QText = '" + newQuestion + "' WHERE QID =" + ID;
        rs = qrStmt.executeQuery(getQuestion);
        while (rs.next()) {
            int QID = rs.getInt("QID");
            String question = rs.getString("QText");
            System.out.printf("(%d) - %s\n", QID, question);
        }
        rsUPDATE = qrStmt.executeUpdate(putNewQuestion);
    }

    //check in main if OpenQuestion Or Not
    public void updateAnswerWordingFromDB(int questionID,int answerID ,String newAnswer , boolean isMulti , boolean isCorrect) throws SQLException {
        if(isMulti == false) // Open Question
        {
            String getQuestion = "SELECT OQID,OAText FROM OpenQuestionTable WHERE OQID =" + questionID;
            String putNewAnswer = "UPDATE OpenQuestionTable SET OAText ='" + newAnswer + "' WHERE OQID =" +questionID ;
            rs = qrStmt.executeQuery(getQuestion);
            while(rs.next())
            {
                int OQID = rs.getInt("OQID");
                String openAnswer = rs.getString("OAText");
                System.out.printf("(%d) - %s\n", OQID, openAnswer);
            }
            rsUPDATE = qrStmt.executeUpdate(putNewAnswer);
        }
        else {
            String getAnswers = "SELECT AQID , AID , isCorrect , AAnsText FROM AmericanAnswersTable WHERE AQID =" + questionID;
            String putNewAnswer = "UPDATE AmericanAnswersTable SET AAnsText ='" + newAnswer + "' WHERE AID = " +answerID;
            rs = qrStmt.executeQuery(getAnswers);
            while (rs.next()) {
                int answerQuestionID= rs.getInt("AQID");
                int theAnswerID = rs.getInt("AID");
                int isAnswerCorrect = rs.getInt("isCorrect");
                String answerText  = rs.getString("AAnsText");
                System.out.printf("QID - %d \t (%d) - %s \t is it correct? |%d|\n" , answerQuestionID , theAnswerID ,answerText, isAnswerCorrect );

            }
            rsUPDATE = qrStmt.executeUpdate(putNewAnswer);
        }

    }
//    public static void main(String[] args) {
//
//        QueryQuestionReservoir jdbc =new QueryQuestionReservoir();
//        try(Connection con=jdbc.getConnection()){
//

//            System.out.println("--- All OpenQuestions ---");
//            jdbc.getOpenQuestions(con).forEach(System.out::println);
//            System.out.println();
//
//
//            System.out.println("--- All AmericanQuestions ---");
//            jdbc.getAmericanQuestions(con).forEach(System.out::println);
//            System.out.println();
//
//
//            System.out.println("--- All Questions ---");
//            jdbc.getQuestions(con).forEach(System.out::println);
//            System.out.println();
//

//    }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//





}
