package com.ejbank.session.accounts;

import com.ejbank.model.AccountModel;
import com.ejbank.model.AdvisorModel;
import com.ejbank.model.CustomerModel;
import com.ejbank.model.UserModel;
import com.ejbank.session.transaction.TransactionBean;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.*;

@Stateless
@LocalBean
public class AccountsBean implements AccountsBeanLocal {

    @PersistenceContext(unitName = "EJBankPU")
    private EntityManager em;
    @EJB
    private TransactionBean transactionBean;

    private List<AccountModel> getAccount(CustomerModel c){
        return c.getAccountModels();
    }
    @Override
    public AccountsPayload getCustomerAccounts(int id) {
        var user=em.find(UserModel.class,id);
        ArrayList<AccountModel> accounts = new ArrayList<>();
        if(user instanceof CustomerModel){
            accounts.addAll(getAccount((CustomerModel) user));
        } else if (user instanceof AdvisorModel) {
            var customers = ((AdvisorModel) user).getCustomerModels();
            customers.forEach(c->accounts.addAll(c.getAccountModels()));
        }
        List<AccountsPayload.AccountsDetailsPayload> details = new ArrayList<>();
        for (AccountModel accountModel : accounts) {
            details.add(new AccountsPayload.AccountsDetailsPayload(
                    accountModel.getId(),
                    accountModel.getAccountType().getName(),
                    accountModel.getBalance()
            ));
        }
        return new AccountsPayload(details, details.isEmpty()?"Error : No account found for this user":null);
    }
    public AccountsPayload getAllAccounts(int id) {
        var user=em.find(UserModel.class,id);
        HashMap<String,List<AccountModel>> accounts = new HashMap<>();
        if(user instanceof CustomerModel){
            var name= user.getFirstname()+" "+user.getLastname();
            accounts.put(name,getAccount((CustomerModel) user));

        } else if (user instanceof AdvisorModel) {
            var customers = ((AdvisorModel) user).getCustomerModels();
            customers.forEach(c->{
                var name = c.getFirstname()+" "+c.getLastname();
                accounts.put(name,c.getAccountModels());
            });
        }

        List<AccountsPayload.AccountsDetailsPayload> details = new ArrayList<>();
        accounts.entrySet().forEach(e->e.getValue().forEach(a->{
            var payload=new AccountsPayload.AccountsDetailsPayload(
                    a.getId(),
                    a.getAccountType().getName(),
                    a.getBalance());
            payload.setUser(e.getKey());
            details.add(payload);

        }));
        return new AccountsPayload(details, details.isEmpty()?"Error : No account found for this user":null);
    }

    public AccountDetailsPayload getDetailedAccount(int userId, int accountId){
        var user=em.find(UserModel.class,userId);
        var account=em.find(AccountModel.class,accountId);
        var owner=account.getCustomer();
        if (account == null){ // wdym its always false
            return  new AccountDetailsPayload(null,null,0,"0",null,"Error : this account doesn't exist");

        }
        if((user instanceof CustomerModel && account.getCustomer_id()!= user.getId()) && account.getCustomer().getAdvisor().getId()!=userId ){
            return  new AccountDetailsPayload(null,null,0,"0",null,"Error : this user isn't allowed to see this account");
        }
        var advisor=account.getCustomer().getAdvisor();
        var rate = account.getAccountType().getRate();
        var balance = account.getBalance();
        return new AccountDetailsPayload(owner.getFirstname()+" "+owner.getLastname(),
                advisor.getFirstname()+" "+advisor.getLastname(),rate,rate==0?"0":getInterest(account).toString(),balance,null);
    }
    // Interest = average of interest of each month
    // No idea if this works ...
    private BigDecimal getInterest(AccountModel account){
        var rate = account.getAccountType().getRate();
        var balance = account.getBalance();
        // Interest of a month = taux * balance of month
        // balance of month = current balance - transactions of current month
        var offset= 0 ;
        var calNow = Calendar.getInstance();
        var month = Calendar.getInstance();
        month.getActualMaximum(Calendar.DAY_OF_MONTH);
        var limit = Calendar.getInstance();
        limit.set(Calendar.MONTH,1);
        limit.set(Calendar.DAY_OF_MONTH,1);
        month.add(Calendar.MONTH,-1); // Remove 1 month

        var monthBalance=new ArrayList<BigDecimal>();
        var interests=new ArrayList<BigDecimal>();
        int months=1;
        while(true) {
            var last10tr = transactionBean.getTr(account.getId(), offset);
            if(last10tr.isEmpty()) return (interests.stream().reduce(BigDecimal.ZERO,BigDecimal::add)).divide(BigDecimal.valueOf(months));
            offset+=10;
            for(var tr : last10tr){
                if(tr.getDateTime().before(month.getTime())){ // Check if TR was in another month
                    if(tr.getDateTime().before(limit.getTime())){ // Check if tr was previous year
                        return (interests.stream().reduce(BigDecimal.ZERO,BigDecimal::add)).divide(BigDecimal.valueOf(months));
                    }
                    var totalMonth=monthBalance.stream().reduce(BigDecimal.ZERO,BigDecimal::add); //All TR of month total = value of month
                    balance=balance.subtract(totalMonth);
                    month.add(Calendar.MONTH,-1); // Change months (return to prior month)
                    month.getActualMaximum(Calendar.DAY_OF_MONTH);
                    months++; // Number of months ++
                    // If something wrong happens ...
                    if(months>12) return (interests.stream().reduce(BigDecimal.ZERO,BigDecimal::add)).divide(BigDecimal.valueOf(months));
                    interests.add(balance.multiply(BigDecimal.valueOf(rate))); // balance of month * rate
                    monthBalance=new ArrayList<>(); // Clear month TRs
                }
                monthBalance.add(tr.getAmount()); // Add tr value to month
            }
        }

    }
}