package com.example.paytracker;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.paytracker.R;
import com.example.paytracker.adapter.DailyReportAdapter;
import com.example.paytracker.adapter.ReportsAdapter;
import com.example.paytracker.api.ApiService;
import com.example.paytracker.api.RetroClient;
import com.example.paytracker.model.PaymentPojo;
import com.example.paytracker.model.WorkDatePojo;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DailyReportsActivity extends AppCompatActivity {
    ListView lv;
    String start_date,end_date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        getSupportActionBar().setTitle(" Reports");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lv=(ListView)findViewById(R.id.lv);
        workingDates();
        //serverData();
        //Toast.makeText(getApplicationContext(),getIntent().getStringExtra("start_date")+"  "+getIntent().getStringExtra("end_date"),Toast.LENGTH_SHORT).show();
    }
    String time_slots="";
    com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener selliste= new com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
            //Toast.makeText(getApplicationContext(),""+dayOfMonth,Toast.LENGTH_SHORT).show();
            // btn_select_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
            start_date= year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
            end_date= year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
            serverData();
        }
    };
    private com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd;
    private void showCustomDialog(){
        Calendar now = Calendar.getInstance();
        dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                selliste,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        for(WorkDatePojo aa:workingDates) {
            getEnbDates(aa.getWork_date());
        }
        Calendar[] disabledDays1 = dates.toArray(new Calendar[dates.size()]);
        dpd.setSelectableDays(disabledDays1);
        dpd.show(getSupportFragmentManager(),"ddd");
    }
    private Calendar dateToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }
    Calendar calendar;
    List<Calendar> dates = new ArrayList<>();
    private void getEnbDates(String a){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null,today=null;
        try {
            date1 = sdf.parse(a);
            String str=sdf.format(date1);
            Log.i("CW",str);
            DailyReportsActivity obj = new DailyReportsActivity();
            calendar = obj.dateToCalendar(date1);
        } catch (ParseException e) {
            //Toast.makeText(getApplicationContext(),"Erroorrr",Toast.LENGTH_SHORT).show();
            Log.i("CW","Errorrr");
        }
        //calendar.
        dates.add(calendar);

    }
    List<WorkDatePojo> workingDates;
    private void workingDates(){
        workingDates = new ArrayList<>();
        SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        progressDialog = new ProgressDialog(DailyReportsActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<WorkDatePojo>> call = service.get_daily_work_dates(sharedPreferences.getString("uname","-"));
        call.enqueue(new Callback<List<WorkDatePojo>>() {
            @Override
            public void onResponse(Call<List<WorkDatePojo>> call, Response<List<WorkDatePojo>> response) {
                //Toast.makeText(AvalableRoomsActivity.this,"ddddd   "+response.body().size(),Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(DailyReportsActivity.this,"No data found",Toast.LENGTH_SHORT).show();
                }else {
                    workingDates = response.body();
                    if(workingDates!=null){
                        if(workingDates.size()>0){
                            //Toast.makeText(DailyReportsActivity.this,""+workingDates.get(0).getWork_date(),Toast.LENGTH_SHORT).show();
                            showCustomDialog();
                            //Toast.makeText(DailyReportsActivity.this,""+workingDates.size(),Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<List<WorkDatePojo>> call, Throwable t) {
                progressDialog.dismiss();
                //Toast.makeText(DailyReportsActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    ProgressDialog progressDialog;
    List<PaymentPojo> payments;
    private void serverData(){
        SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        progressDialog = new ProgressDialog(DailyReportsActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<PaymentPojo>> call = service.get_reports(sharedPreferences.getString("uname","-"),start_date,end_date);
        call.enqueue(new Callback<List<PaymentPojo>>() {
            @Override
            public void onResponse(Call<List<PaymentPojo>> call, Response<List<PaymentPojo>> response) {
                //Toast.makeText(AvalableRoomsActivity.this,"ddddd   "+response.body().size(),Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(DailyReportsActivity.this,"No data found",Toast.LENGTH_SHORT).show();
                }else {
                    payments = response.body();
                    //Toast.makeText(ReportsActivity.this,""+payments.size(),Toast.LENGTH_SHORT).show();
                    lv.setAdapter(new DailyReportAdapter(payments, DailyReportsActivity.this));
                }
            }
            @Override
            public void onFailure(Call<List<PaymentPojo>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(DailyReportsActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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