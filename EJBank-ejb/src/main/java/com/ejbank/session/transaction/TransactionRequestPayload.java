package com.ejbank.session.transaction;

import java.math.BigDecimal;

public class TransactionRequestPayload {
    private final int source;
    private final int destination;
    private final BigDecimal amount;
    private final String comment;
    private final int author;

    public TransactionRequestPayload(int source, int destination, BigDecimal amount, String comment, int author) {
        this.source = source;
        this.destination = destination;
        this.amount = amount;
        this.comment = comment;
        this.author = author;
    }

    public int getSource() {
        return source;
    }

    public int getDestination() {
        return destination;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getComment() {
        return comment;
    }

    public int getAuthor() {
        return author;
    }
}
