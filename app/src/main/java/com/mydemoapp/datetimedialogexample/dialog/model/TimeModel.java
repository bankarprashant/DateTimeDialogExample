package com.mydemoapp.datetimedialogexample.dialog.model;

public class TimeModel {
    public int hour;
    public int minute;
    public int second;
    public boolean isTimeSet;

    @Override
    public String toString() {
        return "TimeModel{" +
                "hour=" + hour +
                ", minute=" + minute +
                ", second=" + second +
                '}';
    }
}
