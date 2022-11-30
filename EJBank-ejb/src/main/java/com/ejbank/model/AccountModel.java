package com.ejbank.model;

import javax.persistence.*;

@Entity
@Table(name = "ejbank_account")
public class AccountModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "customer_id", nullable = false)
    private int customer_id;
    @Column(name = "account_type_id", nullable = false)
    private int account_type_id;
    @Column(name = "balance", nullable = false)
    private int balance; //DECIMAL(10,0)

    public AccountModel() {
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getCustomer_id() {
        return customer_id;
    }
    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getAccount_type_id() {
        return account_type_id;
    }
    public void setAccount_type_id(int account_type_id) {
        this.account_type_id = account_type_id;
    }

    public int getBalance() {
        return balance;
    }
    public void setBalance(int balance) {
        this.balance = balance;
    }
}
