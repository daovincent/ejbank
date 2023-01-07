package com.ejbank.session.accounts;

import com.ejbank.model.AccountModel;
import com.ejbank.model.AdvisorModel;
import com.ejbank.model.CustomerModel;
import com.ejbank.model.UserModel;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Stateless
@LocalBean
public class AccountsBean implements AccountsBeanLocal {

    @PersistenceContext(unitName = "EJBankPU")
    private EntityManager em;

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
        if (account == null){
            return  new AccountDetailsPayload(null,null,0,0,null,"Error : this account doesn't exist");

        }
//        if((user instanceof CustomerModel && account.getCustomer_id()!= user.getId()) || account.getCustomer().getAdvisor().getId()!=userId ){
//            return  new AccountDetailsPayload(null,null,0,0,null,"Error : this user isn't allowed to see this account");
//        }
        var advisor=account.getCustomer().getAdvisor();
        return new AccountDetailsPayload(user.getFirstname()+" "+user.getLastname(),
                advisor.getFirstname()+" "+advisor.getLastname(),account.getAccountType().getRate(),0,account.getBalance(),null);
    }
}