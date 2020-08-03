package com.benneighbour.practicebnb.authServer.common.security;

/*
 * @created 01/08/2020 - 12
 * @project PremKnockout
 * @author  Ben Neighbour
 */
public class LoginObj {

  private String username;

  private String password;

  public LoginObj() {}

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
