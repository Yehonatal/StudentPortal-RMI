package server.objects;

import java.io.Serializable;

public class Student implements Serializable {

  private static final long serialVersionUID = 1L;
  public int studentId;
  public String firstName;
  public String lastName;
  public String email;
  public String password;

  public Student() {}

  // Used for retrieving the Student's information
  public Student(
    String firstName,
    String lastName,
    String email,
    int studentId
  ) {
    this.studentId = studentId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
  }

  // Used for creating a Student
  public Student(
    String firstName,
    String lastName,
    String email,
    String password
  ) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
  }

  // Used for Updating a Student
  public Student(
    String firstName,
    String lastName,
    String email,
    String password,
    int studentId
  ) {
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

  public String getPassword() {
    return password;
  }

  public void setStudentId(int studentId) {
    this.studentId = studentId;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  //   For development
  @Override
  public String toString() {
    return (
      "Student{" +
      "studentId=" +
      studentId +
      ", firstName='" +
      firstName +
      '\'' +
      ", lastName='" +
      lastName +
      '\'' +
      ", email='" +
      email +
      '\'' +
      ", password='" +
      password +
      '\'' +
      '}'
    );
  }
}
