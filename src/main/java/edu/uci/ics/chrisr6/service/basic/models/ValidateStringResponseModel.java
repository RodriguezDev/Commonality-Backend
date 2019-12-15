package edu.uci.ics.chrisr6.service.basic.models;

import javax.ws.rs.core.Response.Status;

public class ValidateStringResponseModel {
    int resultCode;
    String message;

    public ValidateStringResponseModel() {

    }

    public ValidateStringResponseModel(int resultCode) {
        this.resultCode = resultCode;
        switch (resultCode) {
            case 0:
                message = "String length matched.";
                break;
            case 1:
                message = "String length does not match.";
                break;
            case 2:
                message = "Invalid request format.";
                break;
            case 3:
                message = "Input string is empty.";
                break;
            case 4:
                message = "Input string is too long.";
                break;
            case 5:
                message = "Length value is negative.";
                break;
        }
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

/*

execute(); -> bool
executeUpdate(); -> returns an int resulting in number of records updated
executeQuery(); -> result set

 */
