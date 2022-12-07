package com.ejbank.model;

import javax.persistence.*;

@Entity
@Table(name = "ejbank_account")
public class AccountModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerModel customer;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_type_id", nullable = false)
    private AccountTypeModel accountType;
    @Column(name = "balance", nullable = false)
    private double balance;

    public AccountModel() {
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getCustomer_id() {
        return customer.getId();
    }
    public void setCustomer_id(int customer_id) {
        this.setId(customer_id);
    }

    public AccountTypeModel getAccountType() {
        return accountType;
    }
    public void setAccountType(AccountTypeModel accountType) {
        this.accountType = accountType;
    }

    public double getBalance() {
        return balance;
    }
    public void setBalance(int balance) {
        this.balance = balance;
    }
}
