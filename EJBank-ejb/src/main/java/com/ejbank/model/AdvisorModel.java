package com.ejbank.model;

import javax.persistence.*;

@Entity
@Table(name = "ejbank_advisor")
public class AdvisorModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    public AdvisorModel() {
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
