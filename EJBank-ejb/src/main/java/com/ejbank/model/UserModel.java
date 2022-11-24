package com.ejbank.model;

import javax.persistence.*;

@Entity
@Table(name = "ejbak_user")
public class UserModel {
    private int id;
    private String login;
    private String password;
    private String email;
    private String firstname;
    private String lastname;
    private String type;

    public UserModel() {
    }

    @Id
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "login", nullable = false, length = 8)
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }

    @Column(name = "password", nullable = false, length = 255)
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "email", nullable = false, length = 255)
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "firstname", nullable = false, length = 50)
    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Column(name = "lastname", nullable = false, length = 50)
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Column(name = "type", nullable = false, length = 50)
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
}
