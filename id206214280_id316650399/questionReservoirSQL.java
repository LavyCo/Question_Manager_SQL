package id206214280_id316650399;

import java.sql.SQLException;
import java.util.Scanner;

public interface questionReservoirSQL {


    void addQuestionToDataBase(QueryQuestionReservoir qqr, QuestionReservoir qr, Scanner input) throws SQLException;

    void updateQuestionWordingFromDataBase(QueryQuestionReservoir qr1) throws Exception;

    boolean deleteAnswerFromDB(QueryQuestionReservoir qqr, Scanner s) throws Exception;
}
