package edu.uci.ics.chrisr6.service.basic.core;

import edu.uci.ics.chrisr6.service.basic.App;
import edu.uci.ics.chrisr6.service.basic.logger.ServiceLogger;
import edu.uci.ics.chrisr6.service.basic.models.*;
import edu.uci.ics.chrisr6.service.basic.models.Users.CredentialRequestModel;
import edu.uci.ics.chrisr6.service.basic.utilities.CredentialValidation;
import edu.uci.ics.chrisr6.service.basic.utilities.DatabaseOperations;
import edu.uci.ics.chrisr6.service.basic.utilities.security.Crypto;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Users {
    /**
     * Register a user if they meet all criteria.
     * @param requestModel: username + password
     * @return response
     */
    public static Response registerUser(CredentialRequestModel requestModel) {
        CredentialValidation validation = new CredentialValidation();
        Connection connection = App.getCon();

        // Check that the email is in the valid format.
        int emailValidity = validation.isValidEmail(requestModel.getEmail());
        if (emailValidity != 0) {
            return Response.status(Status.BAD_REQUEST).entity(new GeneralResponseModel(emailValidity)).build();
        }

        // Check that the password is valid.
        int passwordValidity = validation.isValidPassword(requestModel.getPassword());
        if (passwordValidity != 0) {
            return Response.status(Status.BAD_REQUEST).entity(new GeneralResponseModel(passwordValidity)).build();
        }

        try {

            // If the email is in use, fail.
            if (DatabaseOperations.emailInUse(requestModel.getEmail())) {
                return Response.status(Status.BAD_REQUEST).entity(new GeneralResponseModel(-104)).build();
            }

            // Otherwise, register the user.
            byte[] salt = Crypto.genSalt();

            String hashedPass = Hex.encodeHexString(Crypto.hashPassword(requestModel.getPassword(), salt, Crypto.ITERATIONS, Crypto.KEY_LENGTH));
            String encodedSalt = Hex.encodeHexString(salt);

            String query = "INSERT INTO users (email, name, handle, salt, password, plevel, status) VALUES (?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1, requestModel.getEmail());
            ps.setString(2, null);
            ps.setString(3, null);
            ps.setString(4, encodedSalt);
            ps.setString(5, hashedPass);
            ps.setInt(6,  1);
            ps.setInt(7, 1);

            ps.execute();
            ServiceLogger.LOGGER.info("Success adding " + requestModel.getEmail() + " to database");

            requestModel.setPassword(null);

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }

        return Response.status(Status.OK).entity(new GeneralResponseModel(110)).build();
    }

    /**
     * Attempt to log in a user.
     * @param requestModel: username + password
     * @return response with token, if appropriate.
     */
    public static Response loginUser(CredentialRequestModel requestModel) {

        // Check if there's an account with the email.
        if (!DatabaseOperations.emailInUse(requestModel.getEmail())) {
            return Response.status(Status.BAD_REQUEST).entity(new GeneralResponseModel(-105)).build();
        }

        try {
            Connection connection = App.getCon();
            String query = "SELECT * FROM users WHERE email = ?";

            // Get the stored salt and hashed password.
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, requestModel.getEmail());

            ResultSet rs = ps.executeQuery();
            rs.next();

            String dbSalt = rs.getString("salt");
            String dbPass = rs.getString("password");

            byte[] hashedPass2 = Crypto.hashPassword(requestModel.getPassword(), Hex.decodeHex(dbSalt), Crypto.ITERATIONS, Crypto.KEY_LENGTH);
            String hashedPassEntered = Hex.encodeHexString(hashedPass2);

            requestModel.setPassword(null);

            // Check if the two are equal.
            if (dbPass.equals(hashedPassEntered)) {
                // The login was successful. Return a token.
                String token = DatabaseOperations.createSession(requestModel.getEmail());
                return Response.status(Status.OK).entity(new SessionResponseModel(120, token)).build();
            }

            return Response.status(Status.BAD_REQUEST).entity(new GeneralResponseModel(-106)).build();

        } catch (SQLException | DecoderException e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
