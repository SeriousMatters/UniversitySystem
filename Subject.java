import java.util.Random;

public class Subject {
    private static final int MIN_ID = 1;
    private static final int MAX_ID = 999;
    private static final int MIN_MARK = 25;
    private static final int MAX_MARK = 100;

    private int id;
    private int mark;
    private String grade;

    public Subject() {
        this.id = generateRandomID();
        this.mark = generateRandomMark();
        this.grade = calculateGrade();
    }

    private int generateRandomID() {
        Random random = new Random();
        return random.nextInt(MAX_ID - MIN_ID + 1) + MIN_ID;
    }

    private int generateRandomMark() {
        Random random = new Random();
        return random.nextInt(MAX_MARK - MIN_MARK + 1) + MIN_MARK;
    }

    private String calculateGrade() {
        if (mark >= 90) {
            return "A";
        } else if (mark >= 80) {
            return "B";
        } else if (mark >= 70) {
            return "C";
        } else if (mark >= 60) {
            return "D";
        } else {
            return "F";
        }
    }

    // Getter and setter methods

    public int getId() {
        return id;
    }

    public int getMark() {
        return mark;
    }

    public String getGrade() {
        return grade;
    }

    @Override
    public String toString() {
        return "Subject ID: " + String.format("%03d", id) +
               "\nMark: " + mark +
               "\nGrade: " + grade;
    }
}
