package com.example.paytracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class PaySlipActivity extends AppCompatActivity {


    String dat;
    TextView ps_date,ps_cname,ps_stime,ps_etime,ps_totalhours,ps_breaks,ps_sph,ps_ptax,ps_tdeductions,ps_netincome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_slip);

        getSupportActionBar().setTitle("Pay Report");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dat = getIntent().getStringExtra("t1id");

        Toast.makeText(PaySlipActivity.this,dat,Toast.LENGTH_LONG).show();

        ps_date=(TextView)findViewById(R.id.ps_date);
        ps_date.setText("Date : "+getIntent().getStringExtra("date"));

        ps_cname=(TextView)findViewById(R.id.ps_cname);
        ps_cname.setText("Company Name : "+getIntent().getStringExtra("cname"));

        ps_stime=(TextView)findViewById(R.id.ps_stime);
        ps_stime.setText("Start Time : "+getIntent().getStringExtra("stime"));

        ps_etime=(TextView)findViewById(R.id.ps_etime);
        ps_etime.setText("End Time : "+getIntent().getStringExtra("etime"));

        ps_totalhours=(TextView)findViewById(R.id.ps_totalhours);
        ps_totalhours.setText("Total Hours : "+getIntent().getStringExtra("thours"));

        ps_breaks=(TextView)findViewById(R.id.ps_breaks);
        ps_breaks.setText("Breaks : "+getIntent().getStringExtra("breaks")+"Mins");

        ps_sph=(TextView)findViewById(R.id.ps_sph);
        ps_sph.setText("Salary Per Hour : "+getIntent().getStringExtra("sal")+"$");

        ps_ptax=(TextView)findViewById(R.id.ps_ptax);
        ps_ptax.setText("Provience Tax : "+getIntent().getStringExtra("tax")+"%");


        ps_tdeductions=(TextView)findViewById(R.id.ps_tdeductions);
        ps_tdeductions.setText("Total Deductions : "+getIntent().getStringExtra("dtax")+"$");

        ps_netincome=(TextView)findViewById(R.id.ps_netincome);
        ps_netincome.setText("Net Income : "+getIntent().getStringExtra("net")+"$");



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
