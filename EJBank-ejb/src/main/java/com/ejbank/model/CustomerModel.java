package com.ejbank.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ejbank_customer")
@DiscriminatorValue(value="customer")
public class CustomerModel extends UserModel{

    @ManyToOne
    @JoinColumn(name = "advisor_id", nullable = false)
    private AdvisorModel advisor;

    @OneToMany
    @JoinColumn(name = "customer_id")
    private List<AccountModel> accountModels;

    public CustomerModel() {
    }

//    public AdvisorModel getAdvisor() {
//        return advisor;
//    }
//    public void setAdvisor(AdvisorModel advisor) {
//        this.advisor = advisor;
//    }
}
