package com.ejbank.session.transaction;

import com.ejbank.model.AccountModel;
import com.ejbank.model.TransactionModel;
import com.ejbank.model.UserModel;

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
    @Override
    public TransactionPayload transactionPreview(int source, int dest, double amount, int author){
//        System.out.println("source : "+source+ " "+"dest : "+dest+ " "+"amount : "+amount+ " "+"author : "+author+ " ");
        var payload=new TransactionPayload();
        var sourceAcc=em.find(AccountModel.class, source);
        var destAcc=em.find(AccountModel.class,dest);
        if (sourceAcc==null || destAcc==null) {
            payload.setResult(false);
            payload.setBefore(amount);
            payload.setAfter(0);
            payload.setError("Error : One of the accounts given doesn't exist");
            return payload;
        }
        var sourceBal=sourceAcc.getBalance();
        var destBal=destAcc.getBalance();
//        // Should verify if source account belongs to the author
        if(sourceBal>0 && sourceBal> amount){
            System.out.println(true);
            payload.setResult(true);
            // Result of transaction or result for source account ???
            payload.setBefore(sourceBal-amount);
            payload.setAfter(destBal+amount);
            return payload;
        }
        payload.setResult(false);
        payload.setMessage("Vous ne disposez pas d'un solde suffisant");
        return payload;
    }
}