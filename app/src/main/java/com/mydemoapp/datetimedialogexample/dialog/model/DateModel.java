package com.mydemoapp.datetimedialogexample.dialog.model;

public class DateModel extends TimeModel {
    public int year;
    public int month;
    public int day;
    public boolean isDateSet;

    @Override
    public String toString() {
        return "DateModel{" +
                "hour=" + hour +
                ", year=" + year +
                ", minute=" + minute +
                ", month=" + month +
                ", second=" + second +
                ", day=" + day +
                '}';
    }
}
