package edu.uci.ics.chrisr6.service.basic.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ValidateStringRequestModel {
    String input;
    int len;

    public ValidateStringRequestModel() {

    }

    public ValidateStringRequestModel(@JsonProperty(value = "input", required = true) String input,
                                      @JsonProperty(value = "len", required = true) int len) {
        this.input = input;
        this.len = len;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }
}
