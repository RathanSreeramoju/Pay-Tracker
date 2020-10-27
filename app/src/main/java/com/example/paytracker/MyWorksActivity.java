package com.example.paytracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.paytracker.adapter.GetMyWorksAdapter;
import com.example.paytracker.api.ApiService;
import com.example.paytracker.api.RetroClient;
import com.example.paytracker.model.JobTitlePojo;
import com.example.paytracker.model.PaymentPojo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyWorksActivity extends AppCompatActivity {
    ListView list_view;
    ProgressDialog progressDialog;
    List<PaymentPojo> a1;
    SharedPreferences sharedPreferences;
    String session;
    Spinner spin_jobs;
    GetMyWorksAdapter getMyWorksAdapter;
    EditText tv_date;
    int mYear, mMonth, mDay;
    String DAY, MONTH, YEAR;
    String work_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_works);

        getSupportActionBar().setTitle("My Works");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        spin_jobs = (Spinner)findViewById(R.id.spin_jobs);

        sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        session = sharedPreferences.getString("uname", "def-val");

        list_view=(ListView)findViewById(R.id.list_view);
        a1= new ArrayList<>();
        getJobs();
        serverData();

        tv_date = (EditText) findViewById(R.id.tv_date);
//        tv_date.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                datepicker();
//            }
//        });

        tv_date.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = tv_date.getText().toString().toLowerCase(Locale.getDefault());
                getMyWorksAdapter.filterbyDate(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
            }
        });
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
    public void serverData(){
        progressDialog = new ProgressDialog(MyWorksActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<PaymentPojo>> call = service.myworks(session);
        call.enqueue(new Callback<List<PaymentPojo>>() {
            @Override
            public void onResponse(Call<List<PaymentPojo>> call, Response<List<PaymentPojo>> response) {
                //Toast.makeText(GetFineDetailsActivity.this,"AAA   "+response.body().size(),Toast.LENGTH_SHORT).show();

                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(MyWorksActivity.this,"No data found",Toast.LENGTH_SHORT).show();
                }else {
                    a1 = response.body();
                    getMyWorksAdapter=new GetMyWorksAdapter(a1, MyWorksActivity.this);
                    list_view.setAdapter(getMyWorksAdapter);

                    //list_view.setAdapter(new GetMyWorksAdapter(a1, MyWorksActivity.this));

                }
            }

            @Override
            public void onFailure(Call<List<PaymentPojo>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MyWorksActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
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
                ArrayAdapter aa = new ArrayAdapter(MyWorksActivity.this, android.R.layout.simple_spinner_item, myjobs);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin_jobs.setAdapter(aa);

                spin_jobs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                        if (pos > 0) {
                            getMyWorksAdapter.filter(spin_jobs.getSelectedItem().toString());



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
 /*
    *  public void datepicker() {
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
    }*/

