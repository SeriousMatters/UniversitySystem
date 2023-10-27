package UNIENROLSYSTEM;

import java.io.Serializable;

public class Subject implements Serializable {
    private String id;
    private int mark;
    private String grade;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public int getMark() {
        return mark;
    }
    
    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getGrade() {
        return grade;
    }
}
