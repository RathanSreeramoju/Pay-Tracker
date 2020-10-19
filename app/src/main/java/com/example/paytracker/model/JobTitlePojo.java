package com.example.paytracker.model;

import com.google.gson.annotations.SerializedName;

public class JobTitlePojo {

    @SerializedName("company_name")
    private String company_name;


    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }
}
