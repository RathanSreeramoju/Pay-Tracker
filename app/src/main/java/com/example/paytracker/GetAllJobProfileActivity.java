package com.example.paytracker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.paytracker.adapter.GetAllJobProfileAdapter;
import com.example.paytracker.api.ApiService;
import com.example.paytracker.api.RetroClient;
import com.example.paytracker.model.GetAllJobProfilePojo;
import com.example.paytracker.model.JobTitlePojo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetAllJobProfileActivity extends AppCompatActivity {
    ListView list_view;
    List<GetAllJobProfilePojo> getAllJobProfilePojo;
    Button btn_add_job;
    SharedPreferences sharedPreferences;
    String session;
    ProgressDialog progressDialog;
    Spinner spin_jobs;
    GetAllJobProfileAdapter getAllJobProfilesAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_all_job_profile);

        getSupportActionBar().setTitle("Job Profiles");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        spin_jobs = (Spinner)findViewById(R.id.spin_jobs);



        sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        session = sharedPreferences.getString("uname", "def-val");
        //Toast.makeText(this, ""+session, Toast.LENGTH_SHORT).show();

        btn_add_job=(Button)findViewById(R.id.btn_add_job);
        btn_add_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GetAllJobProfileActivity.this,AddJobProfileActivity.class));
                finish();
            }
        });

        list_view=(ListView)findViewById(R.id.list_view);
        getAllJobProfilePojo= new ArrayList<>();
        getJobs();
        serverData();

    }

    public void serverData(){
        progressDialog = new ProgressDialog(GetAllJobProfileActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<GetAllJobProfilePojo>> call = service.getmyjobprofile(session);

        call.enqueue(new Callback<List<GetAllJobProfilePojo>>() {
            @Override
            public void onResponse(Call<List<GetAllJobProfilePojo>> call, Response<List<GetAllJobProfilePojo>> response) {
                //Toast.makeText(GetAllJobProfileActivity.this, ""+response.body().size(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(GetAllJobProfileActivity.this,"No data found",Toast.LENGTH_SHORT).show();
                }else {
                    getAllJobProfilePojo = response.body();
                    //list_view.setAdapter(new GetAllJobProfilesAdapter(getAllJobProfilePojo, GetAllJobProfileActivity.this));
                    getAllJobProfilesAdapter=new GetAllJobProfileAdapter(getAllJobProfilePojo, GetAllJobProfileActivity.this);
                    list_view.setAdapter(getAllJobProfilesAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<GetAllJobProfilePojo>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(GetAllJobProfileActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    List<JobTitlePojo> array_jobs;
    String myjobs[];
    private void getJobs() {
        ApiService apiService = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<JobTitlePojo>> call = apiService.getjobtypes_by_uname(session);
        call.enqueue(new Callback<List<JobTitlePojo>>() {
            @Override
            public void onResponse(Call<List<JobTitlePojo>> call, Response<List<JobTitlePojo>> response) {
                array_jobs = response.body();
                if(array_jobs!=null){
                    if(array_jobs.size()>0) {
                        myjobs = new String[array_jobs.size()+1];
                        myjobs[0] = "Select Job";
                        Toast.makeText(getApplicationContext(),""+array_jobs.size(),Toast.LENGTH_SHORT).show();
                    }
                }

                for (int i = 0; i < array_jobs.size(); i++) {
                    myjobs[i + 1] = array_jobs.get(i).getCompany_name();
                }
                ArrayAdapter aa = new ArrayAdapter(GetAllJobProfileActivity.this, android.R.layout.simple_spinner_item, myjobs);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin_jobs.setAdapter(aa);

                spin_jobs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                        if (pos > 0) {
                            getAllJobProfilesAdapter.filter(spin_jobs.getSelectedItem().toString());



                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<JobTitlePojo>> call, Throwable t) {
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