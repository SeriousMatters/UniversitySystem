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

    // signUp method 
    public static void signUp() {
        Scanner scanner = new Scanner(System.in);

        // Email regex pattern
        String emailRegex = "^[A-Za-z0-9+_.-]+@university.com$";
        Pattern emailPattern = Pattern.compile(emailRegex);

        // Password regex pattern
        String passwordRegex = "^[A-Z][A-Za-z]{5,}[0-9]{3,}$";
        Pattern passwordPattern = Pattern.compile(passwordRegex);

        System.out.println("Student Sign Up");
        boolean flag = false;
        while(!flag) {
            System.out.print("Email: ");
            String email = scanner.next();
            System.out.print("Password: ");
            String password = scanner.next();

            // Validate email or email
            Matcher emailMatcher = emailPattern.matcher(email);
            Matcher passwordMatcher = passwordPattern.matcher(password);
            //!emailMatcher.matches()||!passwordMatcher.matches()
            if (!emailMatcher.matches()||!passwordMatcher.matches()) {
                flag = false;
                System.out.println("Incorrect email or password format");
            }
            else{
                flag = true;
            }

            if(flag){
                // Create and add the student to the database using the 1st constructor
                System.out.println("email and password formats accepted");
                Student student = new Student(email, password);
                List<Student> students = Database.readAllStudents();
                boolean exists = false;
                for (Student existingStudent : students) {
                    if (existingStudent.getEmail().equals(student.getEmail())) {
                        exists = true;
                        break;
                    }
                }
                if (exists) {
                    String subname = email.substring(0, email.indexOf('@'));
                    String[] token = subname.split(".");
                    System.out.println(token[0]+" "+token[1]+" already exists");
                } else {
                    System.out.print("Name: ");
                    String name = scanner.next();
                    student.name = name;
                    students.add(student);
                    Database.writeObjectsToFile(students);
                    System.out.println("Student added to the database.");
                }


            }
        }


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

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    // Getter and setter methods for other fields

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
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

        // Getter and setter methods for other fields
    }
}
