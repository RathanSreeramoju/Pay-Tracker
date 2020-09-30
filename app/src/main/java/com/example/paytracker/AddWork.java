package com.example.paytracker;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AddWork extends AppCompatActivity {
    TextView tv_date;
    int mYear,mMonth,mDay;
    String DAY,MONTH,YEAR;
    String work_date;
    EditText et_start_time,et_end_time,et_earn_before_tax,et_total_hours,et_tax_deducted,et_net_income;
    Button btn_calculate_hours,btn_submit;
    String time;
}
