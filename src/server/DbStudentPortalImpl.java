package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import server.objects.Admin;
import server.objects.Course;
import server.objects.Enroll;
import server.objects.Enrolled;
import server.objects.LoginData;
import server.objects.Student;

public class DbStudentPortalImpl
  extends UnicastRemoteObject
  implements DbStudentPortal {

  public Connection conn = null;
  public Statement statement = null;
  public PreparedStatement prepStatement = null;

  protected DbStudentPortalImpl() throws RemoteException {
    super();
  }

  // Constant queries
  // Constants for table and column names
  private static final String TABLE_ADMIN = "AdminLog";
  private static final String TABLE_STUDENT = "StudentLog";
  private static final String TABLE_COURSE = "CoursesLog";
  private static final String TABLE_ENROLL = "EnrollmentsLog";

  private static final String COLUMN_ADMIN_ID = "adminId";
  private static final String COLUMN_STUDENT_ID = "studentId";
  private static final String COLUMN_COURSE_ID = "courseId";

  private static final String QUERY_SELECT_ADMINS =
    "SELECT * FROM " + TABLE_ADMIN;
  private static final String QUERY_SELECT_STUDENTS =
    "SELECT * FROM " + TABLE_STUDENT;
  private static final String QUERY_SELECT_COURSES =
    "SELECT * FROM " + TABLE_COURSE;

  private static final String QUERY_INSERT_ADMIN =
    "INSERT INTO " +
    TABLE_ADMIN +
    " (column1, column2, ...) VALUES (?, ?, ...)";
  private static final String QUERY_INSERT_STUDENT =
    "INSERT INTO " +
    TABLE_STUDENT +
    " (firstName, lastName, email, password) VALUES (?, ?, ?, ?)";
  private static final String QUERY_INSERT_COURSE =
    "INSERT INTO " +
    TABLE_COURSE +
    " (courseTitle, courseCode, courseCredit) VALUES (?, ?, ?)";

  private static final String QUERY_DELETE_ADMIN =
    "DELETE FROM " + TABLE_ADMIN + " WHERE " + COLUMN_ADMIN_ID + " = ?";
  private static final String QUERY_DELETE_STUDENT =
    "DELETE FROM " + TABLE_STUDENT + " WHERE " + COLUMN_STUDENT_ID + " = ?";
  private static final String QUERY_DELETE_COURSE =
    "DELETE FROM " + TABLE_COURSE + " WHERE " + COLUMN_COURSE_ID + " = ?";

  private static final String QUERY_UPDATE_ADMIN =
    "UPDATE " +
    TABLE_ADMIN +
    " SET column1 = ?, column2 = ... WHERE " +
    COLUMN_ADMIN_ID +
    " = ?";
  private static final String QUERY_UPDATE_STUDENT =
    "UPDATE " +
    TABLE_STUDENT +
    " SET column1 = ?, column2 = ... WHERE " +
    COLUMN_STUDENT_ID +
    " = ?";
  private static final String QUERY_UPDATE_COURSE =
    "UPDATE " +
    TABLE_COURSE +
    " SET column1 = ?, column2 = ... WHERE " +
    COLUMN_COURSE_ID +
    " = ?";

  private static final String QUERY_ENROLL_TO_COURSE =
    "INSERT INTO " +
    TABLE_ENROLL +
    " (StudentId, CourseId, Grade) VALUES (?, ?, ?)";
  private static final String QUERY_ENROLL_UPDATE =
    "UPDATE EnrollmentsLog " +
    "SET Grade = ? " +
    "WHERE StudentId = ? AND CourseId = ?";
  private static final String QUERY_ENROLLED_COURSES_FOR_STUD =
    "SELECT StudentLog.firstName, StudentLog.lastName, StudentLog.studentId, EnrollmentsLog.courseId, EnrollmentsLog.Grade " +
    "FROM StudentLog INNER JOIN EnrollmentsLog ON StudentLog.studentId = EnrollmentsLog.studentId " +
    "WHERE StudentLog.studentId = ?";
  private static final String QUERY_ENROLLED_COURSES =
    "SELECT StudentLog.firstName, StudentLog.lastName, StudentLog.studentId, EnrollmentsLog.courseId, EnrollmentsLog.Grade " +
    "FROM StudentLog INNER JOIN EnrollmentsLog ON StudentLog.studentId = EnrollmentsLog.studentId ";

  private static final String QUERY_GET_STUDENT_ID =
    "SELECT studentId FROM StudentLog WHERE firstName = ? AND lastName = ? AND password = ?";

  @Override
  public List<Admin> retrieveAdmins() throws RemoteException {
    List<Admin> admins = new ArrayList<>();

    try (
      Connection connection = DbConnector.getConnection();
      PreparedStatement preparedStatement = connection.prepareStatement(
        QUERY_SELECT_ADMINS
      );
      ResultSet resultSet = preparedStatement.executeQuery()
    ) {
      while (resultSet.next()) {
        Admin admin = new Admin();
        // Set admin properties based on the columns in the ResultSet
        admin.setAdminId(resultSet.getInt("adminId"));
        admin.setFirstName(resultSet.getString("firstName"));
        admin.setLastName(resultSet.getString("lastName"));
        admin.setEmail(resultSet.getString("email"));
        admin.setPassword(resultSet.getString("password"));

        admins.add(admin);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return admins;
  }

  @Override
  public List<Student> retrieveStudents() throws RemoteException {
    List<Student> students = new ArrayList<>();

    try (
      Connection connection = DbConnector.getConnection();
      PreparedStatement preparedStatement = connection.prepareStatement(
        QUERY_SELECT_STUDENTS
      );
      ResultSet resultSet = preparedStatement.executeQuery()
    ) {
      while (resultSet.next()) {
        Student student = new Student();
        student.setStudentId(resultSet.getInt("studentId"));
        student.setFirstName(resultSet.getString("firstName"));
        student.setLastName(resultSet.getString("lastName"));
        student.setEmail(resultSet.getString("email"));
        student.setPassword(resultSet.getString("password"));

        students.add(student);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return students;
  }

  @Override
  public List<Course> retrieveCourses() throws RemoteException {
    List<Course> courses = new ArrayList<>();

    try (
      Connection connection = DbConnector.getConnection();
      PreparedStatement preparedStatement = connection.prepareStatement(
        QUERY_SELECT_COURSES
      );
      ResultSet resultSet = preparedStatement.executeQuery()
    ) {
      while (resultSet.next()) {
        Course course = new Course();
        course.setCourseId(resultSet.getInt("courseId"));
        course.setCourseTitle(resultSet.getString("courseTitle"));
        course.setCourseCode(resultSet.getString("courseCode"));
        course.setCourseCredit(resultSet.getInt("courseCredit"));

        courses.add(course);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return courses;
  }

  @Override
  public List<Enrolled> retrieveCoursesEnrolled(int studId)
    throws RemoteException {
    List<Enrolled> courses = new ArrayList<>();
    try (
      Connection connection = DbConnector.getConnection();
      PreparedStatement preparedStatement = connection.prepareStatement(
        QUERY_ENROLLED_COURSES_FOR_STUD
      )
    ) {
      preparedStatement.setInt(1, studId);

      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        while (resultSet.next()) {
          Enrolled course = new Enrolled();
          course.setFirstName(resultSet.getString("firstName"));
          course.setLastName(resultSet.getString("lastName"));
          course.setCourseId(resultSet.getInt("courseId"));
          course.setStudentId(resultSet.getInt("studentId"));
          course.setGrade(resultSet.getString("Grade"));

          courses.add(course);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    System.out.println("Retrieved Enrolled courses: " + courses + " " + studId);
    return courses;
  }

  public List<Enrolled> retrieveCoursesEnrolled() throws RemoteException {
    List<Enrolled> courses = new ArrayList<>();
    try (
      Connection connection = DbConnector.getConnection();
      PreparedStatement preparedStatement = connection.prepareStatement(
        QUERY_ENROLLED_COURSES
      )
    ) {
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        while (resultSet.next()) {
          Enrolled course = new Enrolled();
          course.setFirstName(resultSet.getString("firstName"));
          course.setLastName(resultSet.getString("lastName"));
          course.setCourseId(resultSet.getInt("courseId"));
          course.setStudentId(resultSet.getInt("studentId"));
          course.setGrade(resultSet.getString("Grade"));

          courses.add(course);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return courses;
  }

  @Override
  public void createAdmin(Admin admin) throws RemoteException {
    try (
      Connection connection = DbConnector.getConnection();
      PreparedStatement preparedStatement = connection.prepareStatement(
        QUERY_INSERT_ADMIN
      )
    ) {
      preparedStatement.setString(1, admin.getFirstName());
      preparedStatement.setString(2, admin.getLastName());
      preparedStatement.setString(3, admin.getEmail());
      preparedStatement.setString(4, admin.getPassword());

      // Execute the INSERT query
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void createStudent(Student student) throws RemoteException {
    try (
      Connection connection = DbConnector.getConnection();
      PreparedStatement preparedStatement = connection.prepareStatement(
        QUERY_INSERT_STUDENT
      )
    ) {
      preparedStatement.setString(1, student.getFirstName());
      preparedStatement.setString(2, student.getLastName());
      preparedStatement.setString(3, student.getEmail());
      preparedStatement.setString(4, student.getPassword());

      // Execute the INSERT query
      preparedStatement.executeUpdate();
      System.out.println("Created student :)");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void createCourse(Course course) throws RemoteException {
    try (
      Connection connection = DbConnector.getConnection();
      PreparedStatement preparedStatement = connection.prepareStatement(
        QUERY_INSERT_COURSE
      )
    ) {
      preparedStatement.setString(1, course.getCourseTitle());
      preparedStatement.setString(2, course.getCourseCode());
      preparedStatement.setInt(3, course.getCourseCredit());

      // Execute the INSERT query
      preparedStatement.executeUpdate();
      System.out.println("Course Created :)");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void delAdmin(int adminId) throws RemoteException {
    try (
      Connection connection = DbConnector.getConnection();
      PreparedStatement preparedStatement = connection.prepareStatement(
        QUERY_DELETE_ADMIN
      )
    ) {
      preparedStatement.setInt(1, adminId);

      // Execute the DELETE query
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void delStudent(int studentId) throws RemoteException {
    try (
      Connection connection = DbConnector.getConnection();
      PreparedStatement preparedStatement = connection.prepareStatement(
        QUERY_DELETE_STUDENT
      )
    ) {
      preparedStatement.setInt(1, studentId);

      // Execute the DELETE query
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void delCourse(String courseId) throws RemoteException {
    try (
      Connection connection = DbConnector.getConnection();
      PreparedStatement preparedStatement = connection.prepareStatement(
        QUERY_DELETE_COURSE
      )
    ) {
      preparedStatement.setString(1, courseId);

      // Execute the DELETE query
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void updateAdmin(Admin admin) throws RemoteException {
    try (
      Connection connection = DbConnector.getConnection();
      PreparedStatement preparedStatement = connection.prepareStatement(
        QUERY_UPDATE_ADMIN
      )
    ) {
      preparedStatement.setString(1, admin.getFirstName());
      preparedStatement.setString(2, admin.getLastName());
      preparedStatement.setString(3, admin.getEmail());
      preparedStatement.setString(4, admin.getPassword());
      preparedStatement.setInt(5, admin.getAdminId());

      // Execute the UPDATE query
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void updateStudent(Student student) throws RemoteException {
    try (
      Connection connection = DbConnector.getConnection();
      PreparedStatement preparedStatement = connection.prepareStatement(
        QUERY_UPDATE_STUDENT
      )
    ) {
      preparedStatement.setString(1, student.getFirstName());
      preparedStatement.setString(2, student.getLastName());
      preparedStatement.setString(3, student.getEmail());
      preparedStatement.setString(4, student.getPassword());
      preparedStatement.setInt(5, student.getStudentId());

      // Execute the UPDATE query
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void updateCourse(Course course) throws RemoteException {
    try (
      Connection connection = DbConnector.getConnection();
      PreparedStatement preparedStatement = connection.prepareStatement(
        QUERY_UPDATE_COURSE
      )
    ) {
      preparedStatement.setString(1, course.getCourseTitle());
      preparedStatement.setString(2, course.getCourseCode());
      preparedStatement.setInt(3, course.getCourseCredit());
      preparedStatement.setInt(4, course.getCourseId());

      // Execute the UPDATE query
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void enrollToCourse(Enroll enroll) throws RemoteException {
    try (
      Connection connection = DbConnector.getConnection();
      PreparedStatement preparedStatement = connection.prepareStatement(
        QUERY_ENROLL_TO_COURSE
      )
    ) {
      preparedStatement.setInt(1, enroll.getStudentId());
      preparedStatement.setInt(2, enroll.getCourseId());
      preparedStatement.setString(3, enroll.getGrade());

      // Execute the INSERT query
      preparedStatement.executeUpdate();
      System.out.println("Grade created :) ");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void enrollUpdate(Enroll enroll) throws RemoteException {
    try (
      Connection connection = DbConnector.getConnection();
      PreparedStatement preparedStatement = connection.prepareStatement(
        QUERY_ENROLL_UPDATE
      )
    ) {
      preparedStatement.setString(1, enroll.getGrade());
      preparedStatement.setInt(2, enroll.getStudentId());
      preparedStatement.setInt(3, enroll.getCourseId());

      // Execute the INSERT query
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public boolean getLoginStatus(LoginData data) throws RemoteException {
    DbAccess access = new DbAccess(
      data.getAccessType(),
      data.getUserType(),
      data.getUsername(),
      data.getPassword()
    );

    return access.dbAccessor();
  }

  @Override
  public int retrieveStudentId(String fname, String lname, String password) {
    int studentId = -1; // Default value indicating no match found

    try (
      Connection connection = DbConnector.getConnection();
      PreparedStatement preparedStatement = connection.prepareStatement(
        QUERY_GET_STUDENT_ID
      )
    ) {
      preparedStatement.setString(1, fname);
      preparedStatement.setString(2, lname);
      preparedStatement.setString(3, password);

      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next()) {
          studentId = resultSet.getInt("studentId");
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return studentId;
  }
}
