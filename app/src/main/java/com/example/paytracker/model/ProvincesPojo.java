package com.example.paytracker.model;

import com.google.gson.annotations.SerializedName;

public class ProvincesPojo {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProvince_name() {
        return province_name;
    }

    public void setProvince_name(String province_name) {
        this.province_name = province_name;
    }

    public String getTax_percentage() {
        return tax_percentage;
    }

    public void setTax_percentage(String tax_percentage) {
        this.tax_percentage = tax_percentage;
    }

    @SerializedName("id")
    private String id;

    @SerializedName("province_name")
    private String province_name;

    @SerializedName("tax_percentage")
    private String tax_percentage;
}
