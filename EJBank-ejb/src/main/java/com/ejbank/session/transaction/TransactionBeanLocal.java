package com.ejbank.session.transaction;

import javax.ejb.Local;

@Local
public interface TransactionBeanLocal {
    TransactionResponsePayload submitTransaction(TransactionRequestPayload transactionRequestPayload);
}
