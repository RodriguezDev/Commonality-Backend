package edu.uci.ics.chrisr6.service.basic.models.Communities;

import edu.uci.ics.chrisr6.service.basic.models.GeneralResponseModel;

public class CommunitiesResponseModel extends GeneralResponseModel {

    public CommunitiesResponseModel(int resultCode) {
        super();
        this.resultCode = resultCode;

        switch (resultCode) {
            case -205:
                this.message = "Must sort by ASC or DESC";
                break;
            case -204:
                this.message = "Illegal order by condition";
                break;
            case -203:
                this.message = "Offset must be >= 0";
                break;
            case -202:
                this.message = "Community name is taken.";
                break;
            case -201:
                this.message = "Community name length is invalid.";
                break;
            case 200:
                this.message = "Community created successfully.";
                break;
            case 201:
                this.message = "Communities successfully retrieved.";
                break;
            case 202:
                this.message = "No communities found.";
                break;
        }
    }
}
