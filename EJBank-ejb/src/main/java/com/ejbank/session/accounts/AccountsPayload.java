package com.ejbank.session.accounts;

import java.util.List;

public class AccountsPayload {

    public static class AccountsDetailsPayload {
        private final int id;
        private final String type;
        private final double amount;

        public AccountsDetailsPayload(int id, String type, double amount) {
            this.id = id;
            this.type = type;
            this.amount = amount;
        }

        public int getId() {
            return id;
        }

        public String getType() {
            return type;
        }

        public double getAmount() {
            return amount;
        }
    }

    private final List<AccountsDetailsPayload> accounts;
    private final String error;

    public AccountsPayload(List<AccountsDetailsPayload> accounts, String error) {
        this.accounts = accounts;
        this.error = error;
    }

    public List<AccountsDetailsPayload> getAccounts() {
        return accounts;
    }

    public String getError() {
        return error;
    }
}
