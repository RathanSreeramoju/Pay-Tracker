package com.example.paytracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paytracker.api.ApiService;
import com.example.paytracker.api.RetroClient;
import com.example.paytracker.model.ProvincesPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddJobProfileActivity extends AppCompatActivity {
    ProgressDialog pd;
    TextView tv_dob;
    int mYear,mMonth,mDay;
    String DAY,MONTH,YEAR;
    LinearLayout ll_reg_form,ll_sal_per_hour,ll_job_title,ll_company_name,ll_img_upload,ll_provinces;
    Button btn_sal_prev,btn_sal_next,job_previous,job_next,btn_company_prev,btn_company_next,btn_submit_all;
    EditText et_first_name,et_last_name,et_email,et_username,et_password,et_sal,et_job_title,et_company_name;
    Button btn_submit;
    private static final String SERVER_PATH = "http://paytracker.ca/";
    private Uri uri;
    Spinner spin_provinces;
    List<ProvincesPojo> a2;
    String[] proviences,ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job_profile);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Job Profile");
        et_sal=(EditText)findViewById(R.id.et_sal);
        et_job_title=(EditText)findViewById(R.id.et_job_title);
        et_company_name=(EditText)findViewById(R.id.et_company_name);
        spin_provinces=(Spinner)findViewById(R.id.spin_provinces);

        btn_submit=(Button)findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_company_name.getText().toString().equals(""))
                {
                    Toast.makeText(AddJobProfileActivity.this, "Enter Company Name", Toast.LENGTH_LONG).show();
                }
                else if(et_job_title.getText().toString().equals(""))
                {
                    Toast.makeText(AddJobProfileActivity.this, "Enter Job Title", Toast.LENGTH_LONG).show();
                }
                else if(et_sal.getText().toString().equals(""))
                {
                    Toast.makeText(AddJobProfileActivity.this, "Enter Salary", Toast.LENGTH_LONG).show();
                }
                else {
                    addJobProfile();
                }
            }
        });
        getProvinces();
    }

    private void getProvinces() {
    }

    private void addJobProfile() {

        SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        String sal = et_sal.getText().toString();
        String company_name = et_company_name.getText().toString();
        String jobtitle = et_job_title.getText().toString();
        // String email = et_email.getText().toString();

        pd = new ProgressDialog(AddJobProfileActivity.this);
        pd.setMessage("Loading....");
        pd.show();

        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseData> call = service.add_jobprofile(company_name,jobtitle,sal,sharedPreferences.getString("uname","-"),ids[spin_provinces.getSelectedItemPosition()]);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                pd.dismiss();
                if (response.body().status.equals("true")) {
                    Toast.makeText(AddJobProfileActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(AddJobProfileActivity.this, UserDashboardActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(AddJobProfileActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(AddJobProfileActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}