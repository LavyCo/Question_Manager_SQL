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
