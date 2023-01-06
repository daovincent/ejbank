package com.ejbank.api.accounts;

import com.ejbank.session.accounts.AccountDetailsPayload;
import com.ejbank.session.accounts.AccountsBean;

import javax.ejb.EJB;
import javax.faces.bean.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/account")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class Account {

    @EJB
    private AccountsBean accountsBean;
    @GET
    public AccountDetailsPayload test(){
        return new AccountDetailsPayload();
    }

    @GET
    @Path("/{account_id}/{user_id}")
    public AccountDetailsPayload getAccount(@PathParam("account_id") int accountId, @PathParam("user_id") int userId) {
        return accountsBean.getDetailedAccount(userId,accountId);
    }
}
