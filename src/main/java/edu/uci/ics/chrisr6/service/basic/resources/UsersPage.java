package edu.uci.ics.chrisr6.service.basic.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.uci.ics.chrisr6.service.basic.App;
import edu.uci.ics.chrisr6.service.basic.core.Users;
import edu.uci.ics.chrisr6.service.basic.logger.ServiceLogger;
import edu.uci.ics.chrisr6.service.basic.models.*;
import edu.uci.ics.chrisr6.service.basic.models.Users.CredentialRequestModel;
import edu.uci.ics.chrisr6.service.basic.utilities.CredentialValidation;
import edu.uci.ics.chrisr6.service.basic.utilities.security.Crypto;
import org.apache.commons.codec.binary.Hex;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
            GeneralResponseModel responseModel = new GeneralResponseModel(2);
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
            GeneralResponseModel responseModel = new GeneralResponseModel(2);
            return Response.status(Status.BAD_REQUEST).entity(responseModel).build();
        }
    }
}
