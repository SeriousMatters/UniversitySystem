package UNIENROLSYSTEM;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static final String filePath = "students.data";

    // @SuppressWarnings("unchecked")
    // public static List<Student> readStudentsFromFile() {
    // try (ObjectInputStream inputStream = new ObjectInputStream(new
    // FileInputStream(filePath))) {
    // return (List<Student>) inputStream.readObject();
    // } catch (IOException | ClassNotFoundException e) {
    // e.printStackTrace();
    // System.out.println("Error reading student data from the file.");
    // }
    // return new ArrayList<>();
    // }

    // public static void writeStudentsToFile(List<Student> studentList) {
    // try (ObjectOutputStream outputStream = new ObjectOutputStream(new
    // FileOutputStream(filePath))) {
    // outputStream.writeObject(studentList);
    // System.out.println("Student data written to the file.");
    // } catch (IOException e) {
    // e.printStackTrace();
    // System.out.println("Error writing student data to the file.");
    // }
    // }

    public void checkIfFileExists() {
        File file = new File(filePath);
        if (file.exists() && !file.isDirectory()) {
            System.out.println("The data file exists.");
        } else {
            System.out.println("The data file does not exist.");
        }
    }

    public void createFileIfNotExists() {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("Data file created successfully.");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error creating the data file.");
            }
        }
    }

    public void clearFile() {
        try {
            PrintWriter writer = new PrintWriter(filePath);
            writer.print("");
            writer.close();
            System.out.println("Data file cleared successfully.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error clearing the data file.");
        }
    }

    public static String getFilePath() {
        return filePath;
    }

    public static void setStudents(List<Student> studentList) {
        saveStudentsToFile(studentList);
    }

    public static void saveStudentsToFile(List<Student> studentList) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
            outputStream.writeObject(studentList);
            System.out.println("Student data saved to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to save student data: " + e.getMessage());
        }
    }

    // public static List<Student> readStudentsFromFile(String filePath) {
    // List<Student> students = new ArrayList<>();
    // File file = new File(filePath);

    // if (file.exists()) {
    // try (ObjectInputStream inputStream = new ObjectInputStream(new
    // FileInputStream(filePath))) {
    // // Your existing code to read the students
    // } catch (IOException e) {
    // e.printStackTrace();
    // System.out.println("Error reading student data from the file.");
    // }
    // } else {
    // System.out.println("Student data file does not exist.");
    // }

    // return students;
    // }

    public static List<Student> readStudentsFromFile() {
        List<Student> students = new ArrayList<>();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filePath))) {
            while (true) {
                try {
                    Object object = inputStream.readObject();
                    if (object instanceof List) {
                        List<?> rawList = (List<?>) object;
                        for (Object item : rawList) {
                            if (item instanceof Student) {
                                students.add((Student) item);
                            }
                        }
                    } else {
                        System.out.println("Unexpected data format in the file.");
                    }
                } catch (EOFException e) {
                    // EOF reached, exit the loop
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IO Exception: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Class not found: " + e.getMessage());
        }
        return students;
    }

    public static List<Student> getStudents() {
        return readStudentsFromFile();
    }

    public static void updateStudent(Student student) {
        List<Student> students = readStudentsFromFile();

        if (students != null) {
            for (int i = 0; i < students.size(); i++) {
                if (students.get(i).getEmail().equals(student.getEmail())) {
                    students.set(i, student); // Replace the existing student with the updated student
                    break; // No need to continue searching
                }
            }

            saveStudentsToFile(students); // Save the updated list of students to the database
        }
    }

    // public static void updateStudent(Student student) {
    // List<Student> students = readStudentsFromFile();

    // if (students != null) {
    // for (int i = 0; i < students.size(); i++) {
    // if (students.get(i).getEmail().equals(student.getEmail())) {
    // students.set(i, student); // Replace the existing student with the updated
    // student
    // saveStudentsToFile(students); // Save the updated list of students to the
    // database
    // return;
    // }
    // }
    // }
    // }

    public static void main(String[] args) {
        Database db = new Database();

        // Check if file exists
        db.checkIfFileExists();

        // Create file if it doesn't exist
        db.createFileIfNotExists();

        // ... any other method calls you want to test
    }

}
