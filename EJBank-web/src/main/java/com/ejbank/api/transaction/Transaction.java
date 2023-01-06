package com.ejbank.api.transaction;

import com.ejbank.session.transaction.TransactionBeanLocal;
import com.ejbank.session.transaction.TransactionPayload;
import com.ejbank.session.transaction.TransactionRequestPayload;
import com.ejbank.session.transaction.TransactionResponsePayload;

import javax.ejb.EJB;
import javax.faces.bean.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/transaction")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class Transaction {

    @EJB
    private TransactionBeanLocal transactionBeanLocal;

//    @GET
//    @Path("/list/{account_id}/{offset}/{user_id}")
//    public String getTransactionList(int account_id, int user_id) {
//        return "";
//    }

    @POST
    @Path("/preview")
    @Consumes(MediaType.APPLICATION_JSON)
    public TransactionPayload recapitulationTransaction(TransactionPayload payload) {
        System.out.println(payload);
        return transactionBeanLocal.transactionPreview(payload.getSource(),payload.getDestination(),payload.getAmount(),payload.getAuthor());
    }

    @POST
    @Path("/apply")
    @Consumes(MediaType.APPLICATION_JSON)
    public TransactionResponsePayload applyTransaction(TransactionRequestPayload transactionRequestPayload) {
        System.out.println(transactionRequestPayload.getAmount());
        return transactionBeanLocal.submitTransaction(transactionRequestPayload);
}

//    @POST
//    @Path("/validation")
//    public String validationTransaction() {
//        return "";
//    }

    @GET
    @Path("/validation/notification/{user_id}")
    public Integer waintingValidationTransaction(@PathParam("user_id") Integer id) {
        return transactionBeanLocal.waitingValidation(id);
    }
}
