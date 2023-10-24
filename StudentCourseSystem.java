import java.util.Scanner;
import java.util.List;

public class StudentCourseSystem {
    private static String loggedInStudentEmail;
    private static Scanner scanner;

    public static void studentCourseMenu(String email) {
        loggedInStudentEmail = email;
        scanner = new Scanner(System.in);

        char choice;
        while(true) {
            System.out.print("Student Course Menu (c/e/r/s/x): ");
            choice = Character.toUpperCase(scanner.next().charAt(0));
            switch (choice) {
                
                case 'C':
                    // Change Password
                    changePassword();
                    break;
                case 'E':
                    // Enrol course
                    // add logic
                    break;
                case 'R':
                    // Remove by Subject ID
                    // add logic
                    break;
                case 'S':
                    // Show enrolled course
                    // add logic
                    break;
                case 'X':
                    System.out.println("Exiting Student System...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void changePassword() {
        if (loggedInStudentEmail == null) {
            System.out.println("You are not logged in. Please log in first.");
            return;
        }
    
        List<Student> students = Database.readAllStudents();
        Student studentToUpdate = null;
    
        for (Student student : students) {
            if (student.getEmail().equals(loggedInStudentEmail)) {
                studentToUpdate = student;
                break;
            }
        }
        if (studentToUpdate != null) {
            String newPassword = "";
            String confirmPassword = "";

            boolean passwordValid = false;
            while(!passwordValid){
                System.out.print("New password: ");
                newPassword = scanner.next();
                if (Student.validatePassword(newPassword)) {
                    passwordValid = true;
                }
            }
            
            boolean passwordsMatch = false;
            while (!passwordsMatch) {
                System.out.print("Confirm password: ");
                confirmPassword = scanner.next();
                if (newPassword.equals(confirmPassword)) {
                    passwordsMatch = true;
                    studentToUpdate.setPassword(newPassword);
                    // Now save the updated student data back to the database
                    Database.writeObjectsToFile(students);
                    System.out.println("Password changed successfully.");
                } else {
                    System.out.println("Password does not match");
                }
            }

        } else {
            System.out.println("Student not found. Please log in again.");
            loggedInStudentEmail = null;
        }
    }
}