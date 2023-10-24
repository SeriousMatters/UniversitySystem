import java.util.Scanner;
import java.util.List;

public class StudentSystem {
    public static void studentMenu() {
        Scanner scanner = new Scanner(System.in);
        char choice;

        while(true) {
            System.out.print("Student System (l/r/x):");
            choice = Character.toUpperCase(scanner.next().charAt(0));
            switch (choice) {
                case 'L':
                    signIn();
                    break;
                case 'R':
                    signUp();
                    break;
                case 'X':
                    System.out.println("Exiting Student System...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // signUp method 
    public static void signUp() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Student Sign Up");
        boolean flag = false;
        while(!flag) {
            System.out.print("Email: ");
            String email = scanner.next();
            System.out.print("Password: ");
            String password = scanner.next();

            //!emailMatcher.matches()||!passwordMatcher.matches()
            if (!Student.validateEmailAndPassword(email, password)) {
                flag = false;
                System.out.println("Incorrect email or password format");
            }
            else{
                flag = true;
                System.out.println("email and password formats accepted");
            }

            if(flag){
                // Create and add the student to the database using the 1st constructor
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
                    student.setName(name);
                    students.add(student);
                    Database.writeObjectsToFile(students);
                    // System.out.println("Student added to the database.");
                }
            }
        }
    }

    public static void signIn() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Student Sign In");
        boolean flag = false;
        while(!flag) {
            System.out.print("Email: ");
            String email = scanner.next();
            System.out.print("Password: ");
            String password = scanner.next();

            // Validate email or email
            if (!Student.validateEmailAndPassword(email, password)) {
                flag = false;
                System.out.println("Incorrect email or password format");
            }
            else{
                flag = true;
                System.out.println("email and password formats accepted");
            }

            if(flag) {
                // Create and add the student to the database using the 1st constructor
                // Student student = new Student(email, password);
                List<Student> students = Database.readAllStudents();
                boolean exists = false;
                for (Student existingStudent : students) {
                    if (existingStudent.getEmail().equals(email)) {
                        if (existingStudent.getPassword().equals(password)) {
                            exists = true;
                            StudentCourseSystem.studentCourseMenu(email);
                        }
                        break;
                    }
                }
                if (!exists) {
                    System.out.println("Student not exist");
                }
            }
        }
    }

}
