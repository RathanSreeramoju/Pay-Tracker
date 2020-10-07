package com.example.paytracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.paytracker.adapter.GetAllJobProfileAdapter;
import com.example.paytracker.api.ApiService;
import com.example.paytracker.api.RetroClient;
import com.example.paytracker.model.GetAllJobProfilePojo;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_all_job_profile);

        getSupportActionBar().setTitle("Job Profiles");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



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
        serverData();
    }

    private void serverData() {

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
                    list_view.setAdapter(new GetAllJobProfileAdapter(getAllJobProfilePojo, GetAllJobProfileActivity.this));
                }
            }

            @Override
            public void onFailure(Call<List<GetAllJobProfilePojo>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(GetAllJobProfileActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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