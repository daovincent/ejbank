package com.ejbank.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

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
    private BigDecimal balance;

    @OneToMany
    @JoinColumn(name = "account_id_from")
    private Set<TransactionModel> transactionsFrom;

    @OneToMany
    @JoinColumn(name = "account_id_to")
    private Set<TransactionModel> transactionsTo;
    public AccountModel() {
    }

    public CustomerModel getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerModel customer) {
        this.customer = customer;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Set<TransactionModel> getTransactionsFrom() {
        return transactionsFrom;
    }

    public void setTransactionsFrom(Set<TransactionModel> transactionsFrom) {
        this.transactionsFrom = transactionsFrom;
    }

    public Set<TransactionModel> getTransactionsTo() {
        return transactionsTo;
    }

    public void setTransactionsTo(Set<TransactionModel> transactionsTo) {
        this.transactionsTo = transactionsTo;
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

    public BigDecimal getBalance() {
        return balance;
    }
    public void setBalance(int balance) {
        this.balance = BigDecimal.valueOf(balance);
    }
}
