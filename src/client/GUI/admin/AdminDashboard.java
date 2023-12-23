package client.GUI.admin;

import client.App;
import client.GUI.LoginPanel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AdminDashboard extends JPanel {

  private App parentApp;
  private JTabbedPane tabbedPane;

  Connection con;
  Statement statement;

  // Components for adding students
  private JTextField studentFNameField;
  private JTextField studentLNameField;
  private JTextField studentIdField;
  private JCheckBox studentStatusCheckBox;

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

  public AdminDashboard(App app) {
    parentApp = app;
    setupUI();
  }

  public void setupUI() {
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

  // Creator Functions
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

  private void setupAssignGradesTab() {
    JPanel assignGradesPanel = new JPanel(new BorderLayout());
    tabbedPane.addTab(
      "Assign Grades",
      null,
      assignGradesPanel,
      "Assign Grades to Students"
    );

    DefaultTableModel studentsEnrolledModel = new DefaultTableModel();
    studentsEnrolledModel.addColumn("Student Name");
    studentsEnrolledModel.addColumn("Student ID");
    studentsEnrolledModel.addColumn("Student Status");
    studentsEnrolledModel.addColumn("Course Code");
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

  private void populateStudentsEnrolledTable() {}

  private Component assignGradeFormPanel() {
    JPanel formPanel = new JPanel(new GridBagLayout());

    assignStudentIdField = createTextField(20);
    assignCourseIdField = createTextField(20);
    gradeField = createTextField(5);
    JButton assignGradeButton = createButton(
      "Assign Grade",
      e -> assignGrade()
    );

    addComponentsToPanel(
      formPanel,
      assignStudentIdField,
      "Select Student:",
      0,
      0
    );
    addComponentsToPanel(
      formPanel,
      assignCourseIdField,
      "Select Course:",
      0,
      1
    );
    addComponentsToPanel(formPanel, gradeField, "Enter Grade:", 0, 2);
    addComponentsToPanel(formPanel, assignGradeButton, 1, 3);

    return formPanel;
  }

  private Object assignGrade() {
    return null;
  }

  private void setupCreateCoursesTab() {
    JPanel createCoursePanel = new JPanel(new BorderLayout());
    tabbedPane.addTab(
      "Create Courses",
      null,
      createCoursePanel,
      "Create Courses"
    );

    DefaultTableModel availableCoursesTableModel = new DefaultTableModel();
    availableCoursesTableModel.addColumn("Course ID");
    availableCoursesTableModel.addColumn("Course Name");
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

  private void populateAvailableCoursesTable() {}

  private Component createCourseFormPanel() {
    JPanel formPanel = new JPanel(new GridBagLayout());

    courseNameField = createTextField(20);
    courseCodeField = createTextField(20);
    courseCreditField = createTextField(20);
    JButton createCourseButton = createButton(
      "Create Course",
      e -> createCourse()
    );

    addComponentsToPanel(formPanel, courseNameField, "Course Code:", 0, 0);
    addComponentsToPanel(formPanel, courseCodeField, "Course Title:", 0, 1);
    addComponentsToPanel(formPanel, courseCreditField, "Course Credit:", 0, 2);
    addComponentsToPanel(formPanel, createCourseButton, 0, 3);

    return formPanel;
  }

  private Object createCourse() {
    return null;
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
    studentIdField = createTextField(20);
    studentStatusCheckBox = new JCheckBox("Active");
    JButton addStudentButton = createButton("Add Student", e -> addStudent());

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
    addComponentsToPanel(addStudentPanel, studentIdField, "Student ID:", 0, 2);
    addComponentsToPanel(
      addStudentPanel,
      studentStatusCheckBox,
      "Student Status:",
      0,
      3
    );
    addComponentsToPanel(addStudentPanel, addStudentButton, 1, 4);
  }

  private Object addStudent() {
    return null;
  }
}
