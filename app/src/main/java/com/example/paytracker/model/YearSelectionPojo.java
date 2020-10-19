package com.example.paytracker.model;

public class YearSelectionPojo {
    private String year_no;
    private String year_display_name;

    public YearSelectionPojo(String year_no,String year_display_name){
        this.year_no =year_no;
        this.year_display_name = year_display_name;
    }

    public String getYear_no() {
        return year_no;
    }

    public void setYear_no(String year_no) {
        this.year_no = year_no;
    }

    public String getYear_display_name() {
        return year_display_name;
    }

    public void setYear_display_name(String year_display_name) {
        this.year_display_name = year_display_name;
    }
}
