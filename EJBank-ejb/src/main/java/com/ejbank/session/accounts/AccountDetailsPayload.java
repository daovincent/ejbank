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

    public AccountDetailsPayload(String owner, String advisor, int rate, String interest, BigDecimal amount, String error) {
        this.owner = owner;
        this.advisor = advisor;
        this.rate = rate;
        this.interest = interest;
        this.amount = amount;
        this.error = error;
    }

    private final String owner;
    private final String advisor;
    private final int rate;
    private final String interest;
    private final BigDecimal amount;
    private final String error;

    public final String getOwner() {
        return owner;
    }

    public String getAdvisor() {
        return advisor;
    }

    public int getRate() {
        return rate;
    }

    public String getInterest() {
        return interest;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getError() {
        return error;
    }
}
