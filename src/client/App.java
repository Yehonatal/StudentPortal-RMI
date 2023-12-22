package client;

import client.GUI.LoginPanel;
import java.awt.*;
import java.rmi.RemoteException;
import javax.swing.*;

public class App extends JFrame {

  private JPanel currentPanel;

  public App() throws RemoteException {
    setTitle("RMI - STUDENT PORTAL");
    setSize(950, 550);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    // Initially, show the login panel
    currentPanel = new LoginPanel(this);
    add(currentPanel, BorderLayout.CENTER);

    setVisible(true);
  }

  public void switchPanel(JPanel newPanel) {
    remove(currentPanel);
    currentPanel = newPanel;
    add(currentPanel, BorderLayout.CENTER);
    revalidate();
    repaint();
  }
}
