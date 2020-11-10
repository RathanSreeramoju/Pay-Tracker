package com.example.paytracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.paytracker.adapter.ViewProvincesAdapter;
import com.example.paytracker.api.ApiService;
import com.example.paytracker.api.RetroClient;
import com.example.paytracker.model.ProvincesPojo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Admin can view the province activity
 */

public class ViewProvincesActivity extends AppCompatActivity {
    ListView list_view;
    ProgressDialog progressDialog;
    List<ProvincesPojo> a1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_provinces);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("View Provinces");


        list_view=(ListView)findViewById(R.id.list_view);
        a1= new ArrayList<>();
        serverData();



    }

    public void serverData(){
        progressDialog = new ProgressDialog(ViewProvincesActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        // Using ApiService calls through Retrofit Client Instance
        Call<List<ProvincesPojo>> call = service.get_provinces();
        call.enqueue(new Callback<List<ProvincesPojo>>() {
            @Override
            public void onResponse(Call<List<ProvincesPojo>> call, Response<List<ProvincesPojo>> response) {
                //Toast.makeText(AvalableRoomsActivity.this,"ddddd   "+response.body().size(),Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(ViewProvincesActivity.this,"No data found",Toast.LENGTH_SHORT).show();
                }else {
                    a1 = response.body();
                    list_view.setAdapter(new ViewProvincesAdapter(a1, ViewProvincesActivity.this));
                }
            }
            @Override
            public void onFailure(Call<List<ProvincesPojo>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ViewProvincesActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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