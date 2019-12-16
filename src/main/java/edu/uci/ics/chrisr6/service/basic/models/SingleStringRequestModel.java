package edu.uci.ics.chrisr6.service.basic.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SingleStringRequestModel {
    String field;

    public SingleStringRequestModel(@JsonProperty(value = "field", required = true) String field) {
        this.field = field;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
