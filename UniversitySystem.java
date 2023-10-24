import java.util.Scanner;

public class UniversitySystem {
    public static void main(String[] args) {
        Scanner scanner;

        while (true) {
            System.out.print("University System: (A)dmin, (S)tudent, or X:");
            scanner = new Scanner(System.in);
            char choice = Character.toUpperCase(scanner.next().charAt(0));

            switch (choice) {
                case 'A':
                    AdminSystem.adminMenu();
                    break; 
                case 'S':
                    StudentSystem.studentMenu();
                    break; 
                case 'X':
                    System.out.println("Thank you");
                    scanner.close(); 
                    return; 
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
