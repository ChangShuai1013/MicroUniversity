package com.example.changshuai.microuniversity.entity;

/**
 * Created by apple on 2017/5/6.
 */

public class MeReportBean {
    private String objectId;
    private String kind;
    private String time;
    private String thing;

    public MeReportBean() {}

    public MeReportBean(String objectId, String kind, String time, String thing) {
        this.objectId = objectId;
        this.kind = kind;
        this.time = time;
        this.thing = thing;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getThing() {
        return thing;
    }

    public void setThing(String thing) {
        this.thing = thing;
    }
}
