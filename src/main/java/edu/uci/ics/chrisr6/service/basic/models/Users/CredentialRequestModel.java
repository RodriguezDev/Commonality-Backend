package edu.uci.ics.chrisr6.service.basic.models.Users;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CredentialRequestModel {
    String email;
    char[] password;

    public CredentialRequestModel() {

    }

    public CredentialRequestModel(@JsonProperty(value = "email", required = true) String email,
                                  @JsonProperty(value = "password", required = true) char[] password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }
}
