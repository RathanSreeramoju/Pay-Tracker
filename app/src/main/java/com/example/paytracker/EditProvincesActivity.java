package com.example.paytracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.paytracker.api.ApiService;
import com.example.paytracker.api.RetroClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProvincesActivity extends AppCompatActivity {
    EditText et_provinces_name,et_provinces_tax;
    ProgressDialog pd;
    Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_provinces);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Edit Provinces");


        et_provinces_name=(EditText)findViewById(R.id.et_provinces_name);
        et_provinces_tax=(EditText)findViewById(R.id.et_provinces_tax);
        et_provinces_name.setText(getIntent().getStringExtra("Provinces_name"));
        et_provinces_tax.setText(getIntent().getStringExtra("tax"));
        et_provinces_name.setEnabled(false);



        btn_submit=(Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serverData();
            }
        });

    }
    public  void serverData() {
        pd= new ProgressDialog(EditProvincesActivity.this);
        pd.setTitle("Please wait,Data is being submit...");
        pd.show();
        ApiService apiService = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseData> call = apiService.update_provinces(getIntent().getStringExtra("id"),et_provinces_name.getText().toString(),et_provinces_tax.getText().toString());
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                pd.dismiss();
                if (response.body().status.equals("true")) {
                    Toast.makeText(EditProvincesActivity.this, "Data Added Successfully", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(EditProvincesActivity.this, ViewProvincesActivity.class));
                    finish();
                } else {
                    Toast.makeText(EditProvincesActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(EditProvincesActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
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