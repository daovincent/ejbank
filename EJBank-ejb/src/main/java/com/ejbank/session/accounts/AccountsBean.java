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
        System.out.println("Accounts : "+accounts);

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
}
