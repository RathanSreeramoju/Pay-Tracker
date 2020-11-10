package com.example.paytracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.paytracker.api.ApiService;
import com.example.paytracker.api.RetroClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *  Admin can login by email-id and password
 */
public class AdminLoginActivity extends AppCompatActivity {

    EditText et_adminun,et_adminpass;
    Button Btn_adminlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        getSupportActionBar().setTitle("Admin Login");
        et_adminun=(EditText)findViewById(R.id.etadmin_uname);
        et_adminpass=(EditText)findViewById(R.id.etadmin_pwd);


        Btn_adminlogin=(Button)findViewById(R.id.btadmin_signin);

        Btn_adminlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_adminun.getText().toString().equals(""))
                {
                    Toast.makeText(AdminLoginActivity.this, "Enter Admin Email", Toast.LENGTH_LONG).show();
                }
                else if(et_adminpass.getText().toString().equals(""))
                {
                    Toast.makeText(AdminLoginActivity.this, "Enter Admin Password", Toast.LENGTH_LONG).show();
                }
                else
                {
                    adminLoginData();
                }
            }
        });

    }

    ProgressDialog progress;
    public  void adminLoginData() {
        progress= new ProgressDialog(AdminLoginActivity.this);
        progress.setTitle("Loading...");
        progress.show();
        ApiService apiService = RetroClient.getRetrofitInstance().create(ApiService.class);
        // Using ApiService calls through Retrofit Client Instance
        Call<ResponseData> call = apiService.adminLogin(et_adminun.getText().toString(),et_adminpass.getText().toString());
        //Calling web services using web call add_Taxes
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                progress.dismiss();
                
                if (response.body().status.equals("true")) {
                    SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
                    SharedPreferences.Editor et=sharedPreferences.edit();
                    et.putString("uname",et_adminun.getText().toString());
                    et.commit();
                    Toast.makeText(AdminLoginActivity.this, "Login Successfully", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(AdminLoginActivity.this, AdminDashBoardActivity.class));
                    finish();
                } else {
                    Toast.makeText(AdminLoginActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                }
            }


            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(AdminLoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}

