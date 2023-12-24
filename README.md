# Student Portal - RMI Desktop Application
[Github repo link](https://github.com/Yehonatal/StudentPortal-RMI)

The Student Portal RMI Desktop Application is a comprehensive Java-based system that utilizes Remote Method Invocation (RMI) to create an interactive and efficient platform for both administrators and students. This application streamlines various academic tasks, such as managing student information, creating courses, assigning grades, and providing students with tools to view their academic progress.

## Features

- **Administrator Dashboard:**
  - Add new students to the system.
  - Create and manage courses with relevant details.
  - Assign grades to students for specific courses.
  - User-friendly interface for efficient administration.

- **Student Dashboard:**
  - View enrolled courses and grades.
  - Easily enroll in new courses.
  - Intuitive and accessible interface for students.

## Getting Started

To get started with the Student Portal RMI Desktop Application, follow these steps:

1. Clone the repository to your local machine or copy the file to you local machine
2. Set up the necessary dependencies by placing the required JAR files in the `lib` folder.
3. Navigate to the `src/server` directory and run the `RMIServer` then `src/client` and run the `RMIClient`.
4. The login panel will appear, allowing you to enter your credentials.

## Usage

### Administrator

1. Log in as an administrator using valid credentials.
2. Navigate through the tabs (`Add Students`, `Create Courses`, `Assign Grades`) to perform administrative tasks.
3. Enter the required information in the text fields.
4. Click the corresponding buttons (`Add Student`, `Create Course`, `Assign Grade`) to submit the information.
5. Logout using the "Logout" button at the bottom of the interface.

### Student

1. Log in as a student using valid credentials.
2. Navigate through the tabs (`View Your Grades`, `Enroll to Course`) to perform tasks.
3. Enter the required information in the text fields.
4. Click the corresponding buttons (`Enroll`) to submit the information.
5. Logout using the "Logout" button at the bottom of the interface.

# Database Schema

The Student Portal RMI Desktop Application uses a relational database to store key information about administrators, students, courses, and enrollments. Below is the schema for the database tables along with dummy data for initial testing.

The SQL file to create the database is in the `DatabaseQuery.sql` :
[SQL to create the database with the initial dummy data](./DatabaseQuery.sql)

## Folder Structure

```
RMI_STUDENT PORTAL/
|-- lib   // Add the dep files to this folder
|-- bin
|-- src/
|   |-- server/
|   |   |-- DbStudentPortalImpl.java
|   |   |-- DbStudentPortal.java
|   |   |-- DbConnector.java
|   |   |-- DbAccess.java
|   |   |-- RMIServer.java
|   |   |
|   |   |-- object/
|   |       |-- Admin.java
|   |       |-- Course.java
|   |       |-- Student.java
|   |       |-- Enroll.java
|   |       |-- Enrolled.java
|   |       |-- LoginData.java
|   |
|   |-- client/
|   |   |-- RMIClient.java
|   |   |-- App.java
|   |   |
|   |   |-- GUI/
|   |   |   |-- admin/
|   |   |   |   |-- AdminDashboard.java       // Admin main dashboard
|   |   |   |
|   |   |   |-- student/
|   |   |   |   |-- StudentDashboard.java     // Student main dashboard
|   |   |   |
|   |   |   |-- LoginPanel.java
|   |
|   |-- .env
|
|-- build/
|   |-- (compiled .class files)
|
|-- README.md
```

## Dependency Management

- [mysql connector to java]() jar
- protobuf java jar
- [dotenv](https://jar-download.com/artifact-search/java-dotenv) jar
  - annotations jar
  - kotlin stdlib jar
  - kotlin stdlib common jar

Copy these JAR files into the `lib` folder for proper dependency management.

`LoginPanel` class is responsible for handling the login functionality. It interacts with the user through a simple GUI and communicates with the server-side using RMI to validate the user's credentials.

# LoginPanel

The `LoginPanel` class provides a graphical user interface (GUI) for user authentication in the Student Portal RMI Desktop Application. Users can enter their credentials, including username, password, and user type (admin or student). The class communicates with the server-side using RMI to validate the user's credentials and switches to the appropriate dashboard based on the user type.

## Class Structure

- **Fields:**
  - `usernameField`: JTextField for entering the username.
  - `passwordField`: JPasswordField for entering the password.
  - `adminCheckBox`: JCheckBox for selecting the admin user type.
  - `studentCheckBox`: JCheckBox for selecting the student user type.
  - `loginButton`: JButton for triggering the login process.
  - `parentApp`: Reference to the main application class (`App`).
  - `result`: Boolean variable indicating the login result.
  - `username`: String to store the entered username.
  - `passwordChars`: Character array to store the entered password.
  - `password`: String representation of the entered password.
  - `userType`: StringBuilder to store the selected user types.
  - `studID`: Integer to store the student ID.

- **Methods:**
  - `initializeGUI(App app)`: Initializes the graphical user interface.
  - `actionPerformed(ActionEvent e)`: Implements the action to be performed when the login button is clicked.
  
## Usage

1. Enter your username in the "Username" field.
2. Enter your password in the "Password" field.
3. Select the appropriate user type(s) using the checkboxes.
4. Click the "Login" button to initiate the login process.

If the login is successful, the application switches to the corresponding dashboard based on the selected user type.

## Class Interactions

The `LoginPanel` class interacts with the server-side (`DbStudentPortal`) using RMI to validate user credentials and obtain the login result. Upon successful login, it switches to the appropriate dashboard (`AdminDashboard` or `StudentDashboard`) using the `App` class.

# AdminDashboard

The `AdminDashboard` class provides an administrative interface for managing students, courses, and grades in the Student Portal RMI Desktop Application. It consists of multiple tabs, each dedicated to a specific administrative task such as adding students, creating courses, and assigning grades.

## Class Structure

- **Fields:**
  - `studentPortalService`: Reference to the RMI service for communication with the server.
  - `parentApp`: Reference to the main application class (`App`).
  - `tabbedPane`: JTabbedPane for organizing different administrative tasks.
  - `con`, `statement`: Database connection and statement for executing SQL queries.
  - `studentFNameField`, `studentLNameField`, `studentEmailField`, `studentPswField`: TextFields for entering student information.
  - `availableCoursesTable`: JTable for displaying available courses.
  - `courseNameField`, `courseCodeField`, `courseCreditField`: TextFields for entering course information.
  - `studentsEnrolledTable`, `assignStudentIdField`, `assignCourseIdField`, `gradeField`: Components for assigning grades.

- **Methods:**
  - `setupUI()`: Initializes the graphical user interface with tabs for different administrative tasks.
  - `createLogoutPanel()`: Creates a panel with a logout button.
  - `createButton(String string, ActionListener actionListener)`: Creates a JButton with a specified label and action listener.
  - `createTextField(int columns)`: Creates a JTextField with a specified number of columns.
  - `addComponentsToPanel(JPanel panel, Component component, int gridx, int gridy)`: Adds components to a panel at specified grid positions.
  - `addComponentsToPanel(JPanel panel, Component component, String labelText, int gridx, int gridy)`: Adds components with labels to a panel at specified grid positions.
  - `setupAssignGradesTab()`: Sets up the tab for assigning grades to students.
  - `populateStudentsEnrolledTable()`: Populates the table with information about enrolled students.
  - `assignGradeFormPanel()`: Creates a panel for entering information to assign grades.
  - `assignGrade()`: Assigns a grade to a student for a specific course.
  - `setupCreateCoursesTab()`: Sets up the tab for creating courses.
  - `populateAvailableCoursesTable()`: Populates the table with available courses.
  - `createCourseFormPanel()`: Creates a panel for entering information to create a new course.
  - `createCourse()`: Creates a new course with the entered information.
  - `setupAddStudentTab()`: Sets up the tab for adding students.
  - `addStudent()`: Adds a new student with the entered information.
  - `clearFormFields(Component... components)`: Clears the text fields and checkboxes in the specified components.

## Usage

1. Navigate through the different tabs (`Add Students`, `Create Courses`, `Assign Grades`) to perform administrative tasks.
2. Enter the required information in the text fields.
3. Click the corresponding buttons (`Add Student`, `Create Course`, `Assign Grade`) to submit the information.
4. Logout using the "Logout" button at the bottom of the interface.

# StudentDashboard

The `StudentDashboard` class provides a graphical user interface (GUI) for the student portal application. It allows students to view their grades, enroll in courses, and perform various tasks related to their academic activities.

## Class Structure

- **Fields:**
  - `studentPortalService`: Reference to the RMI service for communication with the server.
  - `parentApp`: Reference to the main application class (`App`).
  - `tabbedPane`: JTabbedPane for organizing different tasks.
  - `con`, `statement`: Database connection and statement for executing SQL queries.
  - `studId`: Student ID.
  - `courses`, `coursesEnrolled`: Lists for storing course and enrollment information.
  - `availableCoursesTable`: JTable for displaying available courses.
  - `enrolledCoursesTable`: JTable for displaying enrolled courses and grades.
  - `courseCodeField`: JTextField for entering course code.
  - `enrollButton`: JButton for enrolling in courses.

- **Methods:**
  - `setupUI()`: Initializes the graphical user interface with tabs for different tasks.
  - `createLogoutPanel()`: Creates a panel with a logout button.
  - `createButton(String string, ActionListener actionListener)`: Creates a JButton with a specified label and action listener.
  - `setupViewGrades()`: Sets up the tab for viewing grades.
  - `populateEnrolledCoursesTable()`: Populates the table with enrolled courses and grades.
  - `setupEnrollToCourse()`: Sets up the tab for enrolling in courses.
  - `populateAvailableCoursesTable()`: Populates the table with available courses.
  - `enrollInCourse()`: Enrolls the student in a selected course.
  - `clearFormFields(Component... components)`: Clears the text fields and checkboxes in the specified components.

## Usage

1. Navigate through the different tabs (`View Your Grades`, `Enroll to Course`) to perform tasks.
2. Enter the required information in the text fields.
3. Click the corresponding buttons (`Enroll`) to submit the information.
4. Logout using the "Logout" button at the bottom of the interface.

## Dependencies for every component

- `App` class: Main application class.
- `RMIClient` class: RMI client for communication with the server.
- `DbStudentPortal` interface: Defines RMI methods for the student portal.
