package edu.uci.ics.chrisr6.service.basic.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GeneralResponseModel {
    @JsonProperty(required = true)
    int resultCode;
    @JsonProperty(required = true)
    String message;

    public GeneralResponseModel() {

    }

    public GeneralResponseModel(int resultCode) {
        this.resultCode = resultCode;
        switch (resultCode) {
            case -109:
                this.message = "Name length is invalid.";
                break;
            case -108:
                this.message = "Handle is taken.";
                break;
            case -107:
                this.message = "Handle length is invalid.";
                break;
            case -106:
                this.message = "Password is incorrect.";
                break;
            case -105:
                this.message = "No account exists with email.";
                break;
            case -104:
                this.message = "Email already in use.";
                break;
            case -103:
                this.message = "Password does not meet character requirements.";
                break;
            case -102:
                this.message = "Password has invalid length (cannot be empty/null).";
                break;
            case -101:
                this.message = "Email address has invalid format.";
                break;
            case -100:
                this.message = "Email address has invalid length.";
                break;
            case -3:
                this.message = "JSON Parse Exception.";
                break;
            case -2:
                this.message = "JSON Mapping Exception.";
                break;
            case 110:
                this.message = "User registered successfully.";
                break;
            case 120:
                this.message = "User logged in successfully.";
                break;
            case 130:
                this.message = "Session is active.";
                break;
            case 131:
                this.message = "Session is expired.";
                break;
            case 132:
                this.message = "Session is closed.";
                break;
            case 133:
                this.message = "Session is revoked.";
                break;
            case 134:
                this.message = "Session not found.";
                break;
            case 140:
                this.message = "User has sufficient privilege level.";
                break;
            case 141:
                this.message = "User has insufficient privilege level.";
                break;
            case 150:
                this.message = "User handle updated successfully.";
                break;
            case 160:
                this.message = "User name updated successfully.";
                break;
            case 170:
                this.message = "User logged out successfully.";
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

