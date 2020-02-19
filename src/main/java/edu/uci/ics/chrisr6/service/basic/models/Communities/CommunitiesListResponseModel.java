package edu.uci.ics.chrisr6.service.basic.models.Communities;

public class CommunitiesListResponseModel extends CommunitiesResponseModel {
    public CommunityModel[] communities;

    public CommunitiesListResponseModel(int status, CommunityModel[] communities) {
        super(status);
        this.communities = communities;
    }
}
