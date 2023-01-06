package com.ejbank.session.transaction;

import java.math.BigDecimal;

public class TransactionPreviewResponsePayload {
//    "result": true,
//            "before": 456,
//            "after": 350,
//            "message": "Vous ne disposez pas d'un solde suffisant... A ce titre, la variable 'result' du payload devrait être à 'false', mais elle est à 'true' pour vous permettre de tester l'envoie de la transaction ;)",
//            "error": null
    private boolean result;
    private BigDecimal before;
    private BigDecimal after;
    private String message;
    private String error;

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
}
