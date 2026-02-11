package com.uliana.lab4.dto;

public class ProfileUpdateRequest {
    private String firstName;
    private String lastName;

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
}
