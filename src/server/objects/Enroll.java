package server.objects;

public class Enroll {

  public int courseId;
  public int studentId;
  public String Grade = "A";

  public Enroll() {}

  public Enroll(int ci, int si, String G) {
    this.courseId = ci;
    this.studentId = si;
    this.Grade = G;
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
