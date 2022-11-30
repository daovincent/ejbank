package com.ejbank.session.user;

import com.ejbank.model.UserModel;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class UserBean implements UserBeanLocal{

    @PersistenceContext(unitName = "EJBankPU")
    private EntityManager em;

    @Override
    public UserPayload getUser(int id) {
        UserModel user = em.find(UserModel.class, id);
        return new UserPayload(user.getFirstname(), user.getLastname());
    }
}
