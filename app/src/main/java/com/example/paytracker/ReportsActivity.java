package com.example.paytracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.paytracker.adapter.ReportsAdapter;
import com.example.paytracker.api.ApiService;
import com.example.paytracker.api.RetroClient;
import com.example.paytracker.model.PaymentPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportsActivity extends BaseActivity {
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        getSupportActionBar().setTitle(" Reports");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lv=(ListView)findViewById(R.id.lv);
        serverData();
    }
    ProgressDialog progressDialog;
    List<PaymentPojo> payments;
    private void serverData(){
        SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        progressDialog = new ProgressDialog(ReportsActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<PaymentPojo>> call = service.get_reports(sharedPreferences.getString("uname","-"),getIntent().getStringExtra("start_date"),getIntent().getStringExtra("end_date"));
        call.enqueue(new Callback<List<PaymentPojo>>() {
            @Override
            public void onResponse(Call<List<PaymentPojo>> call, Response<List<PaymentPojo>> response) {
                //Toast.makeText(AvalableRoomsActivity.this,"ddddd   "+response.body().size(),Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(ReportsActivity.this,"No data found",Toast.LENGTH_SHORT).show();
                }else {
                    payments = response.body();
                    //Toast.makeText(ReportsActivity.this,""+payments.size(),Toast.LENGTH_SHORT).show();
                    lv.setAdapter(new ReportsAdapter(payments, ReportsActivity.this));
                }
            }
            @Override
            public void onFailure(Call<List<PaymentPojo>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ReportsActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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