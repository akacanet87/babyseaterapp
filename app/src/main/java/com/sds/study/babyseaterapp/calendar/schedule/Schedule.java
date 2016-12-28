package com.sds.study.babyseaterapp.calendar.schedule;

/**
 * Created by student on 2016-12-13.
 */

public class Schedule {
    private int schedule_id;
    //private int baby_id;
    //private int mom_id;
    private String sc_year;
    private String sc_month;
    private String sc_date;    //몇일
    private String sc_hour;
    private String sc_minute;
    //private String sc_content;


    public int getSchedule_id() {
        return schedule_id;
    }

    public void setSchedule_id(int schedule_id) {
        this.schedule_id = schedule_id;
    }

    public String getSc_year() {
        return sc_year;
    }

    public void setSc_year(String sc_year) {
        this.sc_year = sc_year;
    }

    public String getSc_month() {
        return sc_month;
    }

    public void setSc_month(String sc_month) {
        this.sc_month = sc_month;
    }

    public String getSc_date() {
        return sc_date;
    }

    public void setSc_date(String sc_date) {
        this.sc_date = sc_date;
    }

    public String getSc_hour() {
        return sc_hour;
    }

    public void setSc_hour(String sc_hour) {
        this.sc_hour = sc_hour;
    }

    public String getSc_minute() {
        return sc_minute;
    }

    public void setSc_minute(String sc_minute) {
        this.sc_minute = sc_minute;
    }
}
