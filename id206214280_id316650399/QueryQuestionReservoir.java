package id206214280_id316650399;

import java.sql.*;
import java.util.Arrays;


public class QueryQuestionReservoir {


    Connection conn;
    Statement stmt;
    ResultSet rs;

    public void printQuestionByID(int questionId) throws SQLException {
        rs= stmt.executeQuery("SELECT QID,QText,IsMulti FROM questionTable WHERE QID=" + questionId + "");
            rs.next();
            String questionText=rs.getString("QText");
            System.out.printf("Question Id is:%d Question text is:%s\n",questionId,questionText);
            int isMulti=rs.getInt("isMulti");
            if(isMulti==1) {
                System.out.println("Question is american");
                printAmericanAnswersFromDb(questionId);
            }
            if(isMulti==0) {
                System.out.println("Question is open");
                printOpenQuestionAnswer(questionId);
            }
    }

    public void printQuestionWithoutAnswers() throws SQLException {
        rs = stmt.executeQuery("SELECT QID,QTEXT FROM QuestionTable");
        while (rs.next()) {
            int questionID = rs.getInt("QID");
            String questionText = rs.getString("QTEXT");
            System.out.printf("ID:%d Question is:%s\n", questionID, questionText);
            System.out.println();
        }

    }

    public boolean changeQuestionText(int questionID, String newText) throws SQLException {
        String updateQuery = "UPDATE questionTable SET QText=? WHERE QID=?";
        PreparedStatement pStmt = conn.prepareStatement(updateQuery);
        pStmt.setString(1, newText);
        pStmt.setInt(2, questionID);
        System.out.println(updateQuery);
        int rowAffected = pStmt.executeUpdate();

        if (rowAffected == 1)
            return true;
        else
            return false;

    }

    public int getNumOfQuestions() throws SQLException {

        String getQuestions = "SELECT * FROM QuestionTable";

        int numOfQuestions = 0;

        rs = stmt.executeQuery(getQuestions);

        while (rs.next()) {
            numOfQuestions++;
        }
        return numOfQuestions;
    }

    public void printAmericanQuestions() throws SQLException {
        rs = stmt.executeQuery("SELECT QID,QTEXT FROM QuestionTable WHERE isMulti=1");
        int iterator = 0;
        while (rs.next()) {
            iterator++;
            int questionID = rs.getInt("QID");
            String questionText = rs.getString("QText");
            System.out.printf("Question ID : %d \nQuestion : %s \n", questionID,
                    questionText);
            printAmericanAnswersFromDb(questionID);
            rs = stmt.executeQuery("SELECT QID,QTEXT FROM QuestionTable WHERE isMulti=1");
            for (int i = 0; i < iterator; i++) {
                rs.next();
            }
        }


    }

    public void printQuestions() throws SQLException {
        String queryQuestions = "SELECT QID, QText, isMulti FROM QuestionTable";
        rs = stmt.executeQuery(queryQuestions);
        int iterator = 0;
        while (rs.next()) {
            iterator++;
            int questionID = rs.getInt("QID");
            printQuestionByID(questionID);
            queryQuestions = "SELECT QID, QText, isMulti FROM QuestionTable";
            rs = stmt.executeQuery(queryQuestions);
            for (int i = 0; i < iterator; i++) {
                rs.next();
            }
            System.out.println();
        }
    }



    public void printOpenQuestionAnswer(int questionID) throws SQLException {
        ResultSet openAnswerRs;
        String getOpenAnswerQuery = "SELECT OQid,OAText FROM openQuestionTable WHERE OQID=" + questionID + "";
        openAnswerRs = stmt.executeQuery(getOpenAnswerQuery);
        openAnswerRs.next();
        String openAnswerText = openAnswerRs.getString("OAText");
        System.out.println("Answer:" + openAnswerText);

    }


    public void printAmericanAnswersFromDb(int questionID) throws SQLException {
        ResultSet answerRs;
        String getAmericanAnswers = "SELECT AQID,AID,isCorrect,AAnsText FROM americanAnswersTable WHERE AQID=" + questionID + "";
        answerRs = stmt.executeQuery(getAmericanAnswers);
        while (answerRs.next()) {
            int answerId = answerRs.getInt("AID");
            String answerString = answerRs.getString("AAnsText");
            System.out.println("Answers ID:" + answerId + ")" + answerString);
        }
    }

    public boolean addOpenQuestionDB(String question, String answer) throws SQLException {
        String queryInsertInto = ("INSERT INTO questionTable VALUE (NULL,'" + question + "', 0);");
        stmt.executeUpdate(queryInsertInto);
        rs = stmt.executeQuery("select QID FROM questionTable where QText='" + question + "';");
        if (rs.next()) {
            int QID = rs.getInt("QID");
            stmt.executeUpdate("INSERT INTO openquestiontable VALUES (" + QID + ",'" + answer + "');");

        }

        return true;

    }

    public void addAmericanQuestionDB(String question) throws SQLException {
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

    public boolean deleteAnswer(int questionID, int answerID) throws SQLException {

        int trueAnswerFromUser = 0;
        int boolValFromUser;
        String getAnswers = "SELECT AID , AAnsText FROM AmericanAnswersTable ";

        String getAQTable = "SELECT AQID , AID , isCorrect , AAnsText FROM AmericanAnswersTable WHERE AQID ="
                + questionID;
        String deleteAnswer = "DELETE FROM AmericanAnswersTable WHERE AID =" + answerID + ";";
        int numberOfRowsAffected = stmt.executeUpdate(deleteAnswer);
        if (numberOfRowsAffected == 1)
            return true;
        return false;
    }

    public int getQuestionTypeDB(int questionID) throws SQLException {

        rs = stmt.executeQuery("SELECT QID,isMulti FROM questionTable WHERE QID=" + questionID + "");
        rs.next();
        int isMulti = rs.getInt("isMulti");
        return isMulti;
    }

    public boolean changeOpenAnswerWording(int questionID, String newAnswerText) throws SQLException {

        String updateQuery = "UPDATE openQuestionTable SET OAText=? WHERE OQID=?";
        PreparedStatement pStmt = conn.prepareStatement(updateQuery);
        pStmt.setString(1, newAnswerText);
        pStmt.setInt(2, questionID);
        int affectedRow = pStmt.executeUpdate();
        if (affectedRow == 1)
            return true;
        else
            return false;
    }

    public boolean changeAmericanAnswerWording(int questionID, int answerID, String newAnswerText) throws SQLException {
        String updateQuery = "UPDATE americanAnswersTable SET AAnsText=? WHERE AQID=? AND AID=?";
        PreparedStatement pStmt = conn.prepareStatement(updateQuery);
        pStmt.setString(1, newAnswerText);
        pStmt.setInt(2, questionID);
        pStmt.setInt(3, answerID);
        int affectedRow = pStmt.executeUpdate();
        if (affectedRow == 1)
            return true;
        else
            return false;
    }

    public boolean addQuestionToExam(int examId, int questionId) throws SQLException {
        int affectedRows = stmt.executeUpdate("INSERT INTO examTable VALUES ( " + examId + " ," + questionId + ")");
        if (affectedRows == 1)
            return true;

        return false;
    }

    public boolean doesQuestionExists(int QID) throws SQLException {

        int questionID = 0;
        String getQuestions = "SELECT * FROM QuestionTable";

        rs = stmt.executeQuery(getQuestions);

        while (rs.next()) {

            questionID = rs.getInt("QID");

            if (questionID == QID)
                return true;
        }

        return false;

    }

    public boolean isExistsInExam(int EID, int QID) throws SQLException {
        int examID = 0;
        int questionID = 0;
        String questionExists = "SELECT * FROM ExamTable";
        rs = stmt.executeQuery(questionExists);
        while (rs.next()) {
            examID = rs.getInt("EID");
            questionID = rs.getInt("QID");
            if (examID == EID && questionID == QID)
                return true;
        }
        return false;

    }

    public void printExam(int examId) throws SQLException {
        System.out.println("ExamID is:"+examId);
        rs=stmt.executeQuery("SELECT QID FROM examTable WHERE EID="+examId+"");
        int iterator=0;
        while(rs.next())
        {
            iterator++;
            int questionId=rs.getInt("QID");
            printQuestionByID(questionId);
            rs=stmt.executeQuery("SELECT QID FROM examTable WHERE EID="+examId+"");
            for(int i=0;i<iterator;i++)
            {
                rs.next();
            }
        }
    }

    public void addRandomQuestionsToExam(int examId,int numberOfQuestions) throws SQLException {
        rs=stmt.executeQuery("SELECT QID FROM questionTable " +
                "ORDER BY RAND() " +
                "LIMIT "+numberOfQuestions+"");
        int[] arr=new int[numberOfQuestions];
        for(int i=0;i<numberOfQuestions;i++)
        {
            rs.next();
            arr[i]=rs.getInt("QID");
        }
        for(int i=0;i<numberOfQuestions;i++)
            addQuestionToExam(examId,arr[i]);
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


    public int createExam(int numberOfQuestions) throws SQLException {
        int affectedRows = stmt.executeUpdate("INSERT INTO allExamTable VALUE(NULL," + numberOfQuestions + ")");
        if (affectedRows == 0)
            return 0;
        rs = stmt.executeQuery("SELECT MAX(EID) FROM allExamTable ");
        rs.next();
        return rs.getInt("MAX(EID)");
    }



}
