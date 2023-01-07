package com.ejbank.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "ejbank_transaction")
public class TransactionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "account_id_from", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private AccountModel account_id_from;
    @JoinColumn(name = "account_id_to", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private AccountModel account_id_to;
    @JoinColumn(name = "author", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private UserModel author;
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;
    @Column(name = "comment", nullable = false, length = 255)
    private String comment;
    @Column(name = "applied", nullable = false)
    private Boolean applied;
    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTime;

    public TransactionModel() {
    }

    public TransactionModel(AccountModel account_id_from, AccountModel account_id_to, UserModel author, BigDecimal amount, String comment) {
        this.account_id_from = account_id_from;
        this.account_id_to = account_id_to;
        this.author = author;
        this.amount = amount;
        this.comment = comment;
        this.applied = false;
        this.dateTime = new Date(System.currentTimeMillis());
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public AccountModel getAccount_id_from() {
        return account_id_from;
    }

    public void setAccount_id_from(AccountModel account_id_from) {
        this.account_id_from = account_id_from;
    }

    public AccountModel getAccount_id_to() {
        return account_id_to;
    }

    public void setAccount_id_to(AccountModel account_id_to) {
        this.account_id_to = account_id_to;
    }

    public UserModel getAuthor() {
        return author;
    }

    public void setAuthor(UserModel author) {
        this.author = author;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean getApplied() {
        return applied;
    }
    public void setApplied(boolean applied) {
        this.applied = applied;
    }

    public Date getDateTime() {
        return dateTime;
    }
    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "TransactionModel{" +
                "id=" + id +
                ", account_id_from=" + account_id_from +
                ", account_id_to=" + account_id_to +
                ", author=" + author +
                ", amount=" + amount +
                ", comment='" + comment + '\'' +
                ", applied=" + applied +
                ", dateTime=" + dateTime +
                '}';
    }
}
