package com.example.paytracker.model;

import com.google.gson.annotations.SerializedName;

public class PaymentPojo {
    @SerializedName("id")
    private String id;

    @SerializedName("uname")
    private String uname;


    @SerializedName("total_hours")
    private String total_hours;

    @SerializedName("company_name")
    private String company_name;

    @SerializedName("tax")
    private String tax;

    @SerializedName("sal_hour")
    private String sal_hour;

    @SerializedName("break_time")
    private String break_time;

    @SerializedName("payment")
    private String payment;

    @SerializedName("payment_deduct")
    private String payment_deduct;

    @SerializedName("start_time")
    private String start_time;

    @SerializedName("end_time")
    private String end_time;

    @SerializedName("work_date")
    private String work_date;

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getPayment_deduct() {
        return payment_deduct;
    }

    public void setPayment_deduct(String payment_deduct) {
        this.payment_deduct = payment_deduct;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getWork_date() {
        return work_date;
    }

    public void setWork_date(String work_date) {
        this.work_date = work_date;
    }

    public String getTotal_hours() {
        return total_hours;
    }

    public void setTotal_hours(String total_hours) {
        this.total_hours = total_hours;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getSal_hour() {
        return sal_hour;
    }

    public void setSal_hour(String sal_hour) {
        this.sal_hour = sal_hour;
    }

    public String getBreak_time() {
        return break_time;
    }

    public void setBreak_time(String break_time) {
        this.break_time = break_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }
}
