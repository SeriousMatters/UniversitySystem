package UNIENROLSYSTEM;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UniversitySystem {
    private List<Student> students = new ArrayList<>();

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    private Admin admin;
    private Database database;
    private Scanner scanner;
    private String loggedInStudentEmail;

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final String PASSWORD_REGEX = "^[A-Z].{5,}[0-9]{3,}$";

    public UniversitySystem() {
        database = new Database();
        scanner = new Scanner(System.in);
        admin = new Admin(students); // Initialize the admin
    }

    public void universityMenu() {
        while (true) {
            System.out.println("University System:");
            System.out.println("(A) Admin");
            System.out.println("(S) Student");
            System.out.println("(X) Exit");
            System.out.print("Choose an option: ");
            String option = scanner.next().toLowerCase();

            if (option.equals("a")) {
                adminMenu();
            } else if (option.equals("s")) {
                studentMenu();
            } else if (option.equals("x")) {
                System.out.println("Exiting the program. Goodbye!");
                break;
            } else {
                System.out.println("Invalid option. Please choose a valid option.");
            }
        }
    }

    public void adminMenu() {
        while (true) {
            System.out.println("Admin Menu:");
            System.out.println("(c) Clear database file");
            System.out.println("(g) Group students");
            System.out.println("(p) Partition students");
            System.out.println("(r) Remove student by ID");
            System.out.println("(s) Show all students");
            System.out.println("(x) Exit");
            System.out.print("Choose an option: ");
            char choice = scanner.next().charAt(0);

            switch (choice) {
                case 'c':
                    clearDatabaseFile();
                    break;
                case 'g':
                    groupStudents();
                    break;
                case 'p':
                    partitionStudents();
                    break;
                case 'r':
                    removeStudent();
                    break;
                case 's':
                    showAllStudents();
                    break;
                case 'x':
                    System.out.println("Exiting Admin Menu.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void showAllStudents() {
        admin.showAllStudents();
    }
    // students = Database.readStudentsFromFile(); // Populate students from the
    // database

    // System.out.println("List of All Students:");

    // if (students.isEmpty()) {
    // System.out.println("No students found.");
    // } else {
    // for (Student student : students) {
    // System.out.println("Student ID: " + student.getId());
    // System.out.println("Name: " + student.getName());
    // System.out.println("Email: " + student.getEmail());
    // System.out.println("Enrolled Subjects:");

    // List<Subject> enrolledSubjects = student.getEnrolledSubjects();

    // if (enrolledSubjects.isEmpty()) {
    // System.out.println("No enrolled subjects.");
    // } else {
    // for (Subject subject : enrolledSubjects) {
    // System.out.println("Subject ID: " + subject.getId() + " -- Mark = " +
    // subject.getMark() + " -- Grade = " + subject.getGrade());
    // }
    // }
    // }
    // }

    // private void showAllStudents() {
    // System.out.println("List of All Students:");

    // if (students.isEmpty()) {
    // System.out.println("No students found.");
    // } else {
    // for (Student student : students) {
    // System.out.println("Student ID: " + student.getId());
    // System.out.println("Name: " + student.getName());
    // System.out.println("Email: " + student.getEmail());
    // System.out.println("Enrolled Subjects:");

    // List<Subject> enrolledSubjects = student.getEnrolledSubjects();

    // if (enrolledSubjects.isEmpty()) {
    // System.out.println("No enrolled subjects.");
    // } else {
    // for (Subject subject : enrolledSubjects) {
    // System.out.println("Subject ID: " + subject.getId() + " -- Mark = " +
    // subject.getMark() + " -- Grade = " + subject.getGrade());
    // }
    // }
    // }
    // }
    // }

    public void studentMenu() {
        while (true) {
            System.out.println("Student Menu");
            System.out.println("(l) Login");
            System.out.println("(r) Register");
            System.out.println("(x) Exit");
            System.out.print("Choose an option: ");
            char choice = scanner.next().charAt(0);

            switch (choice) {
                case 'l':
                    login();
                    break;
                case 'r':
                    register();
                    break;
                case 'x':
                    System.out.println("Exiting Student Menu.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void clearDatabaseFile() {
        database.clearFile();
        System.out.println("Database file has been cleared.");
    }

    public void groupStudents() {
        System.out.println("Grouping students by grade...");

        // Create a map to store students by their grades
        Map<String, List<Student>> groupedStudents = new HashMap<>();

        // Group students by their grades
        for (Student student : students) {
            String grade = student.getGrade();
            System.out.println("Processing student: " + student.getName() + " (ID: " + student.getId() + ")");
            System.out.println("Grade: " + grade);

            // Check if the grade key exists in the map
            if (groupedStudents.containsKey(grade)) {
                // If it exists, add the student to the existing list
                System.out.println("Adding student to existing grade: " + grade);
                groupedStudents.get(grade).add(student);
            } else {
                // If it doesn't exist, create a new list and add the student to it
                System.out.println("Creating a new grade list for: " + grade);
                List<Student> studentsWithGrade = new ArrayList<>();
                studentsWithGrade.add(student);
                groupedStudents.put(grade, studentsWithGrade);
            }
        }

        // Print the grouped students
        for (Map.Entry<String, List<Student>> entry : groupedStudents.entrySet()) {
            String grade = entry.getKey();
            List<Student> studentsInGrade = entry.getValue();

            System.out.println("Grade: " + grade);
            for (Student student : studentsInGrade) {
                System.out.println("  " + student.getName() + " (ID: " + student.getId() + ")");
            }
        }
    }

    private void partitionStudents() {
        admin.partitionStudents();
    }

    private void removeStudent() {
        admin.removeStudent(loggedInStudentEmail);
    }

    public void removeStudent(String studentId) {
        Student studentToRemove = null;

        for (Student student : students) {
            if (student.getId().equalsIgnoreCase(studentId.trim())) {
                studentToRemove = student;
                break;
            }
        }

        if (studentToRemove != null) {
            students.remove(studentToRemove);
            admin.setStudents(students); // Update the list of students in the database
            System.out.println("Student with ID " + studentId + " has been removed.");
        } else {
            System.out.println("Student with ID " + studentId + " not found.");
        }
    }

    // System.out.print("Enter the ID of the student to remove: ");
    // String studentId = scanner.next();

    // // Find and remove the student from the list of students
    // Student studentToRemove = null;
    // for (Student student : students) {
    // if (student.getId().equals(studentId)) {
    // studentToRemove = student;
    // break;
    // }
    // }

    // if (studentToRemove != null) {
    // students.remove(studentToRemove);
    // admin.setStudents(students); // Update the list of students in the database
    // System.out.println("Student with ID " + studentId + " has been removed.");
    // } else {
    // System.out.println("Student with ID " + studentId + " not found.");
    // }
    // }

    // private void removeStudent() {
    // System.out.print("Enter the ID of the student to remove: ");
    // String studentId = scanner.next();
    // admin.setStudents(students);
    // admin.removeStudent(studentId);
    // }

    private void register() {
        System.out.println("Student Registration");
        System.out.print("Enter your first name: ");
        String firstName = scanner.next();
        System.out.print("Enter your last name: ");
        String lastName = scanner.next();

        String email = generateEmail(firstName, lastName).toLowerCase();
        System.out.print("Enter your password: ");
        String password = scanner.next();

        Student newStudent = new Student(firstName + " " + lastName, email, password);

        System.out.println("File path for reading: " + Database.getFilePath());

        List<Student> studentList = Database.getStudents();

        if (studentList == null) {
            studentList = new ArrayList<>();
        }

        studentList.add(newStudent);

        System.out.println("Registration successful!");
        System.out.println("Student Details:");
        System.out.println("Name: " + newStudent.getName());
        System.out.println("ID: " + newStudent.getId());
        System.out.println("Email: " + newStudent.getEmail());

        Database.saveStudentsToFile(studentList);
    }

    // private void enrollStudent(Student student) {
    // System.out.println("Enroll in Subjects:");

    // List<Subject> availableSubjects = getAvailableSubjects();
    // System.out.println("Available Subjects:");

    // if (availableSubjects.isEmpty()) {
    // System.out.println("No subjects are available for enrollment.");
    // return;
    // }

    // int maxEnrollment = 4;
    // int enrolledCount = 0;

    // for (int i = 0; i < availableSubjects.size(); i++) {
    // Subject subject = availableSubjects.get(i);
    // System.out.println((i + 1) + ". Subject ID: " + subject.getId() +
    // " -- Mark: " + subject.getMark() + " -- Grade: " + subject.getGrade());

    // if (enrolledCount >= maxEnrollment) {
    // System.out.println("You are now enrolled in " + enrolledCount +
    // " out of " + maxEnrollment + " subjects.");
    // break;
    // }

    // System.out.print("Enroll in this subject? (Y/N): ");
    // String input = scanner.next().toLowerCase();

    // if (input.equals("y")) {
    // student.enrollSubject(subject);
    // System.out.println("Enrolled in subject: " + subject.getId());
    // enrolledCount++;
    // }
    // }

    // System.out.println("You are now enrolled in " + enrolledCount +
    // " out of " + maxEnrollment + " subjects.");
    // }

    // private void enrollStudent(Student newStudent) {
    // }

    // private List<Subject> getAvailableSubjects() {
    // List<Subject> availableSubjects = new ArrayList<>();
    // Random random = new Random();

    // for (int i = 0; i < 10; i++) {
    // int randomSubjectID = random.nextInt(1000);
    // int randomSubjectMark = random.nextInt(76) + 25;

    // String grade = calculateGrade(randomSubjectMark);

    // Subject subject = new Subject();
    // subject.setId(String.format("%03d", randomSubjectID));
    // subject.setMark(randomSubjectMark);
    // subject.setGrade(grade);

    // availableSubjects.add(subject);
    // }

    // return availableSubjects;
    // }
    public void assignGradesToSubjects(List<Subject> subjects) {
        for (Subject subject : subjects) {
            int mark = subject.getMark();
            String grade = calculateGrade(mark);
            subject.setGrade(grade);
        }
    }

    private String calculateGrade(int mark) {
        if (mark >= 85) {
            return "HD";
        } else if (mark >= 75) {
            return "D";
        } else if (mark >= 65) {
            return "C";
        } else if (mark >= 50) {
            return "P";
        } else if (mark >= 0) {
            return "Z";
        } else {
            return "Invalid";
        }
    }

    private String generateEmail(String firstName, String lastName) {
        return firstName + "." + lastName + "@university.com";
    }

    private void login() {
        System.out.println("Student Login");
        System.out.print("Enter your email: ");
        String email = scanner.next();
        System.out.print("Enter your password: ");
        String password = scanner.next();

        List<Student> students = Database.readStudentsFromFile();
        boolean loggedIn = false;

        if (students != null) {
            for (Student student : students) {
                loggedInStudentEmail = student.getEmail(); // Set the loggedInStudentEmail
                if (validateEmail(email) && validatePassword(password) &&
                        student.getEmail().equals(email) && student.getPassword().equals(password)) {
                    System.out.println("Login successful!");
                    loggedIn = true;
                    loggedInStudentEmail = email;
                    studentMenuAfterLogin();
                    break;
                }
            }
        }

        if (!loggedIn) {
            System.out.println("Login failed. Please check your credentials.");
        }

        System.out.println("Debug: loggedInStudentEmail = " + loggedInStudentEmail);
    }

    private void studentMenuAfterLogin() {
        // ...

        while (true) {
            System.out.println("Student Menu (Logged In)");
            System.out.println("(e) Enroll in subjects");
            System.out.println("(v) View enrolled subjects");
            System.out.println("(r) Remove subject by Subject ID");
            System.out.println("(c) Change password");
            System.out.println("(x) Logout");
            System.out.print("Choose an option: ");
            char choice = scanner.next().charAt(0);

            switch (choice) {
                case 'e':
                    enrollSubjects();
                    break;
                case 'v':
                    viewEnrolledSubjects();
                    break;
                case 'r':
                    removeEnrolledSubject(); // Add this case for removing a subject
                    break;
                case 'c':
                    changePassword();
                    break;
                case 'x':
                    System.out.println("Logging out.");
                    loggedInStudentEmail = null;
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void removeEnrolledSubject() {
        Student student = findStudentByEmail(loggedInStudentEmail);

        if (student != null) {
            List<Subject> enrolledSubjects = student.getEnrolledSubjects();

            if (enrolledSubjects.isEmpty()) {
                System.out.println("You have not enrolled in any subjects yet.");
            } else {
                System.out.println("Enrolled Subjects:");
                for (Subject subject : enrolledSubjects) {
                    System.out.println("Subject ID: " + subject.getId() + " -- Mark = " + subject.getMark()
                            + " -- Grade = " + subject.getGrade());
                }

                System.out.print("Enter the ID of the subject to remove: ");
                String subjectId = scanner.next();

                boolean removed = false;

                for (Subject subject : enrolledSubjects) {
                    if (subject.getId().equals(subjectId)) {
                        enrolledSubjects.remove(subject);
                        removed = true;
                        System.out.println("Subject with ID " + subjectId + " has been removed.");
                        break; // Exit the loop after removal
                    }
                }

                if (removed) {
                    student.setEnrolledSubjects(enrolledSubjects);
                    Database.updateStudent(student); // Update the student's data in the database
                } else {
                    System.out.println("Subject with ID " + subjectId + " not found in your enrolled subjects.");
                }
            }
        } else {
            System.out.println("Student not found. Please log in again.");
            loggedInStudentEmail = null;
        }
    }

    // private void removeEnrolledSubject() {
    // if (loggedInStudentEmail == null) {
    // System.out.println("You are not logged in. Please log in first.");
    // return;
    // }

    // Student student = findStudentByEmail(loggedInStudentEmail);

    // if (student != null) {
    // List<Subject> enrolledSubjects = student.getEnrolledSubjects();

    // if (enrolledSubjects.isEmpty()) {
    // System.out.println("You have not enrolled in any subjects yet.");
    // } else {
    // System.out.println("Enrolled Subjects:");
    // for (Subject subject : enrolledSubjects) {
    // System.out.println("Subject ID: " + subject.getId() + " -- Mark = " +
    // subject.getMark() + " -- Grade = " + subject.getGrade());
    // }

    // System.out.print("Enter the ID of the subject to remove: ");
    // String subjectId = scanner.next();

    // for (Subject subject : enrolledSubjects) {
    // if (subject.getId().equals(subjectId)) {
    // enrolledSubjects.remove(subject);
    // System.out.println("Subject with ID " + subjectId + " has been removed.");
    // student.setEnrolledSubjects(enrolledSubjects);
    // updateStudent(student); // Update the student's data in the database
    // return;
    // }
    // }
    // System.out.println("Subject with ID " + subjectId + " not found in your
    // enrolled subjects.");
    // }
    // } else {
    // System.out.println("Student not found. Please log in again.");
    // loggedInStudentEmail = null;
    // }
    // }

    // private void studentMenuAfterLogin() {
    // System.out.println("Debug: Inside studentMenuAfterLogin, loggedInStudentEmail
    // = " + loggedInStudentEmail);

    // while (true) {
    // System.out.println("Student Menu (Logged In)");
    // System.out.println("(e) Enroll in subjects");
    // System.out.println("(v) View enrolled subjects");
    // System.out.println("(c) Change password");
    // System.out.println("(x) Logout");
    // System.out.print("Choose an option: ");
    // char choice = scanner.next().charAt(0);

    // private void updateStudent(Student student) {
    // }

    // switch (choice) {
    // case 'e':
    // enrollSubjects();
    // break;
    // case 'v':
    // viewEnrolledSubjects();
    // break;
    // case 'c':
    // changePassword();
    // break;
    // case 'x':
    // System.out.println("Logging out.");
    // loggedInStudentEmail = null;
    // return;
    // default:
    // System.out.println("Invalid choice. Please try again.");
    // }
    // }
    // }
    private void enrollSubjects() {
        Student student = findStudentByEmail(loggedInStudentEmail);

        if (student == null) {
            System.out.println("Student not found. Please log in again.");
            loggedInStudentEmail = null;
            return;
        }

        if (student.getEnrolledSubjects().size() >= 4) {
            System.out.println(
                    "You are now enrolled in 4 out of 4 subjects. Students are allowed to enroll in 4 subjects only.");
            return;
        }

        int maxEnrollment = 4;
        int enrolledCount = student.getEnrolledSubjects().size();

        if (enrolledCount >= maxEnrollment) {
            System.out.println("You are now enrolled in " + enrolledCount + " out of " + maxEnrollment + " subjects.");
            return;
        }

        List<Subject> availableSubjects = getAutoGeneratedSubjects(student);
        if (availableSubjects.isEmpty()) {
            System.out.println("No subjects are available for enrollment.");
            return;
        }

        // Limit the enrollment to a maximum of 4 subjects
        int enrollmentLimit = maxEnrollment - enrolledCount;
        int subjectsToEnroll = Math.min(availableSubjects.size(), enrollmentLimit);

        for (int i = 0; i < subjectsToEnroll; i++) {
            Subject subject = availableSubjects.get(i);
            student.enrollSubject(subject);
            System.out.println("Enrolled in subject: " + subject.getId());
        }

        System.out.println("You are now enrolled in " + (enrolledCount + subjectsToEnroll) + " out of " + maxEnrollment
                + " subjects.");

        // Save the updated student data to the database
        Database.updateStudent(student);
    }

    private List<Subject> getAutoGeneratedSubjects(Student student) {
        List<Subject> availableSubjects = new ArrayList<>();
        Random random = new Random();

        // Generate random subject details for enrollment
        int randomSubjectID = random.nextInt(1000);
        int randomSubjectMark = random.nextInt(76) + 25;

        // Generate a grade based on the random mark
        String grade = calculateGrade(randomSubjectMark);

        // Create a subject with random details
        Subject subject = new Subject();
        subject.setId(String.format("%03d", randomSubjectID));
        subject.setMark(randomSubjectMark);
        subject.setGrade(grade);

        availableSubjects.add(subject);

        return availableSubjects;
    }
    // private void enrollSubjects() {
    // Student student = findStudentByEmail(loggedInStudentEmail);

    // if (student == null) {
    // System.out.println("Student not found. Please log in again.");
    // loggedInStudentEmail = null;
    // return;
    // }

    // if (student.getEnrolledSubjects().size() >= 4) {
    // System.out.println("You have already enrolled in the maximum number of
    // subjects.");
    // return;
    // }

    // // Auto-generate and enroll up to 4 subjects
    // List<Subject> autoGeneratedSubjects = getAutoGeneratedSubjects();

    // for (Subject subject : autoGeneratedSubjects) {
    // student.enrollSubject(subject);
    // System.out.println("Enrolled in subject: " + subject.getId());
    // }

    // System.out.println("You are now enrolled in " + autoGeneratedSubjects.size()
    // + " subjects.");
    // }

    // private List<Subject> getAutoGeneratedSubjects() {
    // List<Subject> autoGeneratedSubjects = new ArrayList<>();
    // Random random = new Random();

    // // Enroll the student in a maximum of 4 subjects
    // for (int i = 0; i < 4; i++) {
    // int randomSubjectID = random.nextInt(1000);
    // int randomSubjectMark = random.nextInt(76) + 25;

    // String grade = calculateGrade(randomSubjectMark);

    // Subject subject = new Subject();
    // subject.setId(String.format("%03d", randomSubjectID));
    // subject.setMark(randomSubjectMark);
    // subject.setGrade(grade);

    // autoGeneratedSubjects.add(subject);
    // }

    // return autoGeneratedSubjects;
    // }

    // private void enrollSubjects() {
    // Student student = findStudentByEmail(loggedInStudentEmail);

    // if (student == null) {
    // System.out.println("Student not found. Please log in again.");
    // loggedInStudentEmail = null;
    // return;
    // }

    // if (student.getEnrolledSubjects().size() >= 4) {
    // System.out.println("You have already enrolled in the maximum number of
    // subjects.");
    // return;
    // }

    // List<Subject> availableSubjects = getAvailableSubjects();

    // if (availableSubjects.isEmpty()) {
    // System.out.println("No subjects are available for enrollment.");
    // return;
    // }

    // int maxEnrollment = 4;
    // int enrolledCount = student.getEnrolledSubjects().size();

    // System.out.println("Available Subjects:");

    // for (int i = 0; i < availableSubjects.size(); i++) {
    // Subject subject = availableSubjects.get(i);
    // System.out.println((i + 1) + ". Subject ID: " + subject.getId() +
    // " -- Mark: " + subject.getMark() + " -- Grade: " + subject.getGrade());

    // if (enrolledCount >= maxEnrollment) {
    // System.out.println("You are now enrolled in " + enrolledCount +
    // " out of " + maxEnrollment + " subjects.");
    // break;
    // }

    // System.out.print("Enroll in this subject? (Y/N): ");
    // String input = scanner.next().toLowerCase();

    // if (input.equals("y")) {
    // student.enrollSubject(subject);
    // System.out.println("Enrolled in subject: " + subject.getId());
    // enrolledCount++;
    // }
    // }

    // System.out.println("You are now enrolled in " + enrolledCount +
    // " out of " + maxEnrollment + " subjects.");
    // }

    private void changePassword() {
        if (loggedInStudentEmail == null) {
            System.out.println("You are not logged in. Please log in first.");
            return;
        }

        List<Student> students = Database.readStudentsFromFile();
        Student studentToUpdate = null;

        for (Student student : students) {
            if (student.getEmail().equals(loggedInStudentEmail)) {
                studentToUpdate = student;
                break;
            }
        }

        if (studentToUpdate != null) {
            System.out.print("Enter your new password: ");
            String newPassword = scanner.next();
            studentToUpdate.setPassword(newPassword);

            // Now save the updated student data back to the database
            Database.saveStudentsToFile(students);

            System.out.println("Password changed successfully.");
        } else {
            System.out.println("Student not found. Please log in again.");
            loggedInStudentEmail = null;
        }
    }

    // private void changePassword() {
    // if (loggedInStudentEmail == null) {
    // System.out.println("You are not logged in. Please log in first.");
    // return;
    // }

    // Student student = findStudentByEmail(loggedInStudentEmail);

    // if (student != null) {
    // System.out.print("Enter your new password: ");
    // String newPassword = scanner.next();
    // student.setPassword(newPassword);
    // System.out.println("Password changed successfully.");
    // } else {
    // System.out.println("Student not found. Please log in again.");
    // loggedInStudentEmail = null;
    // }
    // }

    private void viewEnrolledSubjects() {
        // if (loggedInStudentEmail == null) {
        // System.out.println("You are not logged in. Please log in first.");
        // return;
        // }

        Student student = findStudentByEmail(loggedInStudentEmail);

        // if (student == null) {
        // System.out.println("Student not found. Please log in again.");
        // loggedInStudentEmail = null;
        // return;
        // }

        // if (student != null) {
        // List<Subject> enrolledSubjects = student.getEnrolledSubjects();

        // if (enrolledSubjects.isEmpty()) {
        // System.out.println("You have not enrolled in any subjects yet.");
        // } else {
        // System.out.println("Enrolled Subjects:");
        // for (Subject subject : enrolledSubjects) {
        // System.out.println("Subject ID: " + subject.getId());
        // System.out.println("Subject Mark: " + subject.getMark());
        // System.out.println("Subject Grade: " + subject.getGrade());
        // }
        // }
        // } else {
        // System.out.println("Student not found. Please log in again.");
        // loggedInStudentEmail = null;
        // }

        List<Subject> enrolledSubjects = student.getEnrolledSubjects();

        if (enrolledSubjects.isEmpty()) {
            System.out.println("You have not enrolled in any subjects yet.");
        } else {
            System.out.println("Enrolled Subjects:");
            for (Subject subject : enrolledSubjects) {
                System.out.println("Subject:: " + subject.getId() +
                        " -- Mark = " + subject.getMark() +
                        " -- Grade = " + subject.getGrade());
            }
        }
    }

    private Student findStudentByEmail(String email) {
        List<Student> students = Database.readStudentsFromFile();

        if (students != null) {
            for (Student student : students) {
                if (student.getEmail().equals(email)) {
                    return student;
                }
            }
        }

        return null;
    }

    private boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean validatePassword(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    // public static void main(String[] args) {
    // UniversitySystem universitySystem = new UniversitySystem();
    // universitySystem.universityMenu();
    // }
}