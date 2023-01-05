package com.ejbank.model;

import javax.persistence.*;

@Entity
@Table(name = "ejbank_account_type")
public class AccountTypeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    @Column(name = "rate", nullable = false)
    private int rate;
    @Column(name = "overdraft", nullable = false)
    private int overdraft;

    public AccountTypeModel() {
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getRate() {
        return rate;
    }
    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getOverdraft() {
        return overdraft;
    }
    public void setOverdraft(int overdraft) {
        this.overdraft = overdraft;
    }
}
