package com.ejbank.session.transaction;

import com.ejbank.model.*;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
    public TransactionPreviewResponsePayload transactionPreview(int source, int dest, double amount, int author){
        System.out.println("source : "+source+ " "+"dest : "+dest+ " "+"amount : "+amount+ " "+"author : "+author+ " ");
        var payload=new TransactionPreviewResponsePayload();
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

    public TransactionListPayload listTransactions(int userId, int accountId, int offset){
        var payload= new TransactionListPayload();
        var user=em.find(UserModel.class,userId);
        var account=em.find(AccountModel.class,accountId);
        String author=null;
        String approval=null;
        if(user == null || account == null) {
            payload.setError("Error : There is a problem with the account id or the user id");
            return payload;
        }
        if (userId== account.getCustomer_id() ) {
            author= user.getFirstname()+" "+user.getLastname();
            approval="WAITING_APPROVAL";
        }
        else if(account.getCustomer().getAdvisor().getId()==userId){
            author=account.getCustomer().getAdvisor().getFirstname()+" "+account.getCustomer().getAdvisor().getLastname();
            approval="TO_APPROVE";
        }
        System.out.println(author);

        var transactions = getTr(accountId,offset);
        // Marche pas > faut faire une requête SQL
        var allT= new ArrayList<TransactionPayload>();
        for(var t : transactions){
            var transacPayload= new TransactionPayload();
            transacPayload.setDate(t.getDateTime());
            var destUser= t.getAccount_id_to().getCustomer().getFirstname() + " " + t.getAccount_id_to().getCustomer().getLastname();
            var source = t.getAccount_id_from().getId();
            var dest=t.getAccount_id_to().getCustomer_id();
            var state = t.getApplied()?"APPLYED":approval;
            transacPayload.setDestination(dest);
            transacPayload.setSource(source);
            transacPayload.setAuthor(author);
            transacPayload.setAmount(t.getAmount());
            transacPayload.setDestination_user(destUser);
            transacPayload.setState(state);
            transacPayload.setComment(t.getComment());
            System.out.println("T===="+t);
            System.out.println("TRANSAC ="+transacPayload);
            allT.add(transacPayload);
        }
        return payload;
    }

    private List<TransactionModel> getTr(int accountId, int offset){
        TypedQuery<TransactionModel> query = em.createQuery(
                "SELECT e FROM TransactionModel e " +
                        "WHERE (e.account_id_from=:accountId OR e.account_id_to=:accountId)" +
                        "ORDER BY e.dateTime desc", TransactionModel.class);
        query.setMaxResults(10);
        query.setFirstResult(offset);
        return query.getResultList();
    }
}