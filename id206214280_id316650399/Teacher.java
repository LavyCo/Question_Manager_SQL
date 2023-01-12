package id206214280_id316650399;

import java.util.ArrayList;

public class Teacher {

    private ArrayList<Questions> teacherQuestionArrayList;

    public ArrayList<Questions> getTeacherQuestionArrayList() {
        return teacherQuestionArrayList;
    }

    public void setTeacherQuestionArrayList(ArrayList<Questions> teacherQuestionArrayList) {
        this.teacherQuestionArrayList = teacherQuestionArrayList;
    }

    private static int count=1;
    private String teacherName;
    private int teacherId;

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        Teacher.count = count;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }
}
