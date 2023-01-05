package com.ejbank.session.transaction;

import com.ejbank.model.AccountModel;
import com.ejbank.model.TransactionModel;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;

@Stateless
@LocalBean
public class TransactionBean implements TransactionBeanLocal{

    @PersistenceContext(unitName = "EJBankPU")
    private EntityManager em;

    @Override
    public TransactionResponsePayload submitTransaction(TransactionRequestPayload transactionRequestPayload) {
        int source = transactionRequestPayload.getSource();
        double amount = transactionRequestPayload.getAmount();
        AccountModel account = em.find(AccountModel.class, source);
        if (account.getBalance() > amount) {
            TransactionModel transactionModel = new TransactionModel();
            transactionModel.setAmount((float) amount);
            transactionModel.setAuthor(transactionRequestPayload.getAuthor());
            transactionModel.setAccountIdFrom(source);
            transactionModel.setAccountIdTo(transactionRequestPayload.getDestination());
            transactionModel.setComment(transactionRequestPayload.getComment());
            transactionModel.setApplied(false);
            em.persist(transactionModel);
            return new TransactionResponsePayload("true", "transaction submitted");
        }
        return new TransactionResponsePayload("false", "transaction rejected");
    }
}
