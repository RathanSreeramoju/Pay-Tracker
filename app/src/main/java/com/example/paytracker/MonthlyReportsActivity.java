package com.example.paytracker;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.paytracker.adapter.ReportsAdapter;
import com.example.paytracker.api.ApiService;
import com.example.paytracker.api.RetroClient;
import com.example.paytracker.model.JobTitlePojo;
import com.example.paytracker.model.PaymentPojo;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class MonthlyReportsActivity extends AppCompatActivity {
    ListView lv;
    Spinner spin_jobs;
    SharedPreferences sharedPreferences;
    String session;
    Button btn_pie,btn_bar;

    BarChart barChart;
    BarData barData,data;
    BarDataSet barDataSet;
    ArrayList barEntries;
    PieChart p;
    ArrayList<String> labels;
    ArrayList<String> dates=new ArrayList<String>();
    int t1=0;
    ArrayList<BarEntry> entries = new ArrayList<>();
    ArrayList<Entry> entries1 = new ArrayList<>();
    ReportsAdapter reportsAdapters;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_reports);

        getSupportActionBar().setTitle(" Monthly Reports");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        barChart=findViewById(R.id.barchart);
        p=findViewById(R.id.piechart);

        btn_pie=(Button)findViewById(R.id.btn_pie);

        btn_bar=(Button)findViewById(R.id.btn_bar);


        btn_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_bar.setBackgroundColor(Color.parseColor("#03DAC5"));
                btn_pie.setBackgroundColor(Color.parseColor("#d5d5d5"));
                p.setVisibility(View.GONE);
                barChart.setVisibility(View.VISIBLE);
            }
        });
        btn_pie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_pie.setBackgroundColor(Color.parseColor("#03DAC5"));
                btn_bar.setBackgroundColor(Color.parseColor("#d5d5d5"));
                barChart.setVisibility(View.GONE);
                p.setVisibility(View.VISIBLE);

            }
        });
        btn_bar.setBackgroundColor(Color.parseColor("#03DAC5"));
        btn_pie.setBackgroundColor(Color.parseColor("#d5d5d5"));



        getdata();

        sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        session = sharedPreferences.getString("uname", "def-val");

        spin_jobs=(Spinner)findViewById(R.id.spin_jobs);
        getJobs();

        lv=(ListView)findViewById(R.id.lv);
        serverData();
        Toast.makeText(getApplicationContext(),session+getIntent().getStringExtra("start_date")+"  "+getIntent().getStringExtra("end_date"),Toast.LENGTH_SHORT).show();
    }
    ProgressDialog progressDialog;
    List<PaymentPojo> payments;
    private void serverData(){
        SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        progressDialog = new ProgressDialog(MonthlyReportsActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<PaymentPojo>> call = service.get_reports(sharedPreferences.getString("uname","-"),getIntent().getStringExtra("end_date"),getIntent().getStringExtra("start_date"));
        call.enqueue(new Callback<List<PaymentPojo>>() {
            @Override
            public void onResponse(Call<List<PaymentPojo>> call, retrofit2.Response<List<PaymentPojo>> response) {
                //Toast.makeText(AvalableRoomsActivity.this,"ddddd   "+response.body().size(),Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(MonthlyReportsActivity.this,"No data found",Toast.LENGTH_SHORT).show();
                }else {
                    payments = response.body();
                    //Toast.makeText(ReportsActivity.this,""+payments.size(),Toast.LENGTH_SHORT).show();

                    reportsAdapters=new ReportsAdapter(payments, MonthlyReportsActivity.this);
                    lv.setAdapter(reportsAdapters);
                    //lv.setAdapter(new ReportsAdapters(payments, ReportsActivity.this));
                }
            }
            @Override
            public void onFailure(Call<List<PaymentPojo>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MonthlyReportsActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    List<JobTitlePojo> array_jobs;
    String myjobs[];

    //  SharedPreferences sharedPreferences;
    //String session;

    private void getdata() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url ="http://paytracker.ca/PayTracker/getmom.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url , new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject json = null;
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    // data=new String[jArray.length()];
                    JSONObject jObj;

                    for (int i = 0; i < jsonArray.length(); i++) {
                        jObj = jsonArray.getJSONObject(i);
                        String data=jObj.getString("work_date");
                        String link=jObj.getString("payment");
                        Toast.makeText(MonthlyReportsActivity.this,data+""+link,Toast.LENGTH_LONG).show();
                        entries.add(new BarEntry(Integer.parseInt(link), t1++));
                        entries1.add(new Entry(Integer.parseInt(link), t1));
                        dates.add(data);
                    }


                    BarDataSet bardataset = new BarDataSet(entries, "Month Report");



                    BarData data = new BarData( dates, bardataset);
                    barChart.setData(data); // set the data and list of labels into chart
                    // set the description
                    bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
                    barChart.animateY(5000);



                    PieDataSet pd = new PieDataSet(entries1, "");



                    PieData pp = new PieData( dates, pd);
                    p.setData(pp); // set the data and list of labels into chart
                    // set the description
                    pd.setColors(ColorTemplate.COLORFUL_COLORS);
                    p.setDescription("Pie Chart");
                    p.setCenterText("Pay Tracker");
                    //p.animateY(5000);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Server Issue",Toast.LENGTH_SHORT).show();
            }
        })

        {
            @Override
            protected Map<String, String> getParams() {

                sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
                session = sharedPreferences.getString("uname", "def-val");
                Map<String, String> params = new HashMap<String, String>();
                //  params.put("type",sptype.getSelectedItem().toString());//
                params.put("uname",session);//
                params.put("start_date","2020-10-01");
                params.put("end_date","2020-10-31");

                return params;
            }
        };
        requestQueue.add(stringRequest);


    }

    private void getJobs() {
        ApiService apiService = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<JobTitlePojo>> call = apiService.getjobtypes_by_uname(session);
        call.enqueue(new Callback<List<JobTitlePojo>>() {
            @Override
            public void onResponse(Call<List<JobTitlePojo>> call, retrofit2.Response<List<JobTitlePojo>> response) {
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
                ArrayAdapter aa = new ArrayAdapter(MonthlyReportsActivity.this, android.R.layout.simple_spinner_item, myjobs);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin_jobs.setAdapter(aa);

                spin_jobs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                        if (pos > 0) {
                            reportsAdapters.filter(spin_jobs.getSelectedItem().toString());



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
