package client.GUI;

import client.App;
import client.GUI.admin.AdminDashboard;
import client.GUI.student.StudentDashboard;
import client.RMIClient;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
// import java.util.List;
import javax.swing.*;
import server.DbStudentPortal;
import server.objects.LoginData;

// import server.objects.Course;
// import server.objects.Student;

public class LoginPanel extends JPanel {

  private static DbStudentPortal studentPortalService;
  private static JTextField usernameField;
  private static JPasswordField passwordField;
  private static JCheckBox adminCheckBox;
  private static JCheckBox studentCheckBox;
  private static JButton loginButton;
  private static App parentApp;
  public boolean result;
  static String username;
  static char[] passwordChars;
  static String password;
  static StringBuilder userType;
  static int studID;

  public LoginPanel(App app) throws RemoteException {
    studentPortalService = RMIClient.portalServices;

    // try {
    //   List<Student> students = studentPortalService.retrieveStudents();
    //   System.out.println("Retrieved students:");
    //   System.out.println(students);
    //   for (Student student : students) {
    //     System.out.println(student.toString());
    //   }
    // } catch (RemoteException e) {
    //   e.printStackTrace();
    //   JOptionPane.showMessageDialog(
    //     null,
    //     "Error retrieving courses: " + e.getMessage(),
    //     "Error",
    //     JOptionPane.ERROR_MESSAGE
    //   );
    // }

    initializeGUI(app);
  }

  private void initializeGUI(App app) {
    parentApp = app;

    setLayout(new GridBagLayout());
    GridBagConstraints gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.insets = new Insets(5, 5, 5, 5);

    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    add(new JLabel("Username:"), gridBagConstraints);

    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
    usernameField = new JTextField(20);
    add(usernameField, gridBagConstraints);

    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.gridwidth = 1;
    gridBagConstraints.fill = GridBagConstraints.NONE;
    add(new JLabel("Password:"), gridBagConstraints);

    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
    passwordField = new JPasswordField(20);
    add(passwordField, gridBagConstraints);

    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.gridwidth = 3;
    gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
    adminCheckBox = new JCheckBox("Admin");
    studentCheckBox = new JCheckBox("Student");
    JPanel userTypePanel = new JPanel();
    userTypePanel.add(adminCheckBox);
    userTypePanel.add(studentCheckBox);
    add(userTypePanel, gridBagConstraints);

    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 3;
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
    loginButton = new JButton("Login");
    add(loginButton, gridBagConstraints);

    loginButton.addActionListener(
      new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          username = usernameField.getText();
          passwordChars = passwordField.getPassword();
          password = new String(passwordChars);
          userType = new StringBuilder();

          if (adminCheckBox.isSelected()) {
            userType.append("admin ");
          }

          if (studentCheckBox.isSelected()) {
            userType.append("student ");
          }
          if (userType.length() == 0) {
            JOptionPane.showMessageDialog(
              null,
              "Please select at least one user type."
            );
          } else {
            LoginData data = new LoginData(
              "select",
              userType.toString().trim(),
              username,
              password
            );
            try {
              result = studentPortalService.getLoginStatus(data);
            } catch (RemoteException e1) {
              e1.printStackTrace();
            }

            if (result) {
              JOptionPane.showMessageDialog(null, "Login Successful!");

              // Switch to the appropriate panel based on user type
              if (adminCheckBox.isSelected()) {
                parentApp.switchPanel(new AdminDashboard(parentApp));
                System.out.println("to admin side");
              } else if (studentCheckBox.isSelected()) {
                try {
                  String fname = username.split(" ")[0];
                  String lname = username.split(" ")[1];
                  studID =
                    studentPortalService.retrieveStudentId(
                      fname,
                      lname,
                      password
                    );
                  parentApp.switchPanel(
                    new StudentDashboard(parentApp, studID)
                  );
                } catch (RemoteException e1) {
                  e1.printStackTrace();
                }
                System.out.println("to student side");
              }
            } else {
              JOptionPane.showMessageDialog(
                null,
                "Login Failed. Please check your credentials."
              );
            }
          }
        }
      }
    );
  }
}
