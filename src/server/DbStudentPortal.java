package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import server.objects.Admin;
import server.objects.Course;
import server.objects.Enroll;
import server.objects.Enrolled;
import server.objects.LoginData;
import server.objects.Student;

public interface DbStudentPortal extends Remote {
  List<Admin> retrieveAdmins() throws RemoteException;
  List<Student> retrieveStudents() throws RemoteException;
  List<Course> retrieveCourses() throws RemoteException;
  List<Enrolled> retrieveCoursesEnrolled(int studId) throws RemoteException;
  List<Enrolled> retrieveCoursesEnrolled() throws RemoteException;

  // Creator
  void createAdmin(Admin admin) throws RemoteException;
  void createStudent(Student student) throws RemoteException;
  void createCourse(Course course) throws RemoteException;

  // Delete
  void delAdmin(int adminId) throws RemoteException;
  void delStudent(int studentId) throws RemoteException;
  void delCourse(String courseId) throws RemoteException;

  // update
  void updateAdmin(Admin admin) throws RemoteException;
  void updateStudent(Student student) throws RemoteException;
  void updateCourse(Course course) throws RemoteException;

  // enroll to courses
  void enrollToCourse(Enroll enroll) throws RemoteException;
  void enrollUpdate(Enroll enroll) throws RemoteException;

  // login methods
  boolean getLoginStatus(LoginData data) throws RemoteException;
  int retrieveStudentId(String fname, String lname, String password)
    throws RemoteException;
}
