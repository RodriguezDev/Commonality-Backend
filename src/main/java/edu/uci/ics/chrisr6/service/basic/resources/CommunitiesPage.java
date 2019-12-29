package edu.uci.ics.chrisr6.service.basic.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.uci.ics.chrisr6.service.basic.core.Communities;
import edu.uci.ics.chrisr6.service.basic.core.Users;
import edu.uci.ics.chrisr6.service.basic.logger.ServiceLogger;
import edu.uci.ics.chrisr6.service.basic.models.*;
import edu.uci.ics.chrisr6.service.basic.models.Communities.CommunityCreateRequestModel;
import edu.uci.ics.chrisr6.service.basic.utilities.DatabaseOperations;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.io.IOException;

@Path("communities")
public class CommunitiesPage {
    @Path("create")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCommunity(String jsonText) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            CommunityCreateRequestModel requestModel = mapper.readValue(jsonText, CommunityCreateRequestModel.class);
            return Communities.createCommunity(requestModel);
        } catch (IOException e) {
            GeneralResponseModel responseModel = new GeneralResponseModel(-2);
            return Response.status(Status.BAD_REQUEST).entity(responseModel).build();
        }
    }
}
