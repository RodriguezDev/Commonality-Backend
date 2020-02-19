package edu.uci.ics.chrisr6.service.basic.core;

import edu.uci.ics.chrisr6.service.basic.App;
import edu.uci.ics.chrisr6.service.basic.logger.ServiceLogger;
import edu.uci.ics.chrisr6.service.basic.models.Communities.CommunitiesListResponseModel;
import edu.uci.ics.chrisr6.service.basic.models.Communities.CommunitiesResponseModel;
import edu.uci.ics.chrisr6.service.basic.models.Communities.CommunityCreateRequestModel;
import edu.uci.ics.chrisr6.service.basic.models.Communities.CommunityModel;
import edu.uci.ics.chrisr6.service.basic.utilities.DatabaseOperations;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

    /**
     * Given an offset, return the top communities in terms of # of members
     * @param offset: number to displace return value by
     * @return response
     */
    public static Response getTopCommunities(int offset, String orderBy, String ascDesc) {
        Connection con = App.getCon();

        // Check parameters for validity.
        if (offset < 0) {
            ServiceLogger.LOGGER.info("Error getting top communities: offset must be >= 0");
            return Response.status(Status.BAD_REQUEST).entity(new CommunitiesResponseModel(-203)).build();
        } else if (!orderBy.equals("members") && !orderBy.equals("name")) {
            ServiceLogger.LOGGER.info("Error getting top communities: illegal order by condition.");
            return Response.status(Status.BAD_REQUEST).entity(new CommunitiesResponseModel(-204)).build();
        } else if (!ascDesc.equals("ASC") && !ascDesc.equals("DESC")) {
            ServiceLogger.LOGGER.info("Error getting top communities: must sort by ASC or DESC.");
            return Response.status(Status.BAD_REQUEST).entity(new CommunitiesResponseModel(-205)).build();
        }

        try {
            String query = "SELECT * FROM communities WHERE type = 0 AND status = 0 ORDER BY ? ? LIMIT 10 OFFSET ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, orderBy);
            ps.setString(2, ascDesc);
            ps.setInt(3, offset);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                ServiceLogger.LOGGER.info("Found communities matching criteria.");
                ArrayList<CommunityModel> communities = new ArrayList<>();

                do {
                    communities.add(parseCommunity(rs));
                } while (rs.next());

                return Response.status(Status.OK).entity(new CommunitiesListResponseModel(201, communities.toArray(new CommunityModel[0]))).build();

            } else {
                // There's no communities for this query.
                return Response.status(Status.OK).entity(new CommunitiesResponseModel(202)).build();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    // MARK: Helpers

    private static CommunityModel parseCommunity(ResultSet rs) throws SQLException {
        String name = rs.getString("name");
        String desc = rs.getString("description");
        int members = rs.getInt("members");
        int type = rs.getInt("type");
        int status = rs.getInt("status");

        return new CommunityModel(name, desc, members, type, status);

    }
}
