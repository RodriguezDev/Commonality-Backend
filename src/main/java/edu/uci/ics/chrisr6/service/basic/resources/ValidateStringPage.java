package edu.uci.ics.chrisr6.service.basic.resources;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.uci.ics.chrisr6.service.basic.App;
import edu.uci.ics.chrisr6.service.basic.logger.ServiceLogger;
import edu.uci.ics.chrisr6.service.basic.models.ValidateStringRequestModel;
import edu.uci.ics.chrisr6.service.basic.models.ValidateStringResponseModel;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Path("validateString")
public class ValidateStringPage {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response generateResponse(String jsonText) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            ValidateStringRequestModel requestModel = mapper.readValue(jsonText, ValidateStringRequestModel.class);

            String[] strs = requestModel.getInput().split("\\s*(-|\\s)\\s*");
            int stringLen = 0;

            for (int i = 0; i < strs.length; i++) {
                if (!strs[i].matches("\\p{Punct}+")) {
                    stringLen++;
                }
            }


            ValidateStringResponseModel responseModel;
            Status status;

            if (requestModel.getInput() == null || requestModel.getInput().length() == 0) {
                responseModel = new ValidateStringResponseModel(3);
                status = Status.BAD_REQUEST;
            } else if (requestModel.getInput().length() > 512) {
                responseModel = new ValidateStringResponseModel(4);
                status = Status.BAD_REQUEST;
            } else if (requestModel.getLen() < 0) {
                responseModel = new ValidateStringResponseModel(5);
                status = Status.BAD_REQUEST;
            } else if (stringLen == requestModel.getLen()) {
                // Success = insert into database.

                ServiceLogger.LOGGER.info("Inserting student into database...");
                try {
                    // Construct the query
                    String query = "INSERT INTO valid_strings (sentence, length) VALUES (?, ?);";
                    // Create the prepared statement
                    PreparedStatement ps = App.getCon().prepareStatement(query);
                    // Set the paremeters
                    ps.setString(1, requestModel.getInput());
                    ps.setInt(2, requestModel.getLen());
                    // Execute query
                    ServiceLogger.LOGGER.info("Trying query: " + ps.toString());
                    ps.execute();
                    ServiceLogger.LOGGER.info("Success inserting: " + requestModel.getInput());
                } catch (SQLException e) {
                    ServiceLogger.LOGGER.warning("Unable to insert string: " + requestModel.getInput());
                    e.printStackTrace();
                }
                // END
                responseModel = new ValidateStringResponseModel(0);
                status = Status.OK;
            } else {
                responseModel = new ValidateStringResponseModel(1);
                status = Status.OK;
            }

            return Response.status(status).entity(responseModel).build();

        } catch (IOException e) {
            // Also a mapping and a parse exception
            // Mapping = format incorrect
            // Parse = badly formed JSON
            ValidateStringResponseModel responseModel = new ValidateStringResponseModel(2);
            return Response.status(Status.BAD_REQUEST).entity(responseModel).build(); // 400
        }
    }
}
