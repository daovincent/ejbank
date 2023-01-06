package com.ejbank.session.transaction;

import javax.ejb.Local;

@Local
public interface TransactionBeanLocal {
    TransactionResponsePayload submitTransaction(TransactionRequestPayload transactionRequestPayload);
    TransactionPreviewResponsePayload transactionPreview(int source, int dest, double amount, int author);
    TransactionListPayload listTransactions(int userId, int accountId, int offset);
}