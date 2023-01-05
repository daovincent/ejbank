package com.ejbank.session.user;

import com.ejbank.model.UserModel;

import javax.ejb.Local;

@Local
public interface UserBeanLocal {
    UserPayload getUser(int id);
}
