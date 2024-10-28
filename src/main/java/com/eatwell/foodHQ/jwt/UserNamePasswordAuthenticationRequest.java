package com.eatwell.foodHQ.jwt;
//models userName and password sent by the user
public class UserNamePasswordAuthenticationRequest {
    private String userName;
    private String password;

    public UserNamePasswordAuthenticationRequest() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
