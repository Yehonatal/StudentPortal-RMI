package client;
import java.rmi.Naming;
import javax.swing.SwingUtilities;

import client.GUI.LoginPanel;
import io.github.cdimascio.dotenv.Dotenv;
import server.DbStudentPortal;

public class RMIClient {
    public static Dotenv dotenv = Dotenv.load();
    public static String port = dotenv.get("PORT");
    static int PORT = Integer.parseInt(port);
    public static DbStudentPortal portalServices;


    public static void main(String[] args){
        try {
            portalServices = (DbStudentPortal)Naming.lookup("rmi://localhost:" + PORT + "/PORTAL");
            startGUI();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void startGUI() {
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                try {
                    new LoginPanel();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}