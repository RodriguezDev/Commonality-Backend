package edu.uci.ics.chrisr6.service.basic.utilities;

import edu.uci.ics.chrisr6.service.basic.models.GeneralResponseModel;

import javax.ws.rs.core.Response.Status;

public class CredentialValidation {
    public CredentialValidation() {}

    public int isValidEmail(String email) {
        if(!emailHasValidFormat(email))
            return -101;

        if (!emailHasValidLength(email))
            return -100;

        return 0;
    }

    public int isValidPassword(char[] password) {
        if (!passwordNotEmpty(password))
            return -102;

        if (!passwordHasValidLength(password))
            return -102;

        if (!passwordHasValidCharacters(password))
            return -103;

        return 0;
    }

    public Status getStatus(GeneralResponseModel responseModel) {
        int resultCode = responseModel.getResultCode();
        switch (resultCode) {
            case -103:
            case -102:
            case -101:
            case -100:
            case -3:
            case -2:
                return Status.BAD_REQUEST;
            default:
                return Status.OK;
        }
    }

    private boolean emailHasValidLength(String email) {
        return email.length() > 0 && email.length() <= 50;
    }

    private boolean emailHasValidFormat(String email) {
        if (email.contains("@") && email.contains("."))
            return email.indexOf('@') < email.lastIndexOf('.') && email.indexOf('@') != 0;
        return false;
    }

    private boolean passwordNotEmpty(char[] password) {
        return password != null && password.length > 0;
    }

    private boolean passwordHasValidLength(char[] password) {
        return password.length >= 7 && password.length <= 16;
    }

    private boolean passwordHasValidCharacters(char[] password) {

        boolean upper = false;
        boolean lower = false;
        boolean digit = false;
        boolean special = false;

        for (char c: password) {
            if (Character.isDigit(c)) {
                digit = true;
            } else if (Character.isUpperCase(c)) {
                upper = true;
            } else if (Character.isLowerCase(c)) {
                lower = true;
            } else {
                special = true;
            }
        }

        return upper && lower && digit && special;
    }
}
