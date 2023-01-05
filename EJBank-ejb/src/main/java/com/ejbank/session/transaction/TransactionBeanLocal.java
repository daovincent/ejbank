package com.ejbank.session.transaction;

import javax.ejb.Local;

@Local
public interface TransactionBeanLocal {
    TransactionResponsePayload submitTransaction(TransactionRequestPayload transactionRequestPayload);
    TransactionPayload transactionPreview(int source, int dest, double amount, int author);
}