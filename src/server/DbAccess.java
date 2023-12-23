package server;

import java.sql.*;

public class DbAccess {

  private Connection conn;
  private String accessType;
  private String userType;
  private String firstName;
  private String lastName;
  private String password;

  public DbAccess(
    String accessType,
    String userType,
    String userName,
    String userPsw
  ) {
    this.accessType = accessType.toLowerCase();
    this.userType = userType.toLowerCase();
    String[] names = userName.split(" ");
    if (names.length >= 2) {
      this.firstName = names[0].toLowerCase();
      this.lastName = names[1].toLowerCase();
    } else {
      this.firstName = userName.toLowerCase();
      this.lastName = "";
    }

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
          return selectData("password", "AdminLog");
        }
        break;
      case "student":
        if (accessType.equals("select")) {
          return selectData("password", "StudentLog");
        }
        break;
    }
    return false;
  }

  public boolean selectData(String psw, String table) {
    System.out.println("from select Data");
    System.out.println(firstName);

    ResultSet result;
    try {
      PreparedStatement preparedStatement = conn.prepareStatement(
        String.format(
          "SELECT * FROM %s WHERE firstName = ? AND lastName = ? AND %s = ?",
          table,
          psw
        )
      );
      preparedStatement.setString(1, firstName.toLowerCase());
      preparedStatement.setString(2, lastName.toLowerCase());
      preparedStatement.setString(3, password);

      result = preparedStatement.executeQuery();

      if (result.next()) {
        // If a row is found, return true
        System.out.println("from the select data result found condition");
        return true;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    // If no row is found or an exception occurs, return false
    return false;
  }
}
