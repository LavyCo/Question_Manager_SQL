package id206214280_id316650399;

import javax.xml.transform.Result;
import java.sql.*;


public class QueryQuestionReservoir extends QuestionReservoir {


    Connection conn;
    Statement qrStmt;
    ResultSet rs;

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
        String queryInsertInto = "INSERT INTO questionTable VALUE (NULL,'" + question + "', 0);";
        qrStmt.executeQuery(queryInsertInto);

        return true;

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
