package server.objects;

public class Student {
    public int studentId;
    public String firstName;
    public String lastName;
    public String email;
    public String password;

    public Student() {

    }

    // Used for retrieving the Student's information
    public Student(String firstName, String lastName, String email, int studentId) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    // Used for creating a Student
    public Student(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    // Used for Updating a Student
    public Student(String firstName, String lastName, String email, String password, int studentId) {
        this.studentId = studentId; 
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }


    public int getStudentId() {
        return studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }
}
