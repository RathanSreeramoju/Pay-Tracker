package com.example.paytracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

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

    private void serverData() {
    }
}