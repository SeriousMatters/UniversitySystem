package UNIENROLSYSTEM;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Student implements Serializable {
    private static final int MAX_ENROLLED_SUBJECTS = 5;
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private String email;
    private String password;
    private int mark;
    private String grade;
    private List<Subject> Subjects;

    public Student(String name, String email, String password) {
        this.id = generateRandomID();
        this.name = name;
        this.email = email;
        this.password = password;
        this.Subjects = new ArrayList<>();
    }

    // Method to generate a random student ID
    private String generateRandomID() {
        Random rand = new Random();
        int randomInt = rand.nextInt(100000); // Generates a random number between 0 and 99999
        return String.format("%05d", randomInt); // Format as a 5-digit string
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return this.grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean canEnrollMoreSubjects() {
        return Subjects.size() < MAX_ENROLLED_SUBJECTS;
    }

    public void enrollSubject(Subject subject) {
        if (Subjects.size() < MAX_ENROLLED_SUBJECTS) {
            Subjects.add(subject);
            System.out.println("Enrolled in subject: " + subject);
        } else {
            System.out.println("You have already enrolled in the maximum number of subjects.");
        }
    }

    public void dropSubject(Subject subject) {
        Subjects.remove(subject);
    }

    public List<Subject> getEnrolledSubjects() {
        return Subjects;
    }

    public double calculateAverageMark() {
        int totalMark = 0;
        for (Subject subject : Subjects) {
            totalMark += subject.getMark();
        }
        return Subjects.size() > 0 ? totalMark / (double) Subjects.size() : 0;
    }

    public boolean isCoursePassed() {
        return calculateAverageMark() >= 50;
    }

    public void setEnrolledSubjects(List<Subject> enrolledSubjects) {
        this.Subjects = enrolledSubjects;
    }

    // Method to set a grade for a specific subject
    public void setGrade(Subject subject, String grade) {
        for (Subject enrolledSubject : Subjects) {
            if (enrolledSubject.equals(subject)) {
                enrolledSubject.setGrade(grade);
                System.out.println("Grade for subject " + subject.getId() + " has been set to " + grade);
                return;
            }
        }
        System.out.println("Subject not found in enrolled subjects.");
    }

    public String calculateOverallGrade() {
        if (mark >= 85) {
            return "HD";
        } else if (mark >= 75) {
            return "D";
        } else if (mark >= 65) {
            return "C";
        } else if (mark >= 50) {
            return "P";
        } else if (mark >= 0) {
            return "Z";
        } else {
            return "Invalid";
        }
    }
}


// package UNIENROLSYSTEM;

// import java.io.Serializable;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Random;

// public class Student implements Serializable {
//     private static final int MAX_ENROLLED_SUBJECTS = 5;
//     private static final long serialVersionUID = 1L;
//     private String id;
//     private String name;
//     private String email;
//     private String password;
//     private String grade;
//     private List<Subject> Subjects;

//     public Student(String name, String email, String password) {
//         this.id = generateRandomID();
//         this.name = name;
//         this.email = email;
//         this.grade = grade;
//         this.password = password;
//         this.Subjects = new ArrayList<>();
//     }

//     public void setGrade(Subject subject, String grade) {
//         for (Subject enrolledSubject : Subjects) {
//             if (enrolledSubject.equals(subject)) {
//                 enrolledSubject.setGrade(grade);
//                 System.out.println("Grade for subject " + subject.getId() + " has been set to " + grade);
//                 return;
//             }
//         }
//         System.out.println("Subject not found in enrolled subjects.");
//     }
//     // Method to generate a random student ID
//     private String generateRandomID() {
//         Random rand = new Random();
//         int randomInt = rand.nextInt(100000); // Generates a random number between 0 and 99999
//         return String.format("%05d", randomInt); // Format as a 5-digit string
//     }

//     // Getters and Setters for name, email, password
//     // Note: We do not provide a setter for id to ensure it's generated correctly
//     public String getId() {
//         return this.id;
//     }

//     public String getName() {
//         return this.name;
//     }

//     public void setName(String name) {
//         this.name = name;
//     }

//     public String getGrade() {
//         return this.grade;
//     }

//     public void setGrade(String grade) {
//         this.grade = grade;
//     }

//     public String getEmail() {
//         return this.email;
//     }

//     public void setEmail(String email) {
//         this.email = email;
//     }

//     public String getPassword() {
//         return this.password;
//     }

//     public void setPassword(String password) {
//         this.password = password;
//     }

//     // Methods for managing subjects
//     public boolean canEnrollMoreSubjects() {
//         return Subjects.size() < MAX_ENROLLED_SUBJECTS;
//     }

//     public void enrollSubject(Subject subject) {
//         if (Subjects.size() < MAX_ENROLLED_SUBJECTS) {
//             Subjects.add(subject);
//             System.out.println("Enrolled in subject: " + subject);
//         } else {
//             System.out.println("You have already enrolled in the maximum number of subjects.");
//         }
//     }

//     public void dropSubject(Subject subject) {
//         Subjects.remove(subject);
//     }

//     public List<Subject> getEnrolledSubjects() {
//         return Subjects;
//     }

//     public double calculateAverageMark() {
//         int totalMark = 0;
//         for (Subject subject : Subjects) {
//             totalMark += subject.getMark();
//         }
//         return Subjects.size() > 0 ? totalMark / (double) Subjects.size() : 0;
//     }

//     public boolean isCoursePassed() {
//         return calculateAverageMark() >= 50;
//     }

//     public void setEnrolledSubjects(List<Subject> enrolledSubjects) {
//         this.Subjects = enrolledSubjects;
//     }

// }
