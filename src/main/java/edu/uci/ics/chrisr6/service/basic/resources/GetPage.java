package edu.uci.ics.chrisr6.service.basic.resources;

import edu.uci.ics.chrisr6.service.basic.App;
import edu.uci.ics.chrisr6.service.basic.logger.ServiceLogger;
import edu.uci.ics.chrisr6.service.basic.models.GetAllResponseModel;
import edu.uci.ics.chrisr6.service.basic.models.GetIdResponseModel;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Path("get")
public class GetPage {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTotalRecords() {
        GetAllResponseModel responseModel = new GetAllResponseModel(0, "Number of records successfully retrieved.", 0);

        int total = 0;
        try {
            // Construct the query
            String query = "SELECT COUNT(*) AS 'total' FROM valid_strings;";

            // Create the prepared statement
            PreparedStatement ps = App.getCon().prepareStatement(query);

            // Save the query result to a Result Set so records may be retrieved
            ServiceLogger.LOGGER.info("Trying query: " + ps.toString());
            ResultSet rs = ps.executeQuery();
            ServiceLogger.LOGGER.info("Query succeeded.");

            rs.first();
            total = rs.getInt("total");
        } catch (SQLException e) {
            ServiceLogger.LOGGER.warning("Query failed: Unable to retrieve student records.");
            e.printStackTrace();
        }

        responseModel.setNumRecords(total);
        return Response.status(Status.OK).entity(responseModel).build();
    }
    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecordForID(@PathParam("id") String id) {

        int idInt;
        try {
            idInt = Integer.parseInt(id);
            String query = "SELECT * FROM valid_strings WHERE id=?;";
            PreparedStatement ps = App.getCon().prepareStatement(query);
            ps.setInt(1, idInt);

            ServiceLogger.LOGGER.info("Trying query: " + ps.toString());
            ResultSet rs = ps.executeQuery();
            ServiceLogger.LOGGER.info("Query succeeded.");

            rs.first();
            GetIdResponseModel responseModel = new GetIdResponseModel(0, "Record successfully retrieved",
                    rs.getInt("id"), rs.getString("sentence"), rs.getInt("length"));

            return Response.status(Status.OK).entity(responseModel).build();
        }
        catch (NumberFormatException | SQLException e)
        {
            GetIdResponseModel responseModel = new GetIdResponseModel();
            responseModel.setResultCode(1);
            responseModel.setMessage("Record not found.");
            return Response.status(Status.OK).entity(responseModel).build();
        }

    }
}
