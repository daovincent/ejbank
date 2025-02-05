package com.ejbank.api.server;

import com.ejbank.session.test.ServerPayload;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/server")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class CheckServer {

    @GET
    @Path("/status")
    public ServerPayload getStatus() {
        return new ServerPayload(true);
    }
}
