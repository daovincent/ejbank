package com.ejbank.session.user;

public class UserPayload {
    private final String firstName;
    private final String lastName;

    public UserPayload(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstname() {
        return firstName;
    }

    public String getLastname() {
        return lastName;
    }
}
