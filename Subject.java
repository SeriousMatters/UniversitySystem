package UNIENROLSYSTEM;

import java.io.Serializable;

public class Subject implements Serializable {
    private String id;
    private int mark;
    private String grade;

    // Default constructor
    public Subject() {
    }

    // Constructor that takes an ID parameter
    public Subject(String id) {
        this.id = id;
    }

    // ... (rest of the code remains unchanged)

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        if (mark >= 0 && mark <= 100) {
            this.mark = mark;
            this.grade = calculateGrade(mark);
        } else {
            System.out.println("Invalid mark. Please enter a value between 0 and 100.");
        }
    }

    private String calculateGrade(int mark) {
        if (mark >= 85)
            return "HD";
        if (mark >= 75)
            return "D";
        if (mark >= 65)
            return "C";
        if (mark >= 50)
            return "P";
        return "Z";
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Mark: " + mark + ", Grade: " + grade;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Subject subject = (Subject) obj;
        return id.equals(subject.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
