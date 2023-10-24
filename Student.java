import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Student implements Serializable{
    private static final long serialVersionUID = 1L;
    private static final int MAX_SUBJECTS = 4;//max 4 course
    private static final int MIN_MARK = 25;
    private static final int MAX_MARK = 100;
    private int id;
    private String name;
    private String email;
    private String password;
    private List<Subject> subjects;
    
    //1st constructor
    public Student(String email, String password) {
        this.id = generateRandomID();
        this.name = "";
        this.email = email;
        this.password = password;
        this.subjects = new ArrayList<>();
    }
    //2st constructor
    public Student(String name, String email, String password) {
        this.id = generateRandomID();
        this.name = name;
        this.email = email;
        this.password = password;
        this.subjects = new ArrayList<>();
    }

    // check email and password pattern
    public static boolean validateEmailAndPassword(String email, String password){
        return (validateEmail(email) && validatePassword(password));
    }

    public static boolean validateEmail(String email){
        // Email regex pattern
        String emailRegex = "^[A-Za-z0-9+_.-]+@university.com$";
        Pattern emailPattern = Pattern.compile(emailRegex);
        Matcher emailMatcher = emailPattern.matcher(email);
        return emailMatcher.matches();
    }

    public static boolean validatePassword(String password){
        // Password regex pattern
        String passwordRegex = "^[A-Z][A-Za-z]{5,}[0-9]{3,}$";
        Pattern passwordPattern = Pattern.compile(passwordRegex);
        Matcher passwordMatcher = passwordPattern.matcher(password);
        return passwordMatcher.matches();
    }
    
    private int generateRandomID() {
        Random random = new Random();
        return random.nextInt(900000) + 100000; 
    }

    public void enrollSubject(Subject subject) {
        if (subjects.size() < MAX_SUBJECTS) {
            subject.setMark(generateRandomMark());
            subjects.add(subject);
        } else {
            System.out.println("Student can only enroll in 4 subjects maximum.");
        }
    }

    public void dropSubject(Subject subject) {
        subjects.remove(subject);
    }

    private int generateRandomMark() {
        Random random = new Random();
        return random.nextInt(MAX_MARK - MIN_MARK + 1) + MIN_MARK;
    }

    public boolean passCourse() {
        if (subjects.isEmpty()) {
            return false;
        }

        int totalMarks = 0;
        for (Subject subject : subjects) {
            totalMarks += subject.getMark();
        }

        int averageMark = totalMarks / subjects.size();
        return averageMark >= 50;
    }

    // Getter and setter methods for other fields

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    // Subject class (assuming it exists)
    class Subject {
        private String name;
        private int mark;

        public Subject(String name) {
            this.name = name;
        }

        public void setMark(int mark) {
            this.mark = mark;
        }

        public int getMark() {
            return mark;
        }

    }
}
