package edu.uci.ics.chrisr6.service.basic.resources;

import edu.uci.ics.chrisr6.service.basic.logger.ServiceLogger;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("test")
public class HelloPage {
    @Path("hello")
    @GET
    public Response helloWorld() {
        ServiceLogger.LOGGER.info("Hello world!");
        return Response.status(Status.OK).build();
    }
}