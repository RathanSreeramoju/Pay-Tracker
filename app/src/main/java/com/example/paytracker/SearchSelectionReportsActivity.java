package com.example.paytracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paytracker.api.ApiService;
import com.example.paytracker.api.RetroClient;
import com.example.paytracker.model.JobTitlePojo;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchSelectionReportsActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    String session;
    Spinner spin_jobs;
    TextView tv_from_date,tv_to_date;
    Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_selection);

        spin_jobs = (Spinner)findViewById(R.id.spin_jobs);
        getSupportActionBar().setTitle("Search Data");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        session = sharedPreferences.getString("uname", "def-val");
        tv_from_date = (TextView)findViewById(R.id.tv_from_date);
        tv_from_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datepicker_from();
            }
        });
        tv_to_date = (TextView)findViewById(R.id.tv_to_date);
        tv_to_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datepicker_to();
            }
        });
        btn_submit= (Button)findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SearchSelectionReportsActivity.this,SearchReportActivity.class);
                intent.putExtra("start_date",end_date);
                intent.putExtra("end_date",start_date);
                intent.putExtra("job_type",spin_jobs.getSelectedItem().toString());
                startActivity(intent);
            }
        });
        getJobs();
    }
    String start_date,end_date;
    private void datepicker_to() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        String DAY = dayOfMonth + "";
                        String MONTH = (monthOfYear + 1) + "";
                        String YEAR = year + "";
                        end_date = YEAR + "-" + MONTH + "-" + DAY;
                        tv_to_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void datepicker_from() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        String DAY = dayOfMonth + "";
                        String MONTH = (monthOfYear + 1) + "";
                        String YEAR = year + "";
                        start_date = YEAR + "-" + MONTH + "-" + DAY;

                        tv_from_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    List<JobTitlePojo> array_jobs;
    String myjobs[];
    private void getJobs() {
        ApiService apiService = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<JobTitlePojo>> call = apiService.getjobtypes_by_uname(session);
        call.enqueue(new Callback<List<JobTitlePojo>>() {
            @Override
            public void onResponse(Call<List<JobTitlePojo>> call, Response<List<JobTitlePojo>> response) {
                array_jobs = response.body();
                if(array_jobs!=null){
                    if(array_jobs.size()>0) {
                        myjobs = new String[array_jobs.size()+1];
                        myjobs[0] = "Select Job";
                        Toast.makeText(getApplicationContext(),""+array_jobs.size(),Toast.LENGTH_SHORT).show();
                    }
                }

                for (int i = 0; i < array_jobs.size(); i++) {
                    myjobs[i + 1] = array_jobs.get(i).getCompany_name();
                }
                ArrayAdapter aa = new ArrayAdapter(SearchSelectionReportsActivity.this, android.R.layout.simple_spinner_item, myjobs);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin_jobs.setAdapter(aa);

                spin_jobs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                        if (pos > 0) {

                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<JobTitlePojo>> call, Throwable t) {
                Log.d("TAG", "Response = " + t.toString());
            }
        });
    }
}