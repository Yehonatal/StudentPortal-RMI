package server.objects;

import java.io.Serializable;

public class LoginData implements Serializable {

  private static final long serialVersionUID = 1L;
  String accessType;
  String userType;
  String username;
  String password;

  public LoginData(String at, String ut, String un, String psw) {
    this.accessType = at;
    this.userType = ut;
    this.username = un;
    this.password = psw;
  }

  public void setAccessType(String at) {
    this.accessType = at;
  }

  public void setUserTYpe(String ut) {
    this.userType = ut;
  }

  public void setUsername(String un) {
    this.username = un;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getAccessType() {
    return this.accessType;
  }

  public String getUserType() {
    return this.userType;
  }

  public String getPassword() {
    return this.password;
  }

  public String getUsername() {
    return this.username;
  }
}
