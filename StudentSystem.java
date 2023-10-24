import java.util.Scanner;

public class StudentSystem {
       public static void studentMenu() {
        Scanner scanner = new Scanner(System.in);
        char choice;

        while(true) {
            System.out.print("Student System (l/r/x):");
            choice = Character.toUpperCase(scanner.next().charAt(0));
            switch (choice) {
                case 'L':
                    System.out.println("Enrolling in a course...");
                    // Add logic for enrolling in a course
                    break;
                case 'R':
                    Student.signUp();
                    // Add logic for dropping a course
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
