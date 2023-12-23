package client.GUI.student;

import client.App;
import client.GUI.LoginPanel;
import client.RMIClient;
import java.awt.*;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.sql.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import server.DbStudentPortal;
import server.objects.Course;
import server.objects.Enrolled;

public class StudentDashboard extends JPanel {

  private static DbStudentPortal studentPortalService;

  // Table Population lists
  List<Course> courses;
  List<Enrolled> coursesEnrolled;

  private App parentApp;
  private JTabbedPane tabbedPane;

  Connection con;
  Statement statement;
  int studId;

  // Components for enrolling in courses
  private JTable availableCoursesTable;
  private JTextField courseCodeField;
  private JButton enrollButton;

  // Component for viewing enrolled courses and grades
  private JTable enrolledCoursesTable;

  public StudentDashboard(App app, int studId) throws RemoteException {
    studentPortalService = RMIClient.portalServices;
    this.studId = studId;
    parentApp = app;
    setupUI();
  }

  public void setupUI() throws RemoteException {
    setLayout(new BorderLayout());
    JLabel titleLabel = new JLabel("Student Dashboard");
    titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 15));
    add(titleLabel, BorderLayout.NORTH);

    tabbedPane = new JTabbedPane();
    setupEnrollToCourse();
    setupViewGrades();

    add(tabbedPane, BorderLayout.CENTER);
    add(createLogoutPanel(), BorderLayout.SOUTH);
  }

  private Component createLogoutPanel() {
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JButton logoutButton = createButton(
      "Logout",
      e -> {
        try {
          parentApp.switchPanel(new LoginPanel(parentApp));
        } catch (RemoteException e1) {
          e1.printStackTrace();
        }
      }
    );
    logoutButton.setPreferredSize(new Dimension(80, 25));
    buttonPanel.add(logoutButton);
    return buttonPanel;
  }

  private JButton createButton(String string, ActionListener actionListener) {
    JButton button = new JButton(string);
    button.setFont(new Font("Arial", Font.BOLD, 12));
    button.addActionListener(actionListener);
    return button;
  }

  private void setupViewGrades() throws RemoteException {
    JPanel viewGradesPanel = new JPanel(new BorderLayout());

    DefaultTableModel enrolledCoursesTableModel = new DefaultTableModel();
    enrolledCoursesTableModel.addColumn("Student FName");
    enrolledCoursesTableModel.addColumn("Student LName");
    enrolledCoursesTableModel.addColumn("Course ID");
    enrolledCoursesTableModel.addColumn("Student ID");
    enrolledCoursesTableModel.addColumn("Grade");
    enrolledCoursesTable = new JTable(enrolledCoursesTableModel);
    JScrollPane enrolledCoursesScrollPane = new JScrollPane(
      enrolledCoursesTable
    );
    viewGradesPanel.add(enrolledCoursesScrollPane, BorderLayout.CENTER);

    enrolledCoursesTable.setDefaultEditor(Object.class, null);

    tabbedPane.addTab(
      "View Your Grades",
      null,
      viewGradesPanel,
      "View your grades"
    );

    // Populate enrolled courses and grades
    populateEnrolledCoursesTable();
  }

  private void populateEnrolledCoursesTable() throws RemoteException {
    coursesEnrolled = studentPortalService.retrieveCoursesEnrolled(studId);

    DefaultTableModel studentsEnrolledModel = (DefaultTableModel) enrolledCoursesTable.getModel();

    for (Enrolled courseEnrolled : coursesEnrolled) {
      System.out.println("Enrolled Course: " + courseEnrolled); // Debug print

      studentsEnrolledModel.addRow(
        new Object[] {
          courseEnrolled.getFirstName(),
          courseEnrolled.getLastName(),
          courseEnrolled.getCourseId(),
          courseEnrolled.getStudentId(),
          courseEnrolled.getGrade(),
        }
      );
    }
  }

  private void setupEnrollToCourse() throws RemoteException {
    JPanel enrollToCoursePanel = new JPanel(new BorderLayout());
    DefaultTableModel availableCoursesTableModel = new DefaultTableModel();
    availableCoursesTableModel.addColumn("Course ID");
    availableCoursesTableModel.addColumn("Course Title");
    availableCoursesTableModel.addColumn("Course Code");
    availableCoursesTableModel.addColumn("Course Credit");
    availableCoursesTable = new JTable(availableCoursesTableModel);
    JScrollPane availableCoursesScrollPane = new JScrollPane(
      availableCoursesTable
    );
    enrollToCoursePanel.add(availableCoursesScrollPane, BorderLayout.CENTER);

    availableCoursesTable.setDefaultEditor(Object.class, null);

    JPanel enrollFormPanel = new JPanel(new FlowLayout());
    courseCodeField = new JTextField(20);
    enrollButton = createButton("Enroll", e -> enrollInCourse());
    enrollFormPanel.add(new JLabel("Enter Course Code:"));
    enrollFormPanel.add(courseCodeField);
    enrollFormPanel.add(enrollButton);
    enrollToCoursePanel.add(enrollFormPanel, BorderLayout.SOUTH);

    tabbedPane.addTab(
      "Enroll to Course",
      null,
      enrollToCoursePanel,
      "Enroll in courses"
    );
    // Populate available courses
    populateAvailableCoursesTable();
  }

  private void populateAvailableCoursesTable() throws RemoteException {
    courses = studentPortalService.retrieveCourses();

    DefaultTableModel model = (DefaultTableModel) availableCoursesTable.getModel();

    for (Course course : courses) {
      model.addRow(
        new Object[] {
          course.getCourseId(),
          course.getCourseTitle(),
          course.getCourseCode(),
          course.getCourseCredit(),
        }
      );
    }
  }

  private void enrollInCourse() {
    // TODO: ENROLL IN COURSE IMPLEMENTATION
  }
}
