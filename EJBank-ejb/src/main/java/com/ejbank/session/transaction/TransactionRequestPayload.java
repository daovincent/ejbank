package com.ejbank.session.transaction;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;

public class TransactionRequestPayload {
    private int source;
    private int destination;
    private double amount;
    private String comment;
    private int author;

    public void setSource(int source) {
        this.source = source;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setAuthor(int author) {
        this.author = author;
    }

    public int getSource() {
        return source;
    }

    public int getDestination() {
        return destination;
    }

    public double getAmount() {
        return amount;
    }

    public String getComment() {
        return comment;
    }

    public int getAuthor() {
        return author;
    }
}
