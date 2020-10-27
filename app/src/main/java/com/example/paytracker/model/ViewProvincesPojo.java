package com.example.paytracker.model;

import com.google.gson.annotations.SerializedName;

public class ViewProvincesPojo {
    @SerializedName("id")
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

    public ViewProvincesPojo(String id, String provinces_name) {
        this.id = id;
        this.provinces_name = provinces_name;
    }

    @SerializedName("id")
    public String id;


    @SerializedName("provinces_name")
    public String provinces_name;


}
