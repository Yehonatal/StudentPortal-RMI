package client.GUI;
import java.rmi.RemoteException;

// import client.RMIClient;
// import server.DbStudentPortal;
import javax.swing.*;
import java.awt.*;

public class LoginPanel {
    // private static DbStudentPortal studentPortalService;
    private static JFrame loginPanel;

    public LoginPanel() throws RemoteException{
        // studentPortalService = RMIClient.portalServices;
        initializeGUI();
    }

    private static void initializeGUI(){
        loginPanel = new JFrame("RMI - STUDENT PORTAL");
        loginPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginPanel.setLayout(new GridLayout());
        loginPanel.setSize(850, 350);

        loginPanel.setVisible(true);
    }
    
}
