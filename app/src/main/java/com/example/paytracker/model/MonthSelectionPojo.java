package com.example.paytracker.model;

public class MonthSelectionPojo {
    private String month_no;
    private String month_display_name;

    public MonthSelectionPojo(String month_no, String month_display_name) {
        this.month_no = month_no;
        this.month_display_name = month_display_name;
    }

    public String getMonth_no() {
        return month_no;
    }

    public void setMonth_no(String month_no) {
        this.month_no = month_no;
    }

    public String getMonth_display_name() {
        return month_display_name;
    }

    public void setMonth_display_name(String month_display_name) {
        this.month_display_name = month_display_name;
    }
}
