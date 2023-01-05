package com.ejbank.session.transaction;

import com.ejbank.model.AccountModel;
import com.ejbank.model.TransactionModel;
import com.ejbank.model.UserModel;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.logging.Logger;

@Stateless
@LocalBean
public class TransactionBean implements TransactionBeanLocal{

    @PersistenceContext(unitName = "EJBankPU")
    private EntityManager em;

    @Override
    public TransactionResponsePayload submitTransaction(TransactionRequestPayload transactionRequestPayload) {
        Integer source = transactionRequestPayload.getSource();
        BigDecimal amount = transactionRequestPayload.getAmount();
        AccountModel account = em.find(AccountModel.class, source);
        if (account.getBalance().compareTo(amount) > 0) {
            TransactionModel transactionModel = new TransactionModel(
                    account,
                    em.find(AccountModel.class, transactionRequestPayload.getDestination()),
                    em.find(UserModel.class, transactionRequestPayload.getAuthor()),
                    transactionRequestPayload.getAmount(),
                    transactionRequestPayload.getComment()
            );
            transactionModel.setApplied(false);

            em.persist(transactionModel);
            return new TransactionResponsePayload("true", "transaction submitted");
        }
        return new TransactionResponsePayload("false", "transaction rejected");
    }
    @Override
    public TransactionPayload transactionPreview(int source, int dest, double amount, int author){
//        System.out.println("source : "+source+ " "+"dest : "+dest+ " "+"amount : "+amount+ " "+"author : "+author+ " ");
        var payload=new TransactionPayload();
        var sourceAcc=em.find(AccountModel.class, source);
        var destAcc=em.find(AccountModel.class,dest);
        if (sourceAcc==null || destAcc==null) {
            payload.setResult(false);
            payload.setBefore(BigDecimal.valueOf(amount));
            payload.setAfter(BigDecimal.ZERO);
            payload.setError("Error : One of the accounts given doesn't exist");
            return payload;
        }
        var sourceBal=sourceAcc.getBalance();
        var destBal=destAcc.getBalance();
//        // Should verify if source account belongs to the author
        if(sourceBal.compareTo(BigDecimal.ZERO) > 0 && sourceBal.compareTo(BigDecimal.valueOf(amount)) > 0){
            System.out.println(true);
            payload.setResult(true);
            // Result of transaction or result for source account ???
            payload.setBefore(sourceBal.add(BigDecimal.valueOf(-amount)));
            payload.setAfter(destBal.add(BigDecimal.valueOf(amount)));
            return payload;
        }
        payload.setResult(false);
        payload.setMessage("Vous ne disposez pas d'un solde suffisant");
        return payload;
    }
}