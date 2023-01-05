package com.ejbank.session.transaction;

import java.math.BigDecimal;

public class TransactionPayload {
    private boolean result;
    private BigDecimal before;
    private BigDecimal after;
    private String message;
    private String error;
    private int id;
    private String date;
    private int source;
    private int destination;
    private int author;
    private String comment;
    private String state;
    private double amount;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public BigDecimal getBefore() {
        return before;
    }

    public void setBefore(BigDecimal before) {
        this.before = before;
    }

    public BigDecimal getAfter() {
        return after;
    }

    public void setAfter(BigDecimal after) {
        this.after = after;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public int getAuthor() {
        return author;
    }

    public void setAuthor(int author) {
        this.author = author;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}