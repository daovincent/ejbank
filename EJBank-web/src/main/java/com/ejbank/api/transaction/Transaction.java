package com.ejbank.api.transaction;

import com.ejbank.session.transaction.*;

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

    @GET
    @Path("/list/{account_id}/{offset}/{user_id}")
    public TransactionListPayload getTransactionList(@PathParam("account_id") int accountId,@PathParam("user_id") int userId,@PathParam("offset") int offset) {
        return transactionBeanLocal.listTransactions(userId,accountId,offset);
    }

    @POST
    @Path("/preview")
    @Consumes(MediaType.APPLICATION_JSON)
    public TransactionPreviewResponsePayload recapitulationTransaction(TransactionPreviewRequestPayload payload) {
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

//    @GET
//    @Path("/validation/notification/{user_id}")
//    public String waintingValidationTransaction() {
//        return "";
//    }
}
