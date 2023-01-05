package com.ejbank.api.accounts;

import com.ejbank.session.accounts.AccountsBean;
import com.ejbank.session.accounts.AccountsBeanLocal;
import com.ejbank.session.accounts.AccountsPayload;

import javax.ejb.EJB;
import javax.faces.bean.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class Accounts {

    @EJB
    private AccountsBeanLocal accountsBean;

    @GET
    @Path("/{user_id}")
    public AccountsPayload getAccounts(@PathParam("user_id") int id) {
        System.out.println("ACCOUNTS");
        return accountsBean.getCustomerAccounts(id);
    }

    @GET
    @Path("/attached/{user_id}")
    public AccountsPayload getAttachedAccounts(@PathParam("user_id") int id) {
        return getAccounts(id);
    }

    @GET
    @Path("/all/{user_id}")
    public AccountsPayload getAllAccounts(@PathParam("user_id") int id) {
        return getAccounts(id);
    }

}
