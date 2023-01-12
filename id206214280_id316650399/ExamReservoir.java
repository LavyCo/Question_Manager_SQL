package id206214280_id316650399;

import java.util.ArrayList;

public class ExamReservoir {
    private ArrayList<Exam> examArray;
    private static int count=0;
    private String teacherName;
    protected int examId;

    public ArrayList<Exam> getExamArray() {
        return examArray;
    }

    public void setExamArray(ArrayList<Exam> examArray) {
        this.examArray = examArray;
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        ExamReservoir.count = count;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }
}
