package id206214280_id316650399;

import java.sql.SQLException;
import java.util.Scanner;

public interface questionReservoirSQL {

    void addQuestionToDataBase(QueryQuestionReservoir qqr, Scanner input) throws SQLException;
    void deleteAnswerDB(QueryQuestionReservoir manager) throws Exception;
    void updateQuestionWordingDB(QueryQuestionReservoir manager) throws Exception;
    void updateAnswerWordingDB(QueryQuestionReservoir manager) throws Exception;
     int createManualExamDB(QueryQuestionReservoir manager) throws Exception;
     int createAutomaticExamDB(QueryQuestionReservoir manager) throws Exception;

}