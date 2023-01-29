package id206214280_id316650399;

import java.sql.*;
import java.util.Scanner;


public class QueryQuestionReservoir extends QuestionReservoir {


    Connection conn;
    Statement stmt;
    ResultSet rs;

    private static final long serialVersionUID = 1L;


    public void printQuestionWithoutAnswers() throws SQLException
    {
        rs= stmt.executeQuery("SELECT QID,QTEXT FROM QuestionTable");
        while(rs.next())
        {
            int questionID=rs.getInt("QID");
            String questionText=rs.getString("QTEXT");
            System.out.printf("ID:%d Question is:%s\n",questionID,questionText);
            System.out.println();
        }

    }

    public boolean changeQuestionText(int questionID,String newText) throws SQLException {
            String updateQuery = "UPDATE questionTable SET QText=? WHERE QID=?";
            PreparedStatement pStmt = conn.prepareStatement(updateQuery);
            pStmt.setString(1, newText);
            pStmt.setInt(2, questionID);
            System.out.println(updateQuery);
            int rowAffected = pStmt.executeUpdate();

            if(rowAffected==1)
                return true;
            else
                return false;

    }

    public void printAmericanQuestions() throws SQLException {
        rs =stmt.executeQuery("SELECT QID,QTEXT FROM QuestionTable WHERE isMulti=1");
        int iterator=0;
        while(rs.next())
        {
            iterator++;
            int questionID = rs.getInt("QID");
            String questionText = rs.getString("QText");
            System.out.printf("Question ID : %d \nQuestion : %s \n", questionID,
                    questionText);
            printAmericanAnswersFromDb(questionID);
            rs =stmt.executeQuery("SELECT QID,QTEXT FROM QuestionTable WHERE isMulti=1");
            for(int i=0;i<iterator;i++)
            {
                rs.next();
            }
        }



    }

    public void printQuestions() throws SQLException {
        String queryQuestions = "SELECT QID, QText, isMulti FROM QuestionTable";
        rs = stmt.executeQuery(queryQuestions);
        int iterator=0;
        while (rs.next()) {
            iterator++;
            int questionID = rs.getInt("QID");
            int isMulti = rs.getInt("isMulti");
            String questionText = rs.getString("QText");
            System.out.printf("Question ID : %d \nQuestion : %s \nIs it multiple choice? : %d\n", questionID,
                    questionText, isMulti);
            if(isMulti==0)
            {
                printOpenQuestionAnswer(questionID);
            }
            else if(isMulti==1)
            {
                printAmericanAnswersFromDb(questionID);

            }
            queryQuestions = "SELECT QID, QText, isMulti FROM QuestionTable";
            rs = stmt.executeQuery(queryQuestions);
            for(int i=0;i<iterator;i++)
            {
                rs.next();
            }
            System.out.println();
        }

    }

    public void printOpenQuestionAnswer(int questionID) throws SQLException
    {
        ResultSet openAnswerRs;
        String getOpenAnswerQuery = "SELECT OQid,OAText FROM openQuestionTable WHERE OQID="+questionID+"";
        openAnswerRs= stmt.executeQuery(getOpenAnswerQuery);
        openAnswerRs.next();
        String openAnswerText=openAnswerRs.getString("OAText");
        System.out.println("Answer:"+openAnswerText);

    }

    public void printAmericanAnswersFromDb(int questionID) throws SQLException {
        ResultSet answerRs;
        String getAmericanAnswers="SELECT AQID,AID,isCorrect,AAnsText FROM americanAnswersTable WHERE AQID="+questionID+"";
        answerRs = stmt.executeQuery(getAmericanAnswers);
        while(answerRs.next())
        {
            int answerId=answerRs.getInt("AID");
            String answerString = answerRs.getString("AAnsText");
            System.out.println("Answers ID:"+answerId+")"+answerString);
        }
    }

    public boolean addOpenQuestionDB(String question , String answer )throws SQLException {
        String queryInsertInto = ("INSERT INTO questionTable VALUE (NULL,'" + question + "', 0);");
        stmt.executeUpdate(queryInsertInto);
        rs=stmt.executeQuery("select QID FROM questionTable where QText='"+question+"';");
        if(rs.next()){
            int QID=rs.getInt("QID");
            stmt.executeUpdate("INSERT INTO openquestiontable VALUES ("+QID+",'"+answer+"');");

        }

        return true;

    }
    public void addAmericanQuestionDB(String question )throws SQLException {
        stmt.executeUpdate("INSERT INTO questionTable VALUES (NULL,'" + question + "', 1);");
        rs = stmt.executeQuery("SELECT QID FROM questionTable WHERE QText = '" + question + "';");
        if (rs.next()) {
            int QID = rs.getInt("QID");
            stmt.executeUpdate("INSERT INTO americanquestiontable VALUE ('" + QID + "');");
        }


    }
    public void addAnswer(String answer, boolean correctness) throws SQLException {
        rs = stmt.executeQuery("SELECT QID FROM questionTable left join  americanquestiontable ON QID = AQID WHERE QID = AQID;");
        if (rs.next()) {
            ResultSet rs2 = stmt.executeQuery("SELECT MAX(QID) from questiontable;");
            if (rs2.next()) {
                int QID = rs2.getInt(1);
                stmt.executeUpdate("INSERT INTO americananswerstable VALUES ('" + QID + "',NULL," + correctness + ",'" + answer + "');");
            }
        }
    }
    String queryQuestions = "SELECT QID,QText,isMulti";

    String queryOpenQuestions  = "SELECT EID, NumOfQuestions";
    String queryAmericanQuestions = "SELECT AQID";
    String queryAmericanAnswers = "SELECT, AQID, AID , isCorrect , AAnsText";

    public boolean deleteAnswer(int questionID, int answerID) throws SQLException {

        int trueAnswerFromUser = 0;
        int boolValFromUser;

        String getAnswers = "SELECT AID , AAnsText FROM AmericanAnswersTable ";

        String getAQTable = "SELECT AQID , AID , isCorrect , AAnsText FROM AmericanAnswersTable WHERE AQID ="
                + questionID;
        String deleteAnswer = "DELETE FROM AmericanAnswersTable WHERE AID ="+answerID+";";
        int numberOfRowsAffected = stmt.executeUpdate(deleteAnswer);
        if(numberOfRowsAffected==1)
            return true;
        return false;
     }



    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return super.toString();
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
