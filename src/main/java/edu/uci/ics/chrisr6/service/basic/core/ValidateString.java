package edu.uci.ics.chrisr6.service.basic.core;

import edu.uci.ics.chrisr6.service.basic.models.ValidateStringResponseModel;
import edu.uci.ics.chrisr6.service.basic.models.ValidateStringRequestModel;

public class ValidateString {



    public ValidateStringResponseModel attemptValidation(ValidateStringRequestModel requestModel) {
        int stringLen = requestModel.getInput().split("\\w+").length;

        if (stringLen == requestModel.getLen()) {
            // TODO: insert into database.
            return new ValidateStringResponseModel(0);
        } else if (requestModel.getInput() == null) {
            return new ValidateStringResponseModel(3);
        } else if (requestModel.getInput().length() > 512) {
            return new ValidateStringResponseModel(4);
        } else if (requestModel.getLen() < 0) {
            return new ValidateStringResponseModel(5);
        } else {
            return new ValidateStringResponseModel(1);
        }
    }
}
