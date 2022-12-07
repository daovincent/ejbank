package com.ejbank.session.accounts;

import com.ejbank.model.AccountModel;

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

    @Override
    public AccountsPayload getCustomerAccounts(int id) {
        List<AccountModel> accounts = em
                .createQuery(
                        "SELECT account FROM AccountModel account WHERE account.customer.id=:id",
                        AccountModel.class
                )
                .setParameter("id", id)
                .getResultList();

        List<AccountsPayload.AccountsDetailsPayload> details = new ArrayList<>();
        for (AccountModel accountModel : accounts) {
            details.add(new AccountsPayload.AccountsDetailsPayload(
                    accountModel.getId(),
                    accountModel.getAccountType().getName(),
                    accountModel.getBalance()
                    ));
        }
        return new AccountsPayload(details, null);
    }
}
