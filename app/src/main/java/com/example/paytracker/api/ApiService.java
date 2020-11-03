package com.example.paytracker.api;

import com.example.paytracker.ResponseData;
import com.example.paytracker.model.EditProfilePojo;
import com.example.paytracker.model.GetAllJobProfilePojo;
import com.example.paytracker.model.JobTitlePojo;
import com.example.paytracker.model.PaymentPojo;
import com.example.paytracker.model.ProvincesPojo;
import com.example.paytracker.model.ViewTaxesPojo;
import com.example.paytracker.model.WorkDatePojo;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface ApiService {


    @GET("PayTracker/user_registration.php")
    Call<ResponseData> userRegistration(
            @Query("name") String name,
            @Query("email") String email,
            @Query("phonenumber") String phonenumber,
            @Query("pwd") String pwd);


    @GET("/PayTracker/user_login.php?")
    Call<ResponseData> userLogin(
            @Query("uname") String uname,
            @Query("pwd") String pwd
    );

    @GET("/PayTracker/adminlogin.php?")
    Call<ResponseData> adminLogin(
            @Query("uname") String uname,
            @Query("pwd") String pwd
    );


    @GET("/PayTracker/forgotpassword.php?")
    Call<ResponseData> forgotpassword
            (

                    @Query("emailid") String emailid
            );

    @GET("/PayTracker/add_provinces.php?")
    Call<ResponseData> add_provinces(
            @Query("provinces_name") String provinces_name
    );
/*
    @GET("/PayTracker/get_provinces.php?")
    Call<List<ProvincesPojo>> get_provinces();*/

    @GET("/PayTracker/getmyjobprofile.php?")
    Call<List<GetAllJobProfilePojo>> getmyjobprofile(@Query("uname") String uname);

    @GET("/PayTracker/getjobtypes_by_uname.php?")
    Call<List<JobTitlePojo>> getjobtypes_by_uname(@Query("uname") String uname);

    @GET("/PayTracker/delete_provinces.php?")
    Call<ResponseData> delete_provinces(
            @Query("id") String id

    );

    @GET("/PayTracker/update_provinces.php?")
    Call<ResponseData> update_provinces(
            @Query("id") String id,
            @Query("provinces_name") String provinces_name,
            @Query("tax") String tax

    );

    @GET("/PayTracker/add_taxes.php?")
    Call<ResponseData> add_taxes(
            @Query("provinces_name") String provinces_name,
            @Query("tax") String tax
    );


    @GET("/PayTracker/myworks.php?")
    Call<List<PaymentPojo>>  myworks(
            @Query("uname") String uname

    );



    @GET("/PayTracker/add_payment_history.php?")
    Call<ResponseData> add_payment(
            @Query("uname") String uname,
            @Query("work_date") String work_date,
            @Query("start_time") String start_time,
            @Query("end_time") String end_time,
            @Query("payment") String payment,
            @Query("payment_deduct") String payment_deduct,
            @Query("company_name") String company_name,
            @Query("tax") String tax,
            @Query("sal_hour") String sal_hour,
            @Query("break_time") String break_time,
            @Query("total_hours") String total_hours
    );


    @GET("/PayTracker/update_payment_history.php?")
    Call<ResponseData> update_payment(
            @Query("uname") String uname,
            @Query("work_date") String work_date,
            @Query("start_time") String start_time,
            @Query("end_time") String end_time,
            @Query("payment") String payment,
            @Query("payment_deduct") String payment_deduct,
            @Query("company_name") String company_name,
            @Query("tax") String tax,
            @Query("sal_hour") String sal_hour,
            @Query("break_time") String break_time,
            @Query("total_hours") String total_hours,
            @Query("id") String id
    );

    @GET("/PayTracker/get_taxes.php?")
    Call<List<ViewTaxesPojo>> get_taxes();

    @GET("/PayTracker/get_daily_work_dates.php?")
    Call<List<WorkDatePojo>> get_daily_work_dates(@Query("uname") String uname);

    @GET("/PayTracker/get_payment_history.php?")
    Call<List<PaymentPojo>> get_reports(@Query("uname") String uname,
                                        @Query("start_date") String start_date,
                                        @Query("end_date") String end_date);

    @GET("/PayTracker/get_search_payment_history.php?")
    Call<List<PaymentPojo>> get_search_reports(@Query("uname") String uname, @Query("start_date") String start_date, @Query("end_date") String end_date, @Query("job_type") String job_type);

    @GET("/PayTracker/delete_taxes.php?")
    Call<ResponseData> delete_taxes(
            @Query("id") String id

    );

    @GET("/PayTracker/update_taxes.php?")
    Call<ResponseData> update_taxes(
            @Query("provinces_name") String provinces_name,
            @Query("tax") String tax,
            @Query("id") String id
    );




    @Multipart
    @POST("PayTracker/user_registration.php?")
    Call<ResponseData> userRegistration(
            @Part MultipartBody.Part file,
            @PartMap Map<String, String> partMap

    );

    @Multipart
    @POST("/PayTracker/user_update_profile.php?")
    Call<ResponseData> user_update_profile(
            @Part MultipartBody.Part file,
            @PartMap Map<String, String> partMap

    );

    @GET("/PayTracker/get_user_profile.php?")
    Call<List<EditProfilePojo>> get_user_profile(
            @Query("uname") String uname
    );

    @GET("/PayTracker/get_provinces.php")
    Call<List<ProvincesPojo>> get_provinces();

    @GET("/PayTracker/add_jobprofile.php?")
    Call<ResponseData> add_jobprofile(
            @Query("companyname") String companyname,
            @Query("jobtitle") String jobtitle,
            @Query("salaryperhour") String salaryperhour,
            @Query("uemail") String uemail,
            @Query("pid") String pid
    );


    @GET("/PayTracker/editjobprofile.php?")
    Call<ResponseData> editjobprofile(
            @Query("companyname") String companyname,
            @Query("jobtitle") String jobtitle,
            @Query("salaryperhour") String salaryperhour,
            @Query("uemail") String uemail,
            @Query("pid") String pid,
            @Query("jid") String jid
    );


}