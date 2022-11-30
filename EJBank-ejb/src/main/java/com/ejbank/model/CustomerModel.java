package com.ejbank.model;

import javax.persistence.*;

@Entity
@Table(name = "ejbank_customer")
public class CustomerModel {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int id;
    @Column(name = "advisor_id", nullable = false)
    private int advisor_id;

    public CustomerModel() {
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getAdvisor_id() {
        return advisor_id;
    }
    public void setAdvisor_id(int advisor_id) {
        this.advisor_id = advisor_id;
    }
}
