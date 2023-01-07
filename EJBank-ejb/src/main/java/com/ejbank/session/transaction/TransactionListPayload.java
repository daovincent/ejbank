package com.ejbank.session.transaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionListPayload {
    private List<TransactionPayload> transactions= new ArrayList<>();
    private String error;
    private int total;

    public List<TransactionPayload> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionPayload> transactions) {
        this.transactions = transactions;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public TransactionListPayload(List<TransactionPayload> transactions,  int total,String error) {
        this.transactions = transactions;
        this.error = error;
        this.total = total;
    }

    @Override
    public String toString() {
        return "TransactionListPayload{" +
                "transactions=" + transactions +
                ", error='" + error + '\'' +
                ", total=" + total +
                '}';
    }
}
