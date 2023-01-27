package id206214280_id316650399;

import java.sql.Connection;
import java.io.*;
import java.sql.*;
import java.util.*;

public class QueryQuestionReservoir {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch(Exception e){
                e.printStackTrace();
            }


    }
    public static void main(String[] args) {
        QueryQuestionReservoir jdbc =new QueryQuestionReservoir();
        try(Connection con=jdbc.getConnection()){
            System.out.println("all Questions:");
            jdbc.getQuestion(con).forEach(System.out::println);
            System.out.println();
            
        }
    }

    private Iterable<Object> getQuestion(Connection con) {

        return null;
    }

    private Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/exam", "root", "lavy2120626");
        } catch(SQLException e){
                    throw new RuntimeException(e);
        }
    }

}
