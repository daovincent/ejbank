package com.ejbank.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ejbank_account_type")
public class AccountTypeModel {
    private int id;
    private String name;
    private int rate;
    private int overdraft;

    public AccountTypeModel() {
    }

    @Id
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false, length = 50)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "rate", nullable = false)
    public int getRate() {
        return rate;
    }
    public void setRate(int rate) {
        this.rate = rate;
    }

    @Column(name = "overdraft", nullable = false)
    public int getOverdraft() {
        return overdraft;
    }
    public void setOverdraft(int overdraft) {
        this.overdraft = overdraft;
    }
}
