package com.ejbank.api.test;

public class PeoplePayload {
    
    private final String firstname;
    private final String lastname;
    private final Integer age;

    public PeoplePayload(String firstname, String lastname, Integer age) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public Integer getAge() {
        return age;
    }
}
