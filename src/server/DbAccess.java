package server;

import java.sql.*;

public class DbAccess {

  private Connection conn;
  private String accessType;
  private String userType;
  private String userName;
  private String password;

  public DbAccess(
    String accessType,
    String userType,
    String userName,
    String userPsw
  ) {
    this.accessType = accessType.toLowerCase();
    this.userType = userType.toLowerCase();
    this.userName = userName.toLowerCase();
    this.password = userPsw.toLowerCase();
    try {
      conn = DbConnector.getConnection();
    } catch (SQLException err) {
      err.printStackTrace();
    }
  }

  public boolean dbAccessor() {
    System.out.println("from dbAccessor");
    switch (userType) {
      case "admin":
        if (accessType.equals("select")) {
          return selectData("adminUserName", "adminUserPsw", "AdminLog");
        }
        break;
      case "student":
        if (accessType.equals("select")) {
          return selectData("studentName", "studentId", "StudentLog");
        }
        break;
    }
    return false;
  }

  public boolean selectData(String user, String psw, String table) {
    System.out.println("from select Data");
    System.out.println(userName + " " + password);

    ResultSet result;
    try {
      PreparedStatement preparedStatement = conn.prepareStatement(
        String.format(
          "SELECT * FROM %s WHERE %s = ? AND %s = ?",
          table,
          user,
          psw
        )
      );
      preparedStatement.setString(1, password);
      preparedStatement.setString(2, userName);
      result = preparedStatement.executeQuery();
      if (result.next()) {
        System.out.println("from the select data result found condition");
        return true;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }
}
