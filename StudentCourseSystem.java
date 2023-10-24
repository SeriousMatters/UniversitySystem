import java.util.Scanner;
import java.util.List;

public class StudentCourseSystem {
    private static Student loggedInStudentEmail;
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
                    // add logic
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

}