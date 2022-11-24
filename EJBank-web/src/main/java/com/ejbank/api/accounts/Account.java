package com.ejbank.api.accounts;

import com.ejbank.session.accounts.AccountsBean;

import javax.ejb.EJB;
import javax.faces.bean.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/account")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class Account {

    @EJB
    private AccountsBean accountsBean;

    @GET
    @Path("/{account_id}/{user_id}")
    public String getAccount(int account_id, int user_id) {
        return "";
    }
}
