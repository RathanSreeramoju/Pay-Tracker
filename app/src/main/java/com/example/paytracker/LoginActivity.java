package com.example.paytracker;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.paytracker.api.ApiService;
import com.example.paytracker.api.RetroClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * User can login through their username and password
 */

public class   LoginActivity extends AppCompatActivity {
    Button bt_signin;
    EditText et_uname, et_pwd;
    TextView tv_forget_pass, bt_signup,tv_admin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        bt_signup = (TextView) findViewById(R.id.bt_signup);
        et_uname = (EditText) findViewById(R.id.et_uname);
        et_pwd = (EditText) findViewById(R.id.et_pwd);

        tv_admin=(TextView)findViewById(R.id.tv_admin);
        tv_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,AdminLoginActivity.class);
                startActivity(intent);
            }
        });

        bt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,RegistrationActivity.class);
                startActivity(intent);

            }
        });


        bt_signin = (Button) findViewById(R.id.bt_signin);
        bt_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);*/
                if (et_uname.getText().toString().equals("")) {
                    Toast.makeText(LoginActivity.this, "Enter Email", Toast.LENGTH_LONG).show();
                } else if (et_pwd.getText().toString().equals("")) {
                    Toast.makeText(LoginActivity.this, "Enter Password", Toast.LENGTH_LONG).show();
                } else {

                    loginData();
                }
            }
        });
        tv_forget_pass = (TextView) findViewById(R.id.tv_forget_pass);
        tv_forget_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
                /* Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);*/

            }
        });
    }

    ProgressDialog pd;
    public  void loginData() {
        pd= new ProgressDialog(LoginActivity.this);
        pd.setTitle("Loading...");
        pd.show();
        ApiService apiService = RetroClient.getRetrofitInstance().create(ApiService.class);
        // Using ApiService calls through Retrofit Client Instance
        Call<ResponseData> call = apiService.userLogin(et_uname.getText().toString(),et_pwd.getText().toString());
        //Calling web services using web call Add_login
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                pd.dismiss();
                if (response.body().status.equals("true")) {
                    SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
                    SharedPreferences.Editor et=sharedPreferences.edit();
                    et.putString( "uname",et_uname.getText().toString());
                    et.commit();
                    Toast.makeText(LoginActivity.this,"Login Success", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LoginActivity.this, UserDashboardActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}


