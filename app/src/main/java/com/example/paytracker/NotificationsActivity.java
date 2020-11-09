package com.example.paytracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.example.paytracker.api.*;
import com.example.paytracker.model.*;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsActivity extends AppCompatActivity {
    Spinner spin_jobs;
    TextView tv_date,tv_time;
    EditText et_title,et_description;
    SharedPreferences sharedPreferences;
    String session;
    List<JobTitlePojo> array_jobs;
    String myjobs[];
    int mYear, mMonth, mDay;
    String DAY, MONTH, YEAR;
    Button btn_submit;
    private int  mHour, mMinute;
    int alaram_year,alaram_month,alaram_day,alaram_hour,alaram_min;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Notification");




        sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        session = sharedPreferences.getString("uname", "def-val");
        getJobs();

        spin_jobs=(Spinner)findViewById(R.id.spin_jobs);

        tv_date=(TextView)findViewById(R.id.tv_date);
        tv_time=(TextView)findViewById(R.id.tv_time);
        et_title=(EditText)findViewById(R.id.et_title);
        et_description=(EditText)findViewById(R.id.et_description);
        tv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datepicker();
            }
        });
        tv_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timepicker();
            }
        });

        btn_submit=(Button)findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Notification set successfully.",Toast.LENGTH_SHORT).show();
                finish();
                Calendar c = Calendar.getInstance();
                c.set(Calendar.MONTH, alaram_month);
                c.set(Calendar.DAY_OF_MONTH, alaram_day);
                c.set(Calendar.YEAR, alaram_year);
                c.set(Calendar.HOUR_OF_DAY, alaram_hour);
                c.set(Calendar.MINUTE, alaram_min);
                c.set(Calendar.SECOND, 0);
                startAlarm(c);
            }
        });


    }
    private void startAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        intent.putExtra("title",et_title.getText().toString()+"     "+spin_jobs.getSelectedItem().toString());
        intent.putExtra("msg",et_description.getText().toString());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }
    private void getJobs() {
        // TO-DO
    }

    public void datepicker() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        DAY = dayOfMonth + "";
                        alaram_day = dayOfMonth;
                        MONTH = monthOfYear + 1 + "";
                        alaram_month = monthOfYear;
                        YEAR = year + "";
                        alaram_year = year;
                        //work_date = YEAR + "-" + MONTH + "-" + DAY;
                        tv_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
    public void timepicker(){
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        alaram_hour = hourOfDay;
                        alaram_min = minute;
                        tv_time.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
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