package server.objects;

public class Course {
    public int courseId;
    public String courseTitle;
    public String courseCode;
    public int courseCredit;


    public Course(){}
    
    // Used for retrieving || updating || creating the Course 
    public Course(int courseId, String courseTitle, String courseCode, int courseCredit){
        this.courseId = courseId;
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
}
