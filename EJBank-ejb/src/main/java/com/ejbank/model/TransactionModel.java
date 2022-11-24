package com.ejbank.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "ejbank_transaction")
public class TransactionModel {
    private int id;
    private int account_id_from;
    private int account_id_to;
    private int author;
    private float amount; //DECIMAL(10,2)
    private String comment;
    private int applied; //TINYINT(1)
    private Date dateTime; //DATETIME

    public TransactionModel() {
    }

    @Id
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "account_id_from", nullable = false)
    public int getAccountIdFrom() {
        return account_id_from;
    }
    public void setAccountIdFrom(int account_id_from) {
        this.account_id_from = account_id_from;
    }

    @Column(name = "account_id_to", nullable = false)
    public int getAccountIdTo() {
        return account_id_to;
    }
    public void setAccountIdTo(int account_id_to) {
        this.account_id_to = account_id_to;
    }

    @Column(name = "author", nullable = false)
    public int getAuthor() {
        return author;
    }
    public void setAuthor(int author) {
        this.author = author;
    }

    @Column(name = "amount", nullable = false)
    public float getAmount() {
        return amount;
    }
    public void setAmount(float amount) {
        this.amount = amount;
    }

    @Column(name = "comment", nullable = false, length = 255)
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    @Column(name = "applied", nullable = false)
    public int getApplied() {
        return applied;
    }
    public void setApplied(int applied) {
        this.applied = applied;
    }

    @Column(name = "date", nullable = false)
    public Date getDateTime() {
        return dateTime;
    }
    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
}
