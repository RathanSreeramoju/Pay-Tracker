package com.example.paytracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.paytracker.api.ApiService;
import com.example.paytracker.api.RetroClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditTaxesActivity extends AppCompatActivity {
    Spinner spin_prov_names;
    EditText et_tax_amount;
    Button btn_submit;
    ProgressDialog pd;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tax);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Edit Taxes");

        spin_prov_names=(Spinner)findViewById(R.id.spin_prov_names);

        et_tax_amount=(EditText)findViewById(R.id.et_tax_amount);
        et_tax_amount.setText(getIntent().getStringExtra("tax"));

        btn_submit=(Button)findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serverData();

            }
        });
    }

    public void serverData() {
        pd= new ProgressDialog(EditTaxesActivity.this);
        pd.setTitle("Please wait,Data is being submit...");
        pd.show();
        ApiService apiService = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseData> call = apiService.update_taxes(spin_prov_names.getSelectedItem().toString(),et_tax_amount.getText().toString(),getIntent().getStringExtra("id"));
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                pd.dismiss();
                if (response.body().status.equals("true")) {
                    Toast.makeText(EditTaxesActivity.this, "Data Added Successfully", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(EditTaxesActivity.this, ViewTaxActivity.class));
                    finish();
                } else {
                    Toast.makeText(EditTaxesActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(EditTaxesActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
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