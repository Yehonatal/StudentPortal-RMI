package server.objects;

import java.io.Serializable;

public class Enroll implements Serializable {

  private static final long serialVersionUID = 1L;

  public int studentId;
  public int courseId;
  public String Grade = "A";

  public Enroll() {}

  public Enroll(int ci, int si, String G) {
    this.studentId = si;
    this.courseId = ci;
    this.Grade = G;
  }

  public Enroll(int ci, int si) {
    this.studentId = si;
    this.courseId = ci;
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
