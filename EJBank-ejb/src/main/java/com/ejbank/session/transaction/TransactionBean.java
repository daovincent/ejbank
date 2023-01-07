package com.ejbank.session.transaction;

import com.ejbank.model.*;
import com.ejbank.session.accounts.AccountDetailsPayload;

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
        var user=em.find(UserModel.class,userId);
        var account=em.find(AccountModel.class,accountId);
        String author=null;
        String approval=null;
        if(user == null || account == null) {
            return new TransactionListPayload(null,0,"Error : There is a problem with the account id or the user id");
        }
        if((user instanceof CustomerModel && account.getCustomer_id()!= user.getId()) || account.getCustomer().getAdvisor().getId()!=userId ){
            return new TransactionListPayload(null,0,"Error : This user isn't allowed to access this account");
        }
        if (userId== account.getCustomer_id() ) {
            author= user.getFirstname()+" "+user.getLastname();
            approval="WAITING_APPROVAL";
        }
        else if(account.getCustomer().getAdvisor().getId()==userId){
            author=account.getCustomer().getAdvisor().getFirstname()+" "+account.getCustomer().getAdvisor().getLastname();
            approval="TO_APPROVE";
        }

        var transactions = getTr(accountId,offset);
        // Marche pas > faut faire une requête SQL
        var allT= new ArrayList<TransactionPayload>();
        for(var t : transactions){
            var destUser= t.getAccount_id_to().getCustomer().getFirstname() + " " + t.getAccount_id_to().getCustomer().getLastname();
            var source = t.getAccount_id_from().getId();
            var dest=t.getAccount_id_to().getCustomer_id();
            var state = t.getApplied()?"APPLYED":approval;
            var transacPayload= new TransactionPayload(t.getId(),t.getDateTime(),source,dest,destUser,
                    t.getAmount(),author,t.getComment(),state);
            allT.add(transacPayload);
        }
        return new TransactionListPayload(allT,getNbTr(account),null);
    }

    private List<TransactionModel> getTr(int accountId, int offset){
        var account = em.find(AccountModel.class,accountId);
        TypedQuery<TransactionModel> query = em.createQuery(
                "SELECT e FROM TransactionModel e " +
                        "WHERE (e.account_id_from=:account OR e.account_id_to=:account)" +
                        "ORDER BY e.dateTime desc", TransactionModel.class);
        query.setParameter("account",account);
        query.setMaxResults(10);
        query.setFirstResult(offset);
        return query.getResultList();
    }
    private int getNbTr(AccountModel account){
        TypedQuery<Long> countQuery = em.createQuery(
                "SELECT COUNT(e) FROM TransactionModel e " +
                        "WHERE (e.account_id_from=:account OR e.account_id_to=:account)", Long.class);
        countQuery.setParameter("account",account);
        return Math.toIntExact(countQuery.getSingleResult());
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