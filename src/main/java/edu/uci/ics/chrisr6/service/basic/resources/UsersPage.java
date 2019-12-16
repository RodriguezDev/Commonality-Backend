package edu.uci.ics.chrisr6.service.basic.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.uci.ics.chrisr6.service.basic.core.Users;
import edu.uci.ics.chrisr6.service.basic.models.*;
import edu.uci.ics.chrisr6.service.basic.models.Users.CredentialRequestModel;
import edu.uci.ics.chrisr6.service.basic.utilities.DatabaseOperations;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.io.IOException;

@Path("users")
public class UsersPage {

    @Path("register")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerUser(String jsonText) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            CredentialRequestModel requestModel = mapper.readValue(jsonText, CredentialRequestModel.class);
            return Users.registerUser(requestModel);
        } catch (IOException e) {
            GeneralResponseModel responseModel = new GeneralResponseModel(-2);
            return Response.status(Status.BAD_REQUEST).entity(responseModel).build();
        }
    }

    @Path("login")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginUser(String jsonText) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            CredentialRequestModel requestModel = mapper.readValue(jsonText, CredentialRequestModel.class);
            return Users.loginUser(requestModel);
        } catch (IOException e) {
            GeneralResponseModel responseModel = new GeneralResponseModel(-2);
            return Response.status(Status.BAD_REQUEST).entity(responseModel).build();
        }
    }

    @Path("setHandle")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response setHandle(@Context HttpHeaders headers, String jsonText) {
        String email = headers.getHeaderString("email");
        String sessionID = headers.getHeaderString("sessionID");

        if (!DatabaseOperations.checkSessionValid(email, sessionID)) return Response.status(Status.UNAUTHORIZED).entity(new GeneralResponseModel(-1)).build();

        ObjectMapper mapper = new ObjectMapper();

        try {
            SingleStringRequestModel requestModel = mapper.readValue(jsonText, SingleStringRequestModel.class);
            return Users.setHandle(requestModel, email);
        } catch (IOException e) {
            GeneralResponseModel responseModel = new GeneralResponseModel(-2);
            return Response.status(Status.BAD_REQUEST).entity(responseModel).build();
        }
    }

    @Path("setName")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response setName(@Context HttpHeaders headers, String jsonText) {
        String email = headers.getHeaderString("email");
        String sessionID = headers.getHeaderString("sessionID");

        if (!DatabaseOperations.checkSessionValid(email, sessionID)) return Response.status(Status.UNAUTHORIZED).entity(new GeneralResponseModel(-1)).build();

        ObjectMapper mapper = new ObjectMapper();

        try {
            SingleStringRequestModel requestModel = mapper.readValue(jsonText, SingleStringRequestModel.class);
            return Users.setName(requestModel, email);
        } catch (IOException e) {
            GeneralResponseModel responseModel = new GeneralResponseModel(-2);
            return Response.status(Status.BAD_REQUEST).entity(responseModel).build();
        }
    }

    @Path("logout")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response setName(@Context HttpHeaders headers) {
        String email = headers.getHeaderString("email");
        String sessionID = headers.getHeaderString("sessionID");

        return Users.logout(email, sessionID);
    }
}
