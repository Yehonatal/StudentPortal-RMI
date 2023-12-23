package server.objects;

import java.io.Serializable;

public class Enrolled implements Serializable {

  private static final long serialVersionUID = 1L;
  public int courseId;
  public int studentId;
  public String Grade = "A";
  public String firstName;
  public String lastName;

  public Enrolled() {}

  public Enrolled(String firstName, String lastName, int ci, int si, String G) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.courseId = ci;
    this.studentId = si;
    this.Grade = G;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public int getCourseId() {
    return courseId;
  }

  public int getStudentId() {
    return studentId;
  }

  public String getGrade() {
    return Grade;
  }

  public void setCourseId(int ci) {
    this.courseId = ci;
  }

  public void setStudentId(int si) {
    this.studentId = si;
  }

  public void setGrade(String G) {
    this.Grade = G;
  }
}
