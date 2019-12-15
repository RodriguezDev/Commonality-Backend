package edu.uci.ics.chrisr6.service.basic.core;

import edu.uci.ics.chrisr6.service.basic.App;
import edu.uci.ics.chrisr6.service.basic.logger.ServiceLogger;
import edu.uci.ics.chrisr6.service.basic.models.ValidateStringRequestModel;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseOperations {
    public DatabaseOperations() {

    }

    public static boolean insertStudentsToDb(ValidateStringRequestModel requestModel) {
        /*
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
            ServiceLogger.LOGGER.info("Success inserting: " + ps.toString());
            return true;
        } catch (SQLException e) {
            ServiceLogger.LOGGER.warning("Unable to insert string: " + requestModel.getInput());
            e.printStackTrace();
        }

        */
        return false;
    }
}
