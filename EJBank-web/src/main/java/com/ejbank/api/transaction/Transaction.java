//package com.ejbank.api.transaction;
//
//import javax.ejb.EJB;
//import javax.faces.bean.RequestScoped;
//import javax.ws.rs.GET;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.MediaType;
//
//@Path("/transaction")
//@Produces(MediaType.APPLICATION_JSON)
//@RequestScoped
//public class Transaction {
//
//    @EJB
//    private TransactionPayload transactionPayload;
//
//    @GET
//    @Path("/list/{account_id}/{offset}/{user_id}")
//    public String getTransactionList(int account_id, int user_id) {
//        return "";
//    }
//
//    @POST
//    @Path("/preview")
//    public String recapitulationTransaction() {
//        return "";
//    }
//
//    @POST
//    @Path("/apply")
//    public String applyTransaction() {
//        return "";
//    }
//
//    @POST
//    @Path("/validation")
//    public String validationTransaction() {
//        return "";
//    }
//
//    @GET
//    @Path("/validation/notification/{user_id}")
//    public String waintingValidationTransaction() {
//        return "";
//    }
//}
