package com.example.paytracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.paytracker.adapter.ReportsAdapter;
import com.example.paytracker.api.ApiService;
import com.example.paytracker.api.RetroClient;
import com.example.paytracker.model.JobTitlePojo;
import com.example.paytracker.model.PaymentPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportsActivity extends BaseActivity {
    ListView lv;
    Spinner spin_jobs;
    SharedPreferences sharedPreferences;
    String session;
    ReportsAdapter reportsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        getSupportActionBar().setTitle(" Reports");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        session = sharedPreferences.getString("uname", "def-val");

        spin_jobs=(Spinner)findViewById(R.id.spin_jobs);
        getJobs();

        lv=(ListView)findViewById(R.id.lv);
        serverData();
        Toast.makeText(getApplicationContext(),getIntent().getStringExtra("start_date")+"  "+getIntent().getStringExtra("end_date"),Toast.LENGTH_SHORT).show();
    }
    ProgressDialog progressDialog;
    List<PaymentPojo> payments;
    private void serverData(){
        SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        progressDialog = new ProgressDialog(ReportsActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<PaymentPojo>> call = service.get_reports(sharedPreferences.getString("uname","-"),getIntent().getStringExtra("end_date"),getIntent().getStringExtra("start_date"));
        call.enqueue(new Callback<List<PaymentPojo>>() {
            @Override
            public void onResponse(Call<List<PaymentPojo>> call, Response<List<PaymentPojo>> response) {

                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(ReportsActivity.this,"No data found",Toast.LENGTH_SHORT).show();
                }else {
                    payments = response.body();
                    //Toast.makeText(ReportsActivity.this,""+payments.size(),Toast.LENGTH_SHORT).show();
                    reportsAdapter=new ReportsAdapter(payments, ReportsActivity.this);
                    lv.setAdapter(reportsAdapter);
                   // lv.setAdapter(new ReportsAdapter(payments, ReportsActivity.this));
                }
            }
            @Override
            public void onFailure(Call<List<PaymentPojo>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ReportsActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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
                ArrayAdapter aa = new ArrayAdapter(ReportsActivity.this, android.R.layout.simple_spinner_item, myjobs);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin_jobs.setAdapter(aa);

                spin_jobs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                        if (pos > 0) {
                            reportsAdapter.filter(spin_jobs.getSelectedItem().toString());



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