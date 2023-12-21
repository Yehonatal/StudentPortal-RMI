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
  private static final String TABLE_ADMIN = "Admin";
  private static final String TABLE_STUDENT = "Student";
  private static final String TABLE_COURSE = "Course";

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
    " (column1, column2, ...) VALUES (?, ?, ...)";
  private static final String QUERY_INSERT_COURSE =
    "INSERT INTO " +
    TABLE_COURSE +
    " (column1, column2, ...) VALUES (?, ?, ...)";

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
    System.out.println("Retrieved courses: " + courses);
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
}
