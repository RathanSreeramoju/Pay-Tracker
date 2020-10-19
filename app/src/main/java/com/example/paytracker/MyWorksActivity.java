package com.example.paytracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.paytracker.api.ApiService;
import com.example.paytracker.api.RetroClient;
import com.example.paytracker.model.PaymentPojo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyWorksActivity extends AppCompatActivity {
    ListView list_view;
    ProgressDialog progressDialog;
    List<PaymentPojo> a1;
    SharedPreferences sharedPreferences;
    String session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_works);
        getSupportActionBar().setTitle("My Works");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        session = sharedPreferences.getString("uname", "def-val");
        //Toast.makeText(UserPaymentDueActivity.this,session,Toast.LENGTH_SHORT).show();



        list_view=(ListView)findViewById(R.id.list_view);
        a1= new ArrayList<>();
        serverData();
    }

    public void serverData(){
        progressDialog = new ProgressDialog(MyWorksActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<PaymentPojo>> call = service.myworks(session);
        call.enqueue(new Callback<List<PaymentPojo>>() {
            @Override
            public void onResponse(Call<List<PaymentPojo>> call, Response<List<PaymentPojo>> response) {
                //Toast.makeText(GetFineDetailsActivity.this,"AAA   "+response.body().size(),Toast.LENGTH_SHORT).show();

                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(MyWorksActivity.this,"No data found",Toast.LENGTH_SHORT).show();
                }else {
                    a1 = response.body();
                    list_view.setAdapter(new GetMyWorksAdapter(a1, MyWorksActivity.this));

                }
            }

            @Override
            public void onFailure(Call<List<PaymentPojo>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MyWorksActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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
    }
}
