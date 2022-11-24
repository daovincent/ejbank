package com.ejbank.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ejbank_advisor")
public class AdvisorModel {
    private int id;

    public AdvisorModel() {
    }

    @Id
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
