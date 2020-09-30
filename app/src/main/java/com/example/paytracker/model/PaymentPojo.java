package com.example.paytracker.model;

import com.google.gson.annotations.SerializedName;

public class PaymentPojo {

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
}
