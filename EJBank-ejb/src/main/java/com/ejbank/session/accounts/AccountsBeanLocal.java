package com.ejbank.session.accounts;

import javax.ejb.Local;

@Local
public interface AccountsBeanLocal {

    AccountsPayload getCustomerAccounts(int id);
    AccountsPayload getAllAccounts(int id);
}
