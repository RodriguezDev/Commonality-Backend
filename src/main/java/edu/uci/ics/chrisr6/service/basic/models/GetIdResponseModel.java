package edu.uci.ics.chrisr6.service.basic.models;

public class GetIdResponseModel {
    int resultCode;
    String message;
    Record record;

    public GetIdResponseModel() {

    }

    public GetIdResponseModel(int resultCode, String message, int id, String sentence, int len) {
        this.resultCode = resultCode;
        this.message = message;
        this.record = new Record(id, sentence, len);
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

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }
}

class Record {
    int id;
    String sentence;
    int len;

    public Record() {

    }

    public Record(int id, String sentence, int len) {
        this.id = id;
        this.sentence = sentence;
        this.len = len;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }
}
