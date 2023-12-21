package client.GUI;

import client.RMIClient;
import java.awt.*;
import java.rmi.RemoteException;
import java.util.List;
import javax.swing.*;
import server.DbStudentPortal;
// import server.objects.Course;
import server.objects.Student;

public class LoginPanel {

  private static DbStudentPortal studentPortalService;
  private static JFrame loginPanel;

  public LoginPanel() throws RemoteException {
    studentPortalService = RMIClient.portalServices;

    try {
      List<Student> students = studentPortalService.retrieveStudents();
      System.out.println("Retrieved students:");
      System.out.println(students);
      for (Student student : students) {
        System.out.println(student.toString());
      }
    } catch (RemoteException e) {
      e.printStackTrace();
      JOptionPane.showMessageDialog(
        null,
        "Error retrieving courses: " + e.getMessage(),
        "Error",
        JOptionPane.ERROR_MESSAGE
      );
    }

    initializeGUI();
  }

  private static void initializeGUI() {
    loginPanel = new JFrame("RMI - STUDENT PORTAL");
    loginPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    loginPanel.setLayout(new GridLayout());
    loginPanel.setSize(850, 350);

    loginPanel.setVisible(true);
  }
}
