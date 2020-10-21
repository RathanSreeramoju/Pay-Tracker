package com.example.paytracker.model;

import com.google.gson.annotations.SerializedName;

public class ViewTaxesPojo {


    @SerializedName("id")
    public String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProvinces_name() {
        return provinces_name;
    }

    public void setProvinces_name(String provinces_name) {
        this.provinces_name = provinces_name;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    @SerializedName("provinces_name")
    public String provinces_name;

    @SerializedName("tax")
    public String tax;
}
