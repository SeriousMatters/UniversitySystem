package UNIENROLSYSTEM;

import java.util.Scanner;

public class UniversityApp {
    public static void main(String[] args) {
        // Initialize your application
        UniversitySystem universitySystem = new UniversitySystem();

        // Initialize Admin and Database classes
        Admin admin = new Admin(null);
        Database database = new Database();

        // Start the main application loop
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("University System:");
                System.out.println("(A) Admin");
                System.out.println("(S) Student");
                System.out.println("(X) Exit");
                System.out.print("Choose an option: ");
                String option = scanner.next().toLowerCase();

                if (option.equals("a")) {
                    // Admin Menu
                    universitySystem.adminMenu();
                } else if (option.equals("s")) {
                    // Student Menu
                    universitySystem.studentMenu();
                } else if (option.equals("x")) {
                    System.out.println("Exiting the program. Goodbye!");
                    break;
                } else {
                    System.out.println("Invalid option. Please choose a valid option.");
                }
            }
        }

        // Check if the data file exists and create it if not
        database.checkIfFileExists();
        database.createFileIfNotExists();

        // Load student data
        admin.setStudents(Database.getStudents());

        // Show all students
        admin.viewStudents();
    }
}
