package com.ejbank.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "ejbank_transaction")
public class TransactionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "account_id_from", nullable = false)
    private int account_id_from;
    @Column(name = "account_id_to", nullable = false)
    private int account_id_to;
    @Column(name = "author", nullable = false)
    private int author;
    @Column(name = "amount", nullable = false)
    private double amount;
    @Column(name = "comment", nullable = false, length = 255)
    private String comment;
    @Column(name = "applied", nullable = false)
    private Boolean applied;
    @Column(name = "date", nullable = false)
    private Date dateTime;

    public TransactionModel() {
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getAccountIdFrom() {
        return account_id_from;
    }
    public void setAccountIdFrom(int account_id_from) {
        this.account_id_from = account_id_from;
    }

    public int getAccountIdTo() {
        return account_id_to;
    }
    public void setAccountIdTo(int account_id_to) {
        this.account_id_to = account_id_to;
    }

    public int getAuthor() {
        return author;
    }
    public void setAuthor(int author) {
        this.author = author;
    }

    public double getAmount() {
        return amount;
    }
    public void setAmount(float amount) {
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
}
