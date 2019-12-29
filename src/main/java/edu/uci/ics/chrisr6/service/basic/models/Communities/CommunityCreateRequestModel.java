package edu.uci.ics.chrisr6.service.basic.models.Communities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CommunityCreateRequestModel {
    String name;
    String description;

    public CommunityCreateRequestModel() {

    }

    public CommunityCreateRequestModel(@JsonProperty(value = "name", required = true) String name,
                                       @JsonProperty(value = "description", required = true) String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
