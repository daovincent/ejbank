package com.ejbank.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ejbank_customer")
public class CustomerModel {
    private int id;
    private int advisor_id;

    public CustomerModel() {
    }

    @Id
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "advisor_id", nullable = false)
    public int getAdvisor_id() {
        return advisor_id;
    }
    public void setAdvisor_id(int advisor_id) {
        this.advisor_id = advisor_id;
    }
}
