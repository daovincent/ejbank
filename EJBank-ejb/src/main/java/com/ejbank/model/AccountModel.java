package com.ejbank.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ejbank_account")
public class AccountModel {
    private int id;
    private int customer_id;
    private int account_type_id;
    private int balance; //DECIMAL(10,0)

    public AccountModel() {
    }

    @Id
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "customer_id", nullable = false)
    public int getCustomer_id() {
        return customer_id;
    }
    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    @Column(name = "account_type_id", nullable = false)
    public int getAccount_type_id() {
        return account_type_id;
    }
    public void setAccount_type_id(int account_type_id) {
        this.account_type_id = account_type_id;
    }

    @Column(name = "balance", nullable = false)
    public int getBalance() {
        return balance;
    }
    public void setBalance(int balance) {
        this.balance = balance;
    }
}
