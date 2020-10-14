package com.example.paytracker.model;

import com.google.gson.annotations.SerializedName;

public class GetAllJobProfilePojo {


    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getJobtitle() {
        return jobtitle;
    }

    public void setJobtitle(String jobtitle) {
        this.jobtitle = jobtitle;
    }

    public String getSalaryperhour() {
        return salaryperhour;
    }

    public void setSalaryperhour(String salaryperhour) {
        this.salaryperhour = salaryperhour;
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

    public String getUemail() {
        return uemail;
    }

    public void setUemail(String uemail) {
        this.uemail = uemail;
    }

    @SerializedName("companyname")
    private String companyname;

    @SerializedName("jobtitle")
    private String jobtitle;

    @SerializedName("salaryperhour")
    private String salaryperhour;

    @SerializedName("province_name")
    private String province_name;

    @SerializedName("tax_percentage")
    private String tax_percentage;

    @SerializedName("uemail")
    private String uemail;

    @SerializedName("jid")
    private String jid;

    public String getJid() {
        return jid;
    }

    public void setJid(String jid) {
        this.jid = jid;
    }
<<<<<<< HEAD
=======

>>>>>>> 6bf8886d5249bb89df1e9f4d50ece6000ded6da6

}
