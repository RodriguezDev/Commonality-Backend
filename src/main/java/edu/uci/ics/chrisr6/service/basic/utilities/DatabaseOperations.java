package edu.uci.ics.chrisr6.service.basic.utilities;

import edu.uci.ics.chrisr6.service.basic.App;
import edu.uci.ics.chrisr6.service.basic.logger.ServiceLogger;
import edu.uci.ics.chrisr6.service.basic.utilities.security.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseOperations {

    /**
     * Check if an email is in use.
     * @param email: to check
     * @return true, if in use
     */
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

    /**
     * Verify that a given session + email are active.
     * @param email: email
     * @param sessionID: session
     * @return if session active
     */
    public static boolean checkSessionValid(String email, String sessionID) {

        Connection connection = App.getCon();

        try {
            String query = "SELECT COUNT(*) as c FROM sessions WHERE email = ? AND sessionID = ? AND status = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, sessionID);
            ps.setInt(3, Session.ACTIVE);

            ResultSet rs = ps.executeQuery();
            rs.next();

            return rs.getInt("c") > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Invalidates sessions. Will invalidate all for an email if no sessionID is provided.
     * @param email: mandatory
     * @param sessionID: optional
     */
    public static void invalidateSessions(String email, String sessionID) {

        try {
            Connection connection = App.getCon();

            String query;
            PreparedStatement ps;

            if (sessionID != null) {
                query = "UPDATE sessions SET status = ? WHERE email = ? AND sessionID = ?";
                ps = connection.prepareStatement(query);
                ps.setInt(1, Session.CLOSED);
                ps.setString(2, email);
                ps.setString(3, sessionID);
            } else {
                query = "UPDATE sessions SET status = ? WHERE email = ?";
                ps = connection.prepareStatement(query);
                ps.setInt(1, Session.CLOSED);
                ps.setString(2, email);
            }

            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check if community name is in use.
     * @param name: to check
     * @return true, if in use
     */
    public static boolean communityNameInUse(String name) {
        try {
            Connection connection = App.getCon();

            String query = "SELECT count(*) AS c FROM communities WHERE name = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();
            rs.next();

            // Return if the count is > 0.
            return rs.getInt("c") > 0;

        } catch (SQLException e) {
            return false;
        }
    }
}
