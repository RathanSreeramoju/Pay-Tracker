package com.example.paytracker.model;
import com.google.gson.annotations.SerializedName;


public class EditProfilePojo {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhno() {
        return phno;
    }

    public void setPhno(String phno) {
        this.phno = phno;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getSalary_per_hour() {
        return salary_per_hour;
    }

    public void setSalary_per_hour(String salary_per_hour) {
        this.salary_per_hour = salary_per_hour;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("phno")
    private String phno;

    @SerializedName("username")
    private String username;

    @SerializedName("pwd")
    private String pwd;

    @SerializedName("salary_per_hour")
    private String salary_per_hour;

    @SerializedName("company_name")
    private String company_name;

    @SerializedName("img_url")
    private String img_url;


}
