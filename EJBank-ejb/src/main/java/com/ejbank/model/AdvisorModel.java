package com.ejbank.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ejbank_advisor")
@DiscriminatorValue(value="advisor")
public class AdvisorModel extends UserModel{

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "advisor")
    private List<CustomerModel> customerModels;

    public AdvisorModel() {
    }
    public List<CustomerModel> getCustomerModels(){
        return customerModels;
    }
}
