package server.objects;

import java.io.Serializable;

public class Course implements Serializable {

  private static final long serialVersionUID = 1L;

  public int courseId;
  public String courseTitle;
  public String courseCode;
  public int courseCredit;

  public Course() {}

  // Used for retrieving || updating || creating the Course
  public Course(
    int courseId,
    String courseTitle,
    String courseCode,
    int courseCredit
  ) {
    this.courseId = courseId;
    this.courseTitle = courseTitle;
    this.courseCode = courseCode;
    this.courseCredit = courseCredit;
  }

  public Course(String courseTitle, String courseCode, int courseCredit) {
    this.courseTitle = courseTitle;
    this.courseCode = courseCode;
    this.courseCredit = courseCredit;
  }

  public int getCourseId() {
    return courseId;
  }

  public String getCourseTitle() {
    return courseTitle;
  }

  public String getCourseCode() {
    return courseCode;
  }

  public int getCourseCredit() {
    return courseCredit;
  }

  public void setCourseId(int courseId) {
    this.courseId = courseId;
  }

  public void setCourseTitle(String string) {
    this.courseTitle = string;
  }

  public void setCourseCode(String string) {
    this.courseCode = string;
  }

  public void setCourseCredit(int credit) {
    this.courseCredit = credit;
  }

  //   For development
  @Override
  public String toString() {
    return (
      "Course{" +
      "courseId=" +
      courseId +
      ", courseTitle='" +
      courseTitle +
      '\'' +
      ", courseCode='" +
      courseCode +
      '\'' +
      ", courseCredit=" +
      courseCredit +
      '}'
    );
  }
}
