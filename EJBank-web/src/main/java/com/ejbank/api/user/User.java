package com.ejbank.api.user;

import com.ejbank.session.user.UserBeanLocal;
import com.ejbank.session.user.UserPayload;

import javax.ejb.EJB;
import javax.faces.bean.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class User {

    @EJB
    private UserBeanLocal userBeanLocal;

    @GET
    @Path("/{id}")
    public UserPayload getFullName(@PathParam("id") Integer id) {
        return userBeanLocal.getUser(id);
    }
}
