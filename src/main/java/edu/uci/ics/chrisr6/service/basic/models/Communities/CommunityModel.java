package edu.uci.ics.chrisr6.service.basic.models.Communities;

public class CommunityModel {
    public String name;
    public String description;
    public int members;
    public int type;
    public int status;

    public CommunityModel() {
    }

    public CommunityModel(String name, String description, int members, int type, int status) {
        this.name = name;
        this.description = description;
        this.members = members;
        this.type = type;
        this.status = status;
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

    public int getMembers() {
        return members;
    }

    public void setMembers(int members) {
        this.members = members;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
