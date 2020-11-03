package com.example.paytracker;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.paytracker.api.ApiService;
import com.example.paytracker.api.RetroClient;
import com.example.paytracker.model.GetAllJobProfilePojo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddWorkActivity extends AppCompatActivity {
    TextView tv_date;
    int mYear, mMonth, mDay;
    String DAY, MONTH, YEAR;
    String work_date;
    EditText et_start_time, et_end_time, et_earn_before_tax, et_total_hours, et_tax_deducted, et_net_income,et_salperhour,et_tax;
    Button btn_calculate_hours, btn_submit;
    String time;
    Spinner sp_jobs,spin_break;
    ImageView imgeditenable;
    String[] myjobs,sal_pe_hour,tax_amount;
    ImageView image_add,img_addjob;
    TextView tv_add_work,addjob;
    String[] mytax;

    List<GetAllJobProfilePojo> array_jobs;

    SharedPreferences sharedPreferences;
    String session;
    View deleteDialogView;
    AlertDialog deleteDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_work);

        getSupportActionBar().setTitle("Add Work");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        img_addjob=(ImageView)findViewById(R.id.img_addjob);
        img_addjob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AddWorkActivity.this,GetAllJobProfileActivity.class);
                startActivity(intent);
            }
        });

        addjob=(TextView)findViewById(R.id.addjob);
        addjob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AddWorkActivity.this,GetAllJobProfileActivity.class);
                startActivity(intent);
            }
        });
        sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        session = sharedPreferences.getString("uname", "def-val");

        sp_jobs = (Spinner) findViewById(R.id.spin_jobs);
        spin_break = (Spinner) findViewById(R.id.spin_break);
        et_salperhour=(EditText)findViewById(R.id.et_salperhour);
        getJobs();
        LayoutInflater factory = LayoutInflater.from(AddWorkActivity.this);
        deleteDialogView = factory.inflate(R.layout.alert_popup_window, null);

        et_net_income = (EditText)deleteDialogView.findViewById(R.id.et_net_income);
        et_total_hours = (EditText)deleteDialogView.findViewById(R.id.et_total_hours);
        et_earn_before_tax = (EditText)deleteDialogView.findViewById(R.id.et_earn_before_tax);
        et_tax_deducted = (EditText)deleteDialogView.findViewById(R.id.et_tax_deducted);

        btn_calculate_hours = (Button) findViewById(R.id.btn_calculate_hours);
        btn_calculate_hours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                deleteDialog = new AlertDialog.Builder(AddWorkActivity.this).create();
                deleteDialog.setView(deleteDialogView);
                deleteDialogView.findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(sp_jobs.getSelectedItem().toString().equals("Select Job"))
                        {
                            Toast.makeText(AddWorkActivity.this, "Select Job ", Toast.LENGTH_LONG).show();
                        }
                        else if(spin_break.getSelectedItem().toString().equals("Choose Break Timings"))
                        {
                            Toast.makeText(AddWorkActivity.this, "Select Break Time", Toast.LENGTH_LONG).show();
                        }

                        else if(et_start_time.getText().toString().equals(""))
                        {
                            Toast.makeText(AddWorkActivity.this, "Select Start Time", Toast.LENGTH_LONG).show();
                        }

                        else if(et_start_time.getText().toString().equals(""))
                        {
                            Toast.makeText(AddWorkActivity.this, "Select End Time", Toast.LENGTH_LONG).show();
                        }
                        else if(sp_jobs.getSelectedItem().toString().equals(""))
                        {
                            Toast.makeText(AddWorkActivity.this, "Please Add Job ", Toast.LENGTH_LONG).show();
                        }
                        else if(et_start_time.getText().toString().equals(et_end_time.getText().toString()))
                        {
                            Toast.makeText(AddWorkActivity.this, "Start time and End time should not be same.", Toast.LENGTH_LONG).show();
                        }
                        else {
                            serverData();
                        }
                        //serverData();
                        //deleteDialog.dismiss();

                    }
                });
                calculateHours();
                //deleteDialog.show();
            }
        });

        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datepicker();
            }
        });

        et_start_time = (EditText) findViewById(R.id.et_start_time);


        et_tax = (EditText) findViewById(R.id.et_tax);
       /* et_net_income = (EditText) findViewById(R.id.et_net_income);
       et_tax_deducted = (EditText) findViewById(R.id.et_tax_deducted);
        et_total_hours = (EditText) findViewById(R.id.et_total_hours);
        et_earn_before_tax = (EditText) findViewById(R.id.et_earn_before_tax);*/
        et_start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setmTimePicker();

            }
        });
        et_end_time = (EditText) findViewById(R.id.et_end_time);

        et_end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setmTimePicker1();

            }
        });

    }

    ProgressDialog pd;

    public void serverData() {
        SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        pd = new ProgressDialog(AddWorkActivity.this);
        pd.setTitle("Please wait,Data is being submit...");
        pd.show();
        ApiService apiService = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseData> call = apiService.add_payment(sharedPreferences.getString("uname", "-"), work_date, et_start_time.getText().toString(),
                et_end_time.getText().toString(), et_earn_before_tax.getText().toString(),
                et_tax_deducted.getText().toString(),sp_jobs.getSelectedItem().toString(),et_tax.getText().toString(),
                et_salperhour.getText().toString(),spin_break.getSelectedItem().toString(),et_total_hours.getText().toString());

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
    float salary_tax, salary;


    /*
    public void serverData1() {
        SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        pd = new ProgressDialog(AddWorkActivity.this);
        pd.setTitle("Please wait,Data is being submit...");
        pd.show();
        ApiService apiService = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseData> call = apiService.add_payment(sharedPreferences.getString("uname", "-"), work_date, et_start_time.getText().toString(),


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
    float salary_tax, salary;
     */

    private void calculateHours() {
        if (et_start_time.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter start time.", Toast.LENGTH_LONG).show();
            return;
        }
        else if (et_end_time.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter end time.", Toast.LENGTH_LONG).show();
            return;
        }
        else if (sp_jobs.getSelectedItem().toString().equals("Select Job")) {
            Toast.makeText(AddWorkActivity.this, "Select Job ", Toast.LENGTH_LONG).show();
        }
        else if (spin_break.getSelectedItem().toString().equals("Choose Break Timings")) {
            Toast.makeText(AddWorkActivity.this, "Select Break Time", Toast.LENGTH_LONG).show();
        }
        else if (sp_jobs.getSelectedItem().toString().equals(""))
        {
            Toast.makeText(AddWorkActivity.this, "Please Add Job ", Toast.LENGTH_LONG).show();
        }
        else if (et_start_time.getText().toString().equals(et_end_time.getText().toString()))
        {
            Toast.makeText(AddWorkActivity.this, "Start time and End time should not be same.", Toast.LENGTH_LONG).show();
            deleteDialog.hide();
        }
        else {

            try {
                String start_time = et_start_time.getText().toString();
                String end_time = et_end_time.getText().toString();
                String break_time = spin_break.getSelectedItem().toString();
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                Date date1 = format.parse(start_time);
                Date date2 = format.parse(end_time);
                Date date3 = format.parse(break_time);
                long diff = date2.getTime() - date1.getTime();
                //Toast.makeText(this, ""+diff, Toast.LENGTH_SHORT).show();

                if (diff < 0) {
                    Toast.makeText(this, "End time should be greater than start date.", Toast.LENGTH_LONG).show();
                    return;
                }
                //long diff=differe - date3.getTime();
                long diffMinutes = diff / (60 * 1000) % 60;
                long diffHours = diff / (60 * 60 * 1000) % 24;

                String kk[] = break_time.split(":");
                int hrs = Integer.parseInt(kk[0]);
                int mins = Integer.parseInt(kk[1]);

                diffHours = diffHours - hrs;
                if (diffMinutes > mins) {
                    diffMinutes = diffMinutes - mins;

                } else {
                    diffHours = diffHours - 1;
                    int temp = 60 - mins;
                    diffMinutes = diffMinutes + temp;
                }

                et_total_hours.setText(diffHours + "Hr : " + diffMinutes + "Min");
                if (diffMinutes != 0) {
                    salary = Integer.parseInt(et_salperhour.getText().toString()) * (diffHours) + (((float) diffMinutes) / 60) * Integer.parseInt(et_salperhour.getText().toString());
                    et_earn_before_tax.setText("" + salary);
                    //salary_tax = (float) et_tax.getText().toString() / 100;
                    salary_tax = Float.parseFloat(et_tax.getText().toString()) / 100;
                    //Toast.makeText(this,"Salary -> "+salary_tax,Toast.LENGTH_LONG).show();
                } else {
                    salary = Integer.parseInt(et_salperhour.getText().toString()) * (diffHours);
                    et_earn_before_tax.setText("" + salary);
                    salary_tax = Float.parseFloat(et_tax.getText().toString()) / 100;
                    //Toast.makeText(this,"Salary -> "+salary_tax,Toast.LENGTH_LONG).show();
                }
                et_tax_deducted.setText("" + (salary_tax * salary));
                et_net_income.setText("" + (salary - (salary_tax * salary)));


            } catch (Exception e) {
            }
        }

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
                        MONTH = monthOfYear + 1 + "";
                        YEAR = year + "";
                        work_date = YEAR + "-" + MONTH + "-" + DAY;
                        tv_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void setmTimePicker() {

        // TODO Auto-generated method stub
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(AddWorkActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                et_start_time.setText(selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }

    public void setmTimePicker1() {

        // TODO Auto-generated method stub
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(AddWorkActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                et_end_time.setText(selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

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

    private void getJobs() {
        ApiService apiService = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<GetAllJobProfilePojo>> call = apiService.getmyjobprofile(session);
        call.enqueue(new Callback<List<GetAllJobProfilePojo>>() {
            @Override
            public void onResponse(Call<List<GetAllJobProfilePojo>> call, Response<List<GetAllJobProfilePojo>> response) {
                array_jobs = response.body();
                Log.d("TAG", "Response = " + array_jobs);
                myjobs = new String[array_jobs.size() + 1];
                sal_pe_hour = new String[array_jobs.size() + 1];
                tax_amount = new String[array_jobs.size() + 1];
                myjobs[0] = "Select Job";
                for (int i = 0; i < array_jobs.size(); i++) {
                    myjobs[i + 1] = array_jobs.get(i).getCompanyname();
                    sal_pe_hour[i + 1] = array_jobs.get(i).getSalaryperhour();
                    tax_amount[i + 1] = array_jobs.get(i).getTax_percentage();

                }
                ArrayAdapter aa = new ArrayAdapter(AddWorkActivity.this, android.R.layout.simple_spinner_item, myjobs);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_jobs.setAdapter(aa);

                sp_jobs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                        if (pos > 0) {
                            /*if(sp_jobs.getSelectedItem().equals("Select Job")){
                                et_salperhour.getText().clear();
                            }
                            else {
                                et_salperhour.setText(sal_pe_hour[pos]+"/ hour salary");
                            }*/
                            et_salperhour.setText(sal_pe_hour[pos]/*+"/ hour salary"*/);
                            et_tax.setText(tax_amount[pos]/*+"/ Tax"*/);

                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<GetAllJobProfilePojo>> call, Throwable t) {
                Log.d("TAG", "Response = " + t.toString());
            }
        });
    }

}