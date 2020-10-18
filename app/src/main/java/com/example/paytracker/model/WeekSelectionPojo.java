package com.example.paytracker.model;

public class WeekSelectionPojo {
    private String week_no;
    private String week_display_name;
    public WeekSelectionPojo(String week_no,String week_display_name){
        this.week_no = week_no;
        this.week_display_name = week_display_name;
    }

    public String getWeek_no() {
        return week_no;
    }

    public void setWeek_no(String week_no) {
        this.week_no = week_no;
    }

    public String getWeek_display_name() {
        return week_display_name;
    }

    public void setWeek_display_name(String week_display_name) {
        this.week_display_name = week_display_name;
    }
}
