package client.GUI.admin;

import client.App;
import client.GUI.LoginPanel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.sql.*;
import javax.swing.*;

public class AdminDashboard extends JPanel {

  private App parentApp;
  private JTabbedPane tabbedPane;

  Connection con;
  Statement statement;

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

  private JButton createButton(String string, ActionListener actionListener) {
    JButton button = new JButton(string);
    button.setFont(new Font("Arial", Font.BOLD, 12));
    button.addActionListener(actionListener);
    return button;
  }

  private void setupAssignGradesTab() {}

  private void setupCreateCoursesTab() {}

  private void setupAddStudentTab() {}
}
