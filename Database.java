import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static final String FILE_NAME = "students.data";

    public static boolean checkFileExists() {
        File file = new File(FILE_NAME);
        return file.exists();
    }

    public static void createFileIfNotExists() {
        if (!checkFileExists()) {
            try {
                File file = new File(FILE_NAME);
                file.createNewFile();
                System.out.println("File 'students.data' created successfully.");
            } catch (IOException e) {
                System.err.println("Error creating the file 'students.data': " + e.getMessage());
            }
        }
    }

    public static void writeObjectsToFile(List<Student> students) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(students);
            System.out.println("Data written to 'students.data' successfully.");
        } catch (IOException e) {
            System.err.println("Error writing data to 'students.data': " + e.getMessage());
        }
    }

    public static List<Student> readObjectsFromFile() {
        List<Student> students = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Error creating the file 'students.data': " + e.getMessage());
            }
        }
        try {
            FileInputStream in = new FileInputStream(file);
            ObjectInputStream objIn=new ObjectInputStream(in);
            Object obj = objIn.readObject();
            if (obj instanceof List) {
                students = (List<Student>) obj;
                System.out.println("Data read from 'students.data' successfully.");
            }
            objIn.close();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error reading data from 'students.data': " + e.getMessage());
        }
        return students;
    }

    public static void clearFile() {
        try {
            PrintWriter writer = new PrintWriter(FILE_NAME);
            writer.close();
            System.out.println("File 'students.data' cleared successfully.");
        } catch (FileNotFoundException e) {
            System.err.println("Error clearing the file 'students.data': " + e.getMessage());
        }
    }

    // Write a single student to the file
    public static void writeStudent(Student student) {
        List<Student> students = readAllStudents();
        students.add(student);
        writeObjectsToFile(students);
        System.out.println("Student added to the database.");
    }

    // Read all students from the file
    public static List<Student> readAllStudents() {
        return readObjectsFromFile();
    }
}


