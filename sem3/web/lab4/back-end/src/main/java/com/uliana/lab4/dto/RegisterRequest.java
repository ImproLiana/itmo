package com.uliana.lab4.dto;

public class RegisterRequest {
    private String login;
    private String password;
    private String firstName;
    private String lastName;

    public String getLogin() { return login; }
    public String getPassword() { return password; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }

    public void setLogin(String login) { this.login = login; }
    public void setPassword(String password) { this.password = password; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
}
