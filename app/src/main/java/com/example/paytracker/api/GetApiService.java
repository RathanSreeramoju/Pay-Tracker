package com.example.paytracker.api;

import com.example.paytracker.model.ResponseData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetApiService {

    @GET("/user_login.php?")
    Call<ResponseData> userLogin(
            @Query("uname") String uname,
            @Query("pwd") String pwd
    );
}
