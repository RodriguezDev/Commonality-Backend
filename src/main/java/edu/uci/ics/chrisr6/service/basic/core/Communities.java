package edu.uci.ics.chrisr6.service.basic.core;

import edu.uci.ics.chrisr6.service.basic.App;
import edu.uci.ics.chrisr6.service.basic.logger.ServiceLogger;
import edu.uci.ics.chrisr6.service.basic.models.Communities.CommunitiesResponseModel;
import edu.uci.ics.chrisr6.service.basic.models.Communities.CommunityCreateRequestModel;
import edu.uci.ics.chrisr6.service.basic.utilities.DatabaseOperations;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Communities {

    /**
     * Given a name + description, create a community. Default to public
     * @param requestModel:  name + description
     * @return response
     */
    public static Response createCommunity(CommunityCreateRequestModel requestModel) {
        Connection con = App.getCon();
        String name = requestModel.getName().trim();

        // Check if the name is of valid length.
        if (name.length() == 0 || name.length() > 21) {
            ServiceLogger.LOGGER.info("Error creating community: name invalid length.");
            return Response.status(Status.BAD_REQUEST).entity(new CommunitiesResponseModel(-201)).build();
        }

        if (DatabaseOperations.communityNameInUse(name)) {
            ServiceLogger.LOGGER.info("Error creating community: name is already in use.");
            return Response.status(Status.BAD_REQUEST).entity(new CommunitiesResponseModel(-202)).build();
        }

        try {
            String query = "INSERT INTO communities VALUES (?, ?, 0, 0, 0)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, requestModel.getDescription());

            ps.execute();

            ServiceLogger.LOGGER.info("Successfully created community: " + name);
            return Response.status(Status.OK).entity(new CommunitiesResponseModel(200)).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }


    }
}
