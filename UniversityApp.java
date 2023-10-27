package UNIENROLSYSTEM;

import java.util.Scanner;

public class UniversityApp {
    public static void main(String[] args) {
        // Initialize your application
        UniversitySystem universitySystem = new UniversitySystem();

        // Initialize Admin and Database classes
        Admin admin = new Admin(null);
        Database database = new Database();

        // Check if the data file exists and create it if not
        database.checkIfFileExists();
        database.createFileIfNotExists();

        // Load student data into the admin class for use
        admin.setStudents(Database.getStudents());

        // Start the main application loop
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("===================================");
                System.out.println("Welcome to the University System:");
                System.out.println("(A) Access Admin Menu");
                System.out.println("(S) Access Student Menu");
                System.out.println("(X) Exit");
                System.out.print("Choose an option: ");
                String option = scanner.next().toLowerCase();

                try {
                    if (option.equals("a")) {
                        // Admin Menu
                        System.out.println("Entering Admin Menu...");
                        universitySystem.adminMenu();
                    } else if (option.equals("s")) {
                        // Student Menu
                        System.out.println("Entering Student Menu...");
                        universitySystem.studentMenu();
                    } else if (option.equals("x")) {
                        System.out.println("Exiting the program. Goodbye!");
                        break;
                    } else {
                        System.out.println("Invalid option. Please choose a valid option.");
                    }
                } catch (Exception e) {
                    System.out.println("An error occurred: " + e.getMessage());
                }
            }
        }
    }
}
