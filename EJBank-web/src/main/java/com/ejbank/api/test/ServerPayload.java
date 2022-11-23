package com.ejbank.api.test;

public class ServerPayload {

    private final boolean result;

    public ServerPayload(boolean result) {
        this.result = result;
    }

    public boolean isResult() {
        return result;
    }
}
