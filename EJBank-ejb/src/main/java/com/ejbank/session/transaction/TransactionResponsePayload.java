package com.ejbank.session.transaction;

public class TransactionResponsePayload {
    private final String result;
    private final String message;

    public TransactionResponsePayload(String result, String message) {
        this.result = result;
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }
}