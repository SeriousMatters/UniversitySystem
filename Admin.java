package UNIENROLSYSTEM;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Admin {
    private List<Student> students;
    private String filePath = "students.data";

    public Admin(List<Student> students) {
        this.students = students;
    }

    void showAllStudents() {
        students = Database.readStudentsFromFile(); // Populate students from the database

        System.out.println("List of All Students:");

        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            for (Student student : students) {
                System.out.println("Student ID: " + student.getId());
                System.out.println("Name: " + student.getName());
                System.out.println("Email: " + student.getEmail());
                System.out.println("Enrolled Subjects:");

                List<Subject> enrolledSubjects = student.getEnrolledSubjects();

                if (enrolledSubjects.isEmpty()) {
                    System.out.println("No enrolled subjects.");
                } else {
                    for (Subject subject : enrolledSubjects) {
                        System.out.println("Subject ID: " + subject.getId() + " -- Mark = " + subject.getMark()
                                + " -- Grade = " + subject.getGrade());
                    }
                }
            }
        }
    }

    public void removeStudent(String studentId) {
        Student studentToRemove = null;

        for (Student student : students) {
            if (student.getId().trim().equalsIgnoreCase(studentId.trim())) {
                studentToRemove = student;
                break;
            }
        }

        if (studentToRemove != null) {
            students.remove(studentToRemove);
            saveStudentsToFile(); // Always save to file after changes
            System.out.println("Student with ID " + studentId + " has been removed.");
        } else {
            System.out.println("Student with ID " + studentId + " not found.");
        }
    }

    public void clearAllStudents() {
        students.clear();
        saveStudentsToFile();
        System.out.println("All student data has been cleared.");
    }

    public void viewStudents() {
        List<Student> students = readStudentsFromFile(filePath);

        if (students.isEmpty()) {
            System.out.println("No student data found.");
        } else {
            System.out.println("List of Students:");
            for (Student student : students) {
                System.out.println(
                        "ID: " + student.getId() + ", Name: " + student.getName() + ", Email: " + student.getEmail());
            }
        }
    }

    public void groupStudents() {
        System.out.println("Grouping students by grade...");

        Map<String, List<Student>> groupedStudents = new HashMap<>();
        List<String> gradeOrder = List.of("HD", "D", "C", "P", "Z"); // Define grade order

        for (Student student : students) {
            String grade = student.getGrade();
            if (groupedStudents.containsKey(grade)) {
                groupedStudents.get(grade).add(student);
            } else {
                List<Student> studentsWithGrade = new ArrayList<>();
                studentsWithGrade.add(student);
                groupedStudents.put(grade, studentsWithGrade);
            }
        }

        for (String grade : gradeOrder) {
            if (groupedStudents.containsKey(grade)) {
                System.out.println("Grade: " + grade);
                for (Student student : groupedStudents.get(grade)) {
                    System.out.println("  " + student.getName() + " (ID: " + student.getId() + ")");
                }
            }
        }
    }

    public void partitionStudents() {
        List<Student> passStudents = new ArrayList<>();
        List<Student> failStudents = new ArrayList<>();

        for (Student student : students) {
            String grade = student.getGrade();
            if (grade.equals("P") || grade.equals("HD") || grade.equals("D") || grade.equals("C")) { // Updated the pass
                                                                                                     // criteria
                passStudents.add(student);
            } else {
                failStudents.add(student);
            }
        }

        System.out.println("Partitioning students...");
        System.out.println("Pass Students:");
        for (Student student : passStudents) {
            System.out.println(
                    "ID: " + student.getId() + ", Name: " + student.getName() + ", Grade: " + student.getGrade());
        }

        System.out.println("\nFail Students:");
        for (Student student : failStudents) {
            System.out.println(
                    "ID: " + student.getId() + ", Name: " + student.getName() + ", Grade: " + student.getGrade());
        }
    }

    public void saveStudentsToFile() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
            outputStream.writeObject(students);
            System.out.println("Student data saved to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to save student data: " + e.getMessage());
        }
    }

    public List<Student> readStudentsFromFile(String filePath) {
        List<Student> students = new ArrayList<>();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filePath))) {
            Object object;
            while (true) {
                try {
                    object = inputStream.readObject();
                    if (object instanceof Student) {
                        students.add((Student) object);
                    }
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error reading student data from the file.");
        }
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}

// package UNIENROLSYSTEM;

// import java.io.EOFException;
// import java.io.FileInputStream;
// import java.io.FileOutputStream;
// import java.io.IOException;
// import java.io.ObjectInputStream;
// import java.io.ObjectOutputStream;
// import java.util.ArrayList;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;

// public class Admin {
// private List<Student> students;
// private String filePath = "students.data";

// public Admin(List<Student> students) {
// this.students = students;
// }

// public void showAllStudents() {
// if (students.isEmpty()) {
// System.out.println("No student data found.");
// } else {
// System.out.println("List of Students:");
// for (Student student : students) {
// System.out.println("ID: " + student.getId() + ", Name: " + student.getName()
// + ", Email: " + student.getEmail());
// }
// }
// }
// // public void groupStudents() {
// // System.out.println("Grouping students by grade...");

// // // Create a map to store students by their grades
// // Map<String, List<Student>> groupedStudents = new HashMap<>();

// // // Group students by their grades
// // for (Student student : students) {
// // String grade = student.getGrade();
// // System.out.println("Processing student: " + student.getName() + " (ID: " +
// student.getId() + ")");
// // System.out.println("Grade: " + grade);

// // // Check if the grade key exists in the map
// // if (groupedStudents.containsKey(grade)) {
// // // If it exists, add the student to the existing list
// // System.out.println("Adding student to existing grade: " + grade);
// // groupedStudents.get(grade).add(student);
// // } else {
// // // If it doesn't exist, create a new list and add the student to it
// // System.out.println("Creating a new grade list for: " + grade);
// // List<Student> studentsWithGrade = new ArrayList<>();
// // studentsWithGrade.add(student);
// // groupedStudents.put(grade, studentsWithGrade);
// // }
// // }

// // // Print the grouped students
// // for (Map.Entry<String, List<Student>> entry : groupedStudents.entrySet())
// {
// // String grade = entry.getKey();
// // List<Student> studentsInGrade = entry.getValue();

// // System.out.println("Grade: " + grade);
// // for (Student student : studentsInGrade) {
// // System.out.println(" " + student.getName() + " (ID: " + student.getId() +
// ")");
// // }
// // }
// // }

// // public void groupStudents() {
// // System.out.println("Grouping students by grade...");

// // // Create a map to store students by their grades
// // Map<String, List<Student>> groupedStudents = new HashMap<>();

// // // Group students by their grades
// // for (Student student : students) {
// // String grade = student.getGrade();
// // // Check if the grade key exists in the map
// // if (groupedStudents.containsKey(grade)) {
// // // If it exists, add the student to the existing list
// // groupedStudents.get(grade).add(student);
// // } else {
// // // If it doesn't exist, create a new list and add the student to it
// // List<Student> studentsWithGrade = new ArrayList<>();
// // studentsWithGrade.add(student);
// // groupedStudents.put(grade, studentsWithGrade);
// // }
// // }

// // // Print the grouped students
// // for (Map.Entry<String, List<Student>> entry : groupedStudents.entrySet())
// {
// // String grade = entry.getKey();
// // List<Student> studentsInGrade = entry.getValue();

// // System.out.println("Grade: " + grade);
// // for (Student student : studentsInGrade) {
// // System.out.println(" " + student.getName() + " (ID: " + student.getId() +
// ")");
// // }
// // }
// // }

// public void partitionStudents() {
// List<Student> passStudents = new ArrayList<>();
// List<Student> failStudents = new ArrayList<>();

// for (Student student : students) {
// String grade = student.getGrade();

// // Check the grade and add the student to the appropriate list
// if (grade.equals("P") || grade.equals("HD")) {
// passStudents.add(student);
// } else {
// failStudents.add(student);
// }
// }

// System.out.println("Partitioning students...");
// System.out.println("Pass Students:");
// for (Student student : passStudents) {
// System.out.println("ID: " + student.getId() + ", Name: " + student.getName()
// + ", Grade: " + student.getGrade());
// }

// System.out.println("\nFail Students:");
// for (Student student : failStudents) {
// System.out.println("ID: " + student.getId() + ", Name: " + student.getName()
// + ", Grade: " + student.getGrade());
// }
// }

// // public void removeStudent(String studentId) {
// // Student studentToRemove = null;

// // for (Student student : students) {
// // if (student.getId().trim().equalsIgnoreCase(studentId.trim())) {
// // studentToRemove = student;
// // break;
// // }
// // }

// // if (studentToRemove != null) {
// // students.remove(studentToRemove);
// // System.out.println("Student with ID " + studentId + " has been removed.");
// // } else {
// // System.out.println("Student with ID " + studentId + " not found.");
// // }
// // }

// public void viewStudents() {
// List<Student> students = readStudentsFromFile(filePath); // Read students
// from the file

// if (students.isEmpty()) {
// System.out.println("No student data found.");
// } else {
// System.out.println("List of Students:");
// for (Student student : students) {
// System.out.println("ID: " + student.getId() + ", Name: " + student.getName()
// + ", Email: " + student.getEmail());
// }
// }
// }
// // public void viewStudents() {
// // // students = readStudentsFromFile(filePath);

// // if (students.isEmpty()) {
// // System.out.println("No student data found.");
// // } else {
// // System.out.println("List of Students:");
// // for (Student student : students) {
// // System.out.println("ID: " + student.getId() + ", Name: " +
// student.getName() + ", Email: " + student.getEmail());
// // }
// // }
// // }

// public List<Student> readStudentsFromFile(String filePath) {
// List<Student> students = new ArrayList<>();
// try (ObjectInputStream inputStream = new ObjectInputStream(new
// FileInputStream(filePath))) {
// Object object = inputStream.readObject();
// if (object instanceof List) {
// List<?> rawList = (List<?>) object;
// for (Object item : rawList) {
// if (item instanceof Student) {
// students.add((Student) item);
// }
// }
// } else {
// System.out.println("Unexpected data format in the file.");
// }
// } catch (EOFException e) {
// System.out.println("End of file reached. Check for file truncation.");
// } catch (IOException e) {
// e.printStackTrace();
// System.out.println("IO Exception: " + e.getMessage());
// } catch (ClassNotFoundException e) {
// e.printStackTrace();
// System.out.println("Class not found: " + e.getMessage());
// }
// return students;
// }

// public void saveStudentsToFile() {
// try (ObjectOutputStream outputStream = new ObjectOutputStream(new
// FileOutputStream(filePath))) {
// outputStream.writeObject(students);
// System.out.println("Student data saved to " + filePath);
// } catch (IOException e) {
// e.printStackTrace();
// System.out.println("Failed to save student data: " + e.getMessage());
// }
// }

// public void setStudents(List<Student> students2) {
// this.students = students2;
// }
// }