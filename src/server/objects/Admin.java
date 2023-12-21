package server.objects;

import java.io.Serializable;

public class Admin implements Serializable {

  private static final long serialVersionUID = 1L;
  private int adminId;
  private String firstName;
  private String lastName;
  private String email;
  private String password;

  public Admin() {}

  // Used for retrieving the Admin's information
  public Admin(String firstName, String lastName, String email, int adminId) {
    this.adminId = adminId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
  }

  // Used for creating an admin
  public Admin(
    String firstName,
    String lastName,
    String email,
    String password
  ) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
  }

  // Used for updating an admin
  public Admin(
    int adminId,
    String firstName,
    String lastName,
    String email,
    String password
  ) {
    this.adminId = adminId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
  }

  public int getAdminId() {
    return adminId;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public void setAdminId(int adminId) {
    this.adminId = adminId;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
