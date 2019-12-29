package edu.uci.ics.chrisr6.service.basic.models.Communities;

import edu.uci.ics.chrisr6.service.basic.models.GeneralResponseModel;

public class CommunitiesResponseModel extends GeneralResponseModel {

    public CommunitiesResponseModel(int resultCode) {
        super();
        this.resultCode = resultCode;

        switch (resultCode) {
            case -202:
                this.message = "Community name is taken.";
                break;
            case -201:
                this.message = "Community name length is invalid.";
                break;
            case 200:
                this.message = "Community created successfully.";
                break;
        }
    }
}
