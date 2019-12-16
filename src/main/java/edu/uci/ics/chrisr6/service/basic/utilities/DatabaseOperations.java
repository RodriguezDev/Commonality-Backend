package edu.uci.ics.chrisr6.service.basic.utilities;

import edu.uci.ics.chrisr6.service.basic.App;
import edu.uci.ics.chrisr6.service.basic.logger.ServiceLogger;
import edu.uci.ics.chrisr6.service.basic.utilities.security.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseOperations {
    // Checks if an email is in use.
    public static boolean emailInUse(String email) {
        try {
            Connection connection = App.getCon();

            String query = "SELECT count(*) AS c FROM users WHERE email = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();
            rs.next();

            // Return if the count is > 0.
            return rs.getInt("c") > 0;

        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Inserts a row into the session table and generates a token
     * @param email: email tied to session
     * @return a token
     */
    public static String createSession(String email) {
        try {
            Connection connection = App.getCon();

            Session session = Session.createSession(email);

            String sessionInsertion = "INSERT INTO sessions (email, sessionID, status, dateCreated) " +
                    "VALUES (? ,?, ?, ?)";

            ServiceLogger.LOGGER.info("New session generated: " + session.getSessionID().toString().length());

            PreparedStatement sessionStatement = connection.prepareStatement(sessionInsertion);
            sessionStatement.setString(1, email);
            sessionStatement.setString(2, session.getSessionID().toString());
            sessionStatement.setInt(3, Session.ACTIVE);
            sessionStatement.setTimestamp(4, session.getTimeCreated());
            sessionStatement.execute();

            ServiceLogger.LOGGER.info("New session generated: " + session.getSessionID().toString());

            return session.getSessionID().toString();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
