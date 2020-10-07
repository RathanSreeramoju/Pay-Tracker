package com.example.paytracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.paytracker.adapter.ViewTaxesAdapter;
import com.example.paytracker.api.ApiService;
import com.example.paytracker.api.RetroClient;
import com.example.paytracker.model.ViewTaxesPojo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewTaxActivity extends AppCompatActivity {
    ListView list_view;
    ProgressDialog progressDialog;
    List<ViewTaxesPojo> a1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewt_tax);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("View Taxes");



        list_view=(ListView)findViewById(R.id.list_view);
        a1= new ArrayList<>();
        serverData();
        /*a1.add(new ViewTaxesPojo("1","Mallangi Parameswar reddy","300")); //static data
        a1.add(new ViewTaxesPojo("2","Mohan Bhavya","254"));*/



    }
    public void serverData(){
        progressDialog = new ProgressDialog(ViewTaxActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<ViewTaxesPojo>> call = service.get_taxes();
        call.enqueue(new Callback<List<ViewTaxesPojo>>() {
            @Override
            public void onResponse(Call<List<ViewTaxesPojo>> call, Response<List<ViewTaxesPojo>> response) {
                //Toast.makeText(AvalableRoomsActivity.this,"ddddd   "+response.body().size(),Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(ViewTaxActivity.this,"No data found",Toast.LENGTH_SHORT).show();
                }else {
                    a1 = response.body();
                    list_view.setAdapter(new ViewTaxesAdapter(a1,ViewTaxActivity.this));
                }
            }
            @Override
            public void onFailure(Call<List<ViewTaxesPojo>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ViewTaxActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override                                                                                                                    //add this method in your program
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



