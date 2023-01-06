package com.ejbank.session.transaction;

import java.math.BigDecimal;
import java.util.Date;

public class TransactionPayload {
//    {
//        "id": 271077732,
//            "date": "2019-10-16T15:28:02",
//            "source": "Label du compte source",
//            "destination": "Label du compte destination",
//            "destination_user": "Prénom destinataire",
//            "amount": 125.65,
//            "author": "Prénom Nom auteur",
//            "comment": "Cadeau pour Noël",
//            "state": "APPLYED"
//    },
    private int id;
    private Date date;
    private int source;
    private int destination;
    private String destination_user;
    private BigDecimal amount;
    private String author;
    private String comment;
    private String state;

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }



    public String getDestination_user() {
        return destination_user;
    }

    public void setDestination_user(String destination_user) {
        this.destination_user = destination_user;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}