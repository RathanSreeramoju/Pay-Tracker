package com.example.paytracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.paytracker.api.ApiService;
import com.example.paytracker.api.RetroClient;
import com.example.paytracker.model.ProvincesPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditJobActivity extends AppCompatActivity {


    ProgressDialog pd;
    EditText et_sal,et_job_title,et_company_name;
    Button btn_submit;
    private static final String SERVER_PATH = "http://paytracker.ca/";
    private Uri uri;
    Spinner spin_provinces;
    List<ProvincesPojo> a2;
    String[] proviences,ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_job);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Edit Job Profile");
        et_sal=(EditText)findViewById(R.id.et_sal);
        et_job_title=(EditText)findViewById(R.id.et_job_title);
        et_company_name=(EditText)findViewById(R.id.et_company_name);
        spin_provinces=(Spinner)findViewById(R.id.spin_provinces);


        et_company_name.setText(getIntent().getStringExtra("cname"));
        et_job_title.setText(getIntent().getStringExtra("ctitle"));
        et_sal.setText(getIntent().getStringExtra("csalary"));
        // spin_provinces.set(getIntent().getStringExtra("cprovince"));

        btn_submit=(Button)findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_company_name.getText().toString().equals(""))
                {
                    Toast.makeText(EditJobActivity.this, "Enter Company Name", Toast.LENGTH_LONG).show();
                }
                else if(et_job_title.getText().toString().equals(""))
                {
                    Toast.makeText(EditJobActivity.this, "Enter Job Title", Toast.LENGTH_LONG).show();
                }
                else if(et_sal.getText().toString().equals(""))
                {
                    Toast.makeText(EditJobActivity.this, "Enter Salary", Toast.LENGTH_LONG).show();
                }
                else {
                    updateJobProfile();
                    String jid =getIntent().getStringExtra("jid") ;
                    Toast.makeText(EditJobActivity.this, jid, Toast.LENGTH_LONG).show();

                }
            }
        });
        getProvinces();
    }


    private void updateJobProfile() {
        SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        String sal = et_sal.getText().toString();
        String company_name = et_company_name.getText().toString();
        String jobtitle = et_job_title.getText().toString();
        String jid =getIntent().getStringExtra("jid") ;

        pd = new ProgressDialog(EditJobActivity.this);
        pd.setMessage("Loading....");
        pd.show();

        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseData> call = service.editjobprofile(company_name,jobtitle,sal,sharedPreferences.getString("uname","-"),ids[spin_provinces.getSelectedItemPosition()],jid);



        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                pd.dismiss();
                if (response.body().status.equals("true")) {
                    Toast.makeText(EditJobActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(EditJobActivity.this, GetAllJobProfileActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(EditJobActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(EditJobActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getProvinces() {
        ApiService apiService = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<ProvincesPojo>> call = apiService.get_provinces();
        call.enqueue(new Callback<List<ProvincesPojo>>() {
            @Override
            public void onResponse(Call<List<ProvincesPojo>> call, Response<List<ProvincesPojo>> response) {
                a2 = response.body();
                Log.d("TAG", "Response = " + a2);
                proviences = new String[a2.size() + 1];
                ids=new String[a2.size()+1];
                ids[0] = "-1";
                proviences[0] = "Select Provinces";
                for (int i = 0; i < a2.size(); i++) {
                    proviences[i + 1] = a2.get(i).getProvince_name();
                    ids[i + 1] = a2.get(i).getId();

                }
                ArrayAdapter aa = new ArrayAdapter(EditJobActivity.this, android.R.layout.simple_spinner_item, proviences);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin_provinces.setAdapter(aa);
                spin_provinces.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                        if (pos > 0) {
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<ProvincesPojo>> call, Throwable t) {
                Log.d("TAG", "Response = " + t.toString());
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}