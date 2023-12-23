package client.GUI.admin;

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
import server.objects.Enroll;
import server.objects.Enrolled;
import server.objects.Student;

public class AdminDashboard extends JPanel {

  private static DbStudentPortal studentPortalService;

  private App parentApp;
  private JTabbedPane tabbedPane;

  Connection con;
  Statement statement;

  // Components for adding students
  private JTextField studentFNameField;
  private JTextField studentLNameField;
  private JTextField studentEmailField;
  private JTextField studentPswField;

  // Components for creating courses
  private JTable availableCoursesTable;
  private JTextField courseNameField;
  private JTextField courseCodeField;
  private JTextField courseCreditField;

  // Components for assigning grades
  private JTable studentsEnrolledTable;
  private JTextField assignStudentIdField;
  private JTextField assignCourseIdField;
  private JTextField gradeField;

  List<Course> courses;
  List<Enrolled> coursesEnrolled;

  public AdminDashboard(App app) throws RemoteException {
    studentPortalService = RMIClient.portalServices;
    parentApp = app;
    setupUI();
  }

  public void setupUI() throws RemoteException {
    setLayout(new BorderLayout());
    JLabel titleLabel = new JLabel("Admin Dashboard");
    titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 15));
    add(titleLabel, BorderLayout.NORTH);

    tabbedPane = new JTabbedPane();
    setupAddStudentTab();
    setupCreateCoursesTab();
    setupAssignGradesTab();

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

  private JTextField createTextField(int columns) {
    JTextField textField = new JTextField(columns);
    textField.setFont(new Font("Arial", Font.PLAIN, 12));
    return textField;
  }

  private void addComponentsToPanel(
    JPanel panel,
    Component component,
    int gridx,
    int gridy
  ) {
    GridBagConstraints gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.insets = new Insets(5, 5, 5, 5);
    gridBagConstraints.gridx = gridx;
    gridBagConstraints.gridy = gridy;
    gridBagConstraints.gridwidth = 1;

    if (component instanceof JButton) {
      panel.add((JButton) component, gridBagConstraints);
    }
  }

  private void addComponentsToPanel(
    JPanel panel,
    Component component,
    String labelText,
    int gridx,
    int gridy
  ) {
    GridBagConstraints labelConstraints = new GridBagConstraints();
    labelConstraints.insets = new Insets(5, 5, 5, 5);
    labelConstraints.gridx = gridx;
    labelConstraints.gridy = gridy;
    labelConstraints.anchor = GridBagConstraints.WEST;

    GridBagConstraints componentConstraints = new GridBagConstraints();
    componentConstraints.insets = new Insets(5, 5, 5, 5);
    componentConstraints.gridx = gridx + 1;
    componentConstraints.gridy = gridy;
    componentConstraints.fill = GridBagConstraints.HORIZONTAL;
    componentConstraints.anchor = GridBagConstraints.WEST;

    if (component instanceof JScrollPane) {
      panel.add(component, componentConstraints);
    } else if (
      component instanceof JTextField || component instanceof JCheckBox
    ) {
      panel.add(new JLabel(labelText), labelConstraints);
      panel.add(component, componentConstraints);
    } else if (component instanceof JButton) {
      componentConstraints.gridwidth = 2;
      panel.add(component, componentConstraints);
    }
  }

  private void setupAssignGradesTab() throws RemoteException {
    JPanel assignGradesPanel = new JPanel(new BorderLayout());
    tabbedPane.addTab(
      "Assign Grades",
      null,
      assignGradesPanel,
      "Assign Grades to Students"
    );

    DefaultTableModel studentsEnrolledModel = new DefaultTableModel();
    studentsEnrolledModel.addColumn("Student fName");
    studentsEnrolledModel.addColumn("Student LName");
    studentsEnrolledModel.addColumn("Course Id");
    studentsEnrolledModel.addColumn("Student Id");
    studentsEnrolledModel.addColumn("Grade");
    studentsEnrolledTable = new JTable(studentsEnrolledModel);
    JScrollPane enrolledTableScrollPane = new JScrollPane(
      studentsEnrolledTable
    );

    studentsEnrolledTable.setDefaultEditor(Object.class, null);

    // Populate enrolled students
    populateStudentsEnrolledTable();

    JPanel tablePanel = new JPanel(new BorderLayout());
    tablePanel.add(enrolledTableScrollPane, BorderLayout.CENTER);

    JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    splitPane.setResizeWeight(1.0);

    splitPane.setTopComponent(tablePanel);
    splitPane.setBottomComponent(assignGradeFormPanel());
    assignGradesPanel.add(splitPane, BorderLayout.CENTER);
  }

  private void populateStudentsEnrolledTable() throws RemoteException {
    coursesEnrolled = studentPortalService.retrieveCoursesEnrolled();

    DefaultTableModel studentsEnrolledModel = (DefaultTableModel) studentsEnrolledTable.getModel();
    // Clear existing rows from the table
    studentsEnrolledModel.setRowCount(0);
    for (Enrolled courseEnrolled : coursesEnrolled) {
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

  private Component assignGradeFormPanel() {
    JPanel formPanel = new JPanel(new GridBagLayout());

    assignStudentIdField = createTextField(20);
    assignCourseIdField = createTextField(20);
    gradeField = createTextField(5);
    JButton assignGradeButton = createButton(
      "Assign Grade",
      e -> {
        try {
          assignGrade();
        } catch (RemoteException e1) {
          e1.printStackTrace();
        }
      }
    );

    addComponentsToPanel(
      formPanel,
      assignStudentIdField,
      "Select Course Id:",
      0,
      0
    );
    addComponentsToPanel(
      formPanel,
      assignCourseIdField,
      "Select Student Id:",
      0,
      1
    );
    addComponentsToPanel(formPanel, gradeField, "Enter Grade:", 0, 2);
    addComponentsToPanel(formPanel, assignGradeButton, 1, 3);

    return formPanel;
  }

  private void assignGrade() throws RemoteException {
    int studentId = Integer.parseInt(assignStudentIdField.getText());
    int courseId = Integer.parseInt(assignCourseIdField.getText());
    String grade = gradeField.getText();

    Enroll enroll = new Enroll(studentId, courseId, grade);

    studentPortalService.enrollUpdate(enroll);
    clearFormFields(assignStudentIdField, assignCourseIdField, gradeField);
    populateStudentsEnrolledTable();
  }

  private void setupCreateCoursesTab() throws RemoteException {
    JPanel createCoursePanel = new JPanel(new BorderLayout());
    tabbedPane.addTab(
      "Create Courses",
      null,
      createCoursePanel,
      "Create Courses"
    );

    DefaultTableModel availableCoursesTableModel = new DefaultTableModel();
    availableCoursesTableModel.addColumn("Course ID");
    availableCoursesTableModel.addColumn("Course Title");
    availableCoursesTableModel.addColumn("Course Code");
    availableCoursesTableModel.addColumn("Credit");
    availableCoursesTable = new JTable(availableCoursesTableModel);
    JScrollPane availableCoursesScrollPane = new JScrollPane(
      availableCoursesTable
    );

    availableCoursesTable.setDefaultEditor(Object.class, null);

    // Populate available courses
    populateAvailableCoursesTable();

    JPanel tablePanel = new JPanel(new BorderLayout());
    tablePanel.add(availableCoursesScrollPane, BorderLayout.CENTER);

    JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    splitPane.setResizeWeight(1.0);

    splitPane.setTopComponent(tablePanel);
    splitPane.setBottomComponent(createCourseFormPanel());
    createCoursePanel.add(splitPane, BorderLayout.CENTER);
  }

  private void populateAvailableCoursesTable() throws RemoteException {
    courses = studentPortalService.retrieveCourses();

    DefaultTableModel model = (DefaultTableModel) availableCoursesTable.getModel();
    // Clear existing rows from the table
    model.setRowCount(0);

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

  private Component createCourseFormPanel() {
    JPanel formPanel = new JPanel(new GridBagLayout());

    courseNameField = createTextField(20);
    courseCodeField = createTextField(20);
    courseCreditField = createTextField(20);
    JButton createCourseButton = createButton(
      "Create Course",
      e -> {
        try {
          createCourse();
        } catch (RemoteException e1) {
          e1.printStackTrace();
        }
      }
    );

    addComponentsToPanel(formPanel, courseCodeField, "Course Code:", 0, 0);
    addComponentsToPanel(formPanel, courseNameField, "Course Title:", 0, 1);
    addComponentsToPanel(formPanel, courseCreditField, "Course Credit:", 0, 2);
    addComponentsToPanel(formPanel, createCourseButton, 0, 3);

    return formPanel;
  }

  private void createCourse() throws RemoteException {
    String courseCode = courseCodeField.getText();
    String courseName = courseNameField.getText();
    int courseCredit = Integer.parseInt(courseCreditField.getText());

    Course course = new Course(courseName, courseCode, courseCredit);

    studentPortalService.createCourse(course);
    clearFormFields(courseCodeField, courseNameField, courseCreditField);
    populateAvailableCoursesTable();
  }

  private void setupAddStudentTab() {
    JPanel addStudentPanel = new JPanel(new GridBagLayout());
    tabbedPane.addTab(
      "Add Students",
      null,
      addStudentPanel,
      "Add Students to Database"
    );

    studentFNameField = createTextField(20);
    studentLNameField = createTextField(20);
    studentEmailField = createTextField(20);
    studentPswField = createTextField(20);
    JButton addStudentButton = createButton(
      "Add Student",
      e -> {
        try {
          addStudent();
        } catch (RemoteException e1) {
          e1.printStackTrace();
        }
      }
    );

    addComponentsToPanel(
      addStudentPanel,
      studentFNameField,
      "First Name:",
      0,
      0
    );
    addComponentsToPanel(
      addStudentPanel,
      studentLNameField,
      "Last Name:",
      0,
      1
    );
    addComponentsToPanel(
      addStudentPanel,
      studentEmailField,
      "Student Email:",
      0,
      2
    );
    addComponentsToPanel(
      addStudentPanel,
      studentPswField,
      "Student Password:",
      0,
      3
    );

    addComponentsToPanel(addStudentPanel, addStudentButton, 1, 4);
  }

  private void addStudent() throws RemoteException {
    String studentFName = studentFNameField.getText();
    String studentLName = studentLNameField.getText();
    String studentEmail = studentEmailField.getText();
    String studentPassword = studentPswField.getText();

    Student student = new Student(
      studentFName,
      studentLName,
      studentEmail,
      studentPassword
    );

    studentPortalService.createStudent(student);
    clearFormFields(
      studentFNameField,
      studentLNameField,
      studentEmailField,
      studentPswField
    );
  }

  private void clearFormFields(Component... components) {
    for (Component component : components) {
      if (component instanceof JTextField) {
        ((JTextField) component).setText("");
      } else if (component instanceof JCheckBox) {
        ((JCheckBox) component).setSelected(false);
      }
    }
  }
}
