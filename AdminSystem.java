import java.util.Scanner;

public class AdminSystem {

    public static void adminMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Admin System (c/g/p/r/s/x):");
            char choice = Character.toUpperCase(scanner.next().charAt(0));
            switch (choice) {
                case 'C':
                    System.out.println("Create a new student...");

                    break;
                case 'G':
                    System.out.println("Edit student information...");

                    break;
                case 'P':
                    System.out.println("Delete a student...");

                    break;
                case 'R':
                    System.out.println("Retrieve student information...");

                    break;
                case 'S':
                    System.out.println("Show all students...");

                    break;
                case 'X':
                    System.out.println("Exiting Admin System...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
