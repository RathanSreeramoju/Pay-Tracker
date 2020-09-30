package com.example.paytracker.api;

import com.example.paytracker.ResponseData;
import com.example.paytracker.model.EditProfilePojo;
import com.example.paytracker.model.PaymentPojo;
import com.example.paytracker.model.ProvincesPojo;

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


    @GET("/PayTracker/forgotpassword.php?")
    Call<ResponseData> forgotpassword
            (

                    @Query("emailid") String emailid
            );



    @GET("/PayTracker/add_payment_history.php?")
    Call<ResponseData> add_payment(
            @Query("uname") String uname,
            @Query("work_date") String work_date,
            @Query("start_time") String start_time,
            @Query("end_time") String end_time,
            @Query("payment") String payment,
            @Query("payment_deduct") String payment_deduct
    );


    @GET("/PayTracker/get_payment_history.php?")
    Call<List<PaymentPojo>> get_reports(@Query("uname") String uname, @Query("start_date") String start_date, @Query("end_date") String end_date);



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
}
