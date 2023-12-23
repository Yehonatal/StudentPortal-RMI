package client.GUI.student;

import client.App;
import client.GUI.LoginPanel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class StudentDashboard extends JPanel {

  private App parentApp;
  private JTabbedPane tabbedPane;

  Connection con;
  Statement statement;
  String studId;

  // Components for enrolling in courses
  private JTable availableCoursesTable;
  private JTextField courseCodeField;
  private JButton enrollButton;

  // Component for viewing enrolled courses and grades
  private JTable enrolledCoursesTable;

  public StudentDashboard(App app, String psw) {
    this.studId = psw;
    parentApp = app;
    setupUI();
  }

  public void setupUI() {
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

  private void setupViewGrades() {
    JPanel viewGradesPanel = new JPanel(new BorderLayout());

    // Create a table to display enrolled courses and grades
    DefaultTableModel enrolledCoursesTableModel = new DefaultTableModel();
    enrolledCoursesTableModel.addColumn("Student Name");
    enrolledCoursesTableModel.addColumn("Student ID");
    enrolledCoursesTableModel.addColumn("Student Status");
    enrolledCoursesTableModel.addColumn("Course Code");
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

  private void populateEnrolledCoursesTable() {}

  private void setupEnrollToCourse() {
    JPanel enrollToCoursePanel = new JPanel(new BorderLayout());
    DefaultTableModel availableCoursesTableModel = new DefaultTableModel();
    availableCoursesTableModel.addColumn("Course ID");
    availableCoursesTableModel.addColumn("Course Name");
    availableCoursesTableModel.addColumn("Credit");
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

  private void populateAvailableCoursesTable() {}

  private Object enrollInCourse() {
    return null;
  }
}
