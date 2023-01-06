package com.ejbank.session.transaction;

public class TransactionValidationRequestPayload {
    private int transaction;
    private boolean approve;
    private int author;

    public int getTransaction() {
        return transaction;
    }

    public void setTransaction(int transaction) {
        this.transaction = transaction;
    }

    public boolean isApprove() {
        return approve;
    }

    public void setApprove(boolean approveDecision) {
        this.approve = approveDecision;
    }

    public int getAuthor() {
        return author;
    }

    public void setAuthor(int author) {
        this.author = author;
    }
}
