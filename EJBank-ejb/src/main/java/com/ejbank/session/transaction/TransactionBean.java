package com.ejbank.session.transaction;

import com.ejbank.model.AccountModel;
import com.ejbank.model.AdvisorModel;
import com.ejbank.model.TransactionModel;
import com.ejbank.model.UserModel;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
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
        System.out.println("source : "+source+ " "+"dest : "+dest+ " "+"amount : "+amount+ " "+"author : "+author+ " ");
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
            payload.setBefore(sourceBal.subtract(BigDecimal.valueOf(amount)));
            payload.setAfter(destBal.add(BigDecimal.valueOf(amount)));
            return payload;
        }
        payload.setResult(false);
        payload.setMessage("Vous ne disposez pas d'un solde suffisant");
        payload.setBefore(sourceBal.add(BigDecimal.valueOf(-amount)));
        payload.setAfter(destBal.add(BigDecimal.valueOf(amount)));
        return payload;
    }

    @Override
    public Integer waitingValidation(int userId) {
        UserModel user = em.find(UserModel.class, userId);
        int notification = 0;
        if(user instanceof AdvisorModel) {
            List<UserModel> customers = em.createQuery("select c from CustomerModel c where c.advisor=:advisor", UserModel.class)
                    .setParameter("advisor", user)
                    .getResultList();
            for (UserModel customer : customers) {
                notification += getUserNotif(customer);
            }
            return notification;
        } else
            return getUserNotif(user);
    }

    private Integer getUserNotif(UserModel user) {
        Long value = 0L;
        List<AccountModel> accounts = em.createQuery("select a from AccountModel a where a.customer =:user", AccountModel.class)
                .setParameter("user", user)
                .getResultList();
        for (AccountModel account: accounts) {
            value += (Long) em.createQuery("select count(t) from TransactionModel t where (t.account_id_to = :id or t.account_id_from = :id) and t.applied = false")
                    .setParameter("id", account)
                    .getSingleResult();
        }
        return Math.toIntExact(value);
    }

    @Override
    public TransactionValidationResponsePayload validationTransaction(TransactionValidationRequestPayload transactionValidationRequestPayload) {
        TransactionModel transactionModel = em.find(TransactionModel.class, transactionValidationRequestPayload.getTransaction());
        TransactionModel transaction = new TransactionModel(
                transactionModel.getAccount_id_from(),
                transactionModel.getAccount_id_to(),
                transactionModel.getAuthor(),
                transactionModel.getAmount(),
                transactionModel.getComment()
        );
        transaction.setId(transactionValidationRequestPayload.getTransaction());
        transaction.setApplied(transactionValidationRequestPayload.isApprove());
        transaction.setDateTime(transactionModel.getDateTime());
        if(em.merge(transaction) != transactionModel && transactionValidationRequestPayload.isApprove()) {
            return new TransactionValidationResponsePayload(
              false,
              "Erreur de validation",
                    "Erreur lors de la modification"
            );
        }
        if (transactionValidationRequestPayload.isApprove()) {
            return new TransactionValidationResponsePayload(
                    true,
                    "Transaction validée " + transactionModel.getId(),
//                    String.valueOf(transactionModel.getId()),
                    null
            );
        } else {
            return new TransactionValidationResponsePayload(
                    true,
                    "Transaction rejetée",
                    null
            );
        }




    }
}