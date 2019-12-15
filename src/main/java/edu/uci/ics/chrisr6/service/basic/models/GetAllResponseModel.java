package edu.uci.ics.chrisr6.service.basic.models;

public class GetAllResponseModel {
    int resultCode;
    String message;
    int numRecords;

    public GetAllResponseModel() {

    }

    public GetAllResponseModel(int resultCode, String message, int numRecords) {
        this.resultCode = resultCode;
        this.message = message;
        this.numRecords = numRecords;
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

    public int getNumRecords() {
        return numRecords;
    }

    public void setNumRecords(int numRecords) {
        this.numRecords = numRecords;
    }
}
