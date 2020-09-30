package com.example.paytracker;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.paytracker.api.ApiService;
import com.example.paytracker.api.RetroClient;



import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class AddWorkActivity extends AppCompatActivity {
    TextView tv_date;
    int mYear,mMonth,mDay;
    String DAY,MONTH,YEAR;
    String work_date;
    EditText et_start_time,et_end_time,et_earn_before_tax,et_total_hours,et_tax_deducted,et_net_income;
    Button btn_calculate_hours,btn_submit;
    String time;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_work);
        tv_date=(TextView)findViewById(R.id.tv_date);
        btn_calculate_hours=(Button)findViewById(R.id.btn_calculate_hours);
        btn_submit=(Button)findViewById(R.id.btn_submit);
        btn_calculate_hours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateHours();
            }
        });
        tv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datepicker();
            }
        });

        et_start_time=(EditText)findViewById(R.id.et_start_time);
        et_net_income=(EditText)findViewById(R.id.et_net_income);
        et_tax_deducted=(EditText)findViewById(R.id.et_tax_deducted);
        et_net_income=(EditText)findViewById(R.id.et_net_income);
        et_total_hours=(EditText)findViewById(R.id.et_total_hours);
        et_earn_before_tax=(EditText)findViewById(R.id.et_earn_before_tax);
        et_start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setmTimePicker();

            }
        });
        et_end_time=(EditText)findViewById(R.id.et_end_time);
        et_end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setmTimePicker1();

            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serverData();
            }
        });
    }
    ProgressDialog pd;
    public  void serverData() {
        SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        pd= new ProgressDialog(AddWorkActivity.this);
        pd.setTitle("Please wait,Data is being submit...");
        pd.show();
        ApiService apiService = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseData> call = apiService.add_payment(sharedPreferences.getString("uname","-"),work_date,et_start_time.getText().toString(),et_end_time.getText().toString(),et_earn_before_tax.getText().toString(),et_tax_deducted.getText().toString());
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                pd.dismiss();
                if (response.body().status.equals("true")) {
                    Toast.makeText(AddWorkActivity.this, "Data Added Successfully", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(AddWorkActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(AddWorkActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    private void calculateHours(){
        if(et_start_time.getText().toString().isEmpty()){
            Toast.makeText(this,"Please enter start time.",Toast.LENGTH_LONG).show();
            return;
        }
        if(et_end_time.getText().toString().isEmpty()){
            Toast.makeText(this,"Please enter end time.",Toast.LENGTH_LONG).show();
            return;
        }
        try {
            String start_time = et_start_time.getText().toString();
            String end_time = et_end_time.getText().toString();
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            Date date1 = format.parse(start_time);
            Date date2 = format.parse(end_time);
            long diff = date2.getTime() - date1.getTime();
            if(diff<0){
                Toast.makeText(this,"End time should be greater than start date.",Toast.LENGTH_LONG).show();
                return;
            }
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            et_total_hours.setText(diffHours+"Hr : "+diffMinutes+"Min");
            if(diffMinutes!=0){
                salary=(100)*(diffHours)+(100/60)*diffMinutes;
                et_earn_before_tax.setText(""+salary);
                salary_tax=(float)15/100;
                //Toast.makeText(this,"Salary -> "+salary_tax,Toast.LENGTH_LONG).show();
            }else{
                salary=(100)*(diffHours);
                et_earn_before_tax.setText(""+salary);
                salary_tax=(float)15/100;
                //Toast.makeText(this,"Salary -> "+salary_tax,Toast.LENGTH_LONG).show();
            }
            et_tax_deducted.setText(""+(salary_tax*salary));
            et_net_income.setText(""+(salary-(salary_tax*salary)));


        }catch (Exception e){}
    }
    float salary_tax,salary;
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
                        MONTH = monthOfYear + 1 + "";
                        YEAR = year + "";
                        work_date=YEAR+"-"+MONTH+"-"+DAY;
                        tv_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
    public void setmTimePicker(){

        // TODO Auto-generated method stub
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(AddWorkActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                et_start_time.setText( selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }

    public void setmTimePicker1(){

        // TODO Auto-generated method stub
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(AddWorkActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                et_end_time.setText( selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }
}

