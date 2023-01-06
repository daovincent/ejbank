package com.ejbank.session.accounts;

import java.math.BigDecimal;

public class AccountDetailsPayload {
//    {
//        "owner": "Prénom Nom (client)",
//            "advisor": "Prénom Nom (conseillé)",
//            "rate": 15,
//            "interest": 56,
//            "amount": 1000,
//            "error": null
//    }
    private String owner;
    private String advisor;
    private int rate;
    private int interest;
    private BigDecimal amount;
    private String error;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAdvisor() {
        return advisor;
    }

    public void setAdvisor(String advisor) {
        this.advisor = advisor;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getInterest() {
        return interest;
    }

    public void setInterest(int interest) {
        this.interest = interest;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
