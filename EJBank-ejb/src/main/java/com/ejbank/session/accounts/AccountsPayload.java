package com.ejbank.session.accounts;

import java.math.BigDecimal;
import java.util.List;

public class AccountsPayload {

    public static class AccountsDetailsPayload {
        private final int id;
        private final String type;
        private final BigDecimal amount;
        private String user;

        public AccountsDetailsPayload(int id, String type, BigDecimal amount) {
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

        public BigDecimal getAmount() {
            return amount;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
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
