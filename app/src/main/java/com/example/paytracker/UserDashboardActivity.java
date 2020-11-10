package com.example.paytracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * User can see their reports in the form of bar chart and pie chart
 */

public class UserDashboardActivity extends AppCompatActivity {
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private DrawerLayout dl;
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
    Button btn_pie,btn_bar;
    int day,month,year;
    ImageView image_add,img_addjob;
    TextView tv_add_work,addjob;
    CardView cd_daily,cd_weekly,cd_monthly,cd_yearly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        navigationView();
        getSupportActionBar().setTitle("User Dashboard");
        barChart=findViewById(R.id.barchart);
        p=findViewById(R.id.piechart);
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





        tv_add_work=(TextView)findViewById(R.id.tv_add_work);

        image_add=(ImageView)findViewById(R.id.image_add);

        img_addjob=(ImageView)findViewById(R.id.img_addjob);
        img_addjob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UserDashboardActivity.this,GetAllJobProfileActivity.class);
                startActivity(intent);
            }
        });

        addjob=(TextView)findViewById(R.id.addjob);
        addjob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UserDashboardActivity.this,GetAllJobProfileActivity.class);
                startActivity(intent);
            }
        });

        image_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UserDashboardActivity.this,AddWorkActivity.class);
                startActivity(intent);
            }
        });
        tv_add_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserDashboardActivity.this,AddWorkActivity.class);
                startActivity(intent);
            }
        });


        cd_daily=(CardView)findViewById(R.id.cd_daily);
        cd_weekly=(CardView)findViewById(R.id.cd_weekly);
        cd_monthly=(CardView)findViewById(R.id.cd_monthly);
        cd_yearly=(CardView)findViewById(R.id.cd_yearly);
        cd_daily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Calendar calendar = Calendar.getInstance();
                day = calendar.get(Calendar.DAY_OF_MONTH);
                month = calendar.get(Calendar.MONTH);
                year = calendar.get(Calendar.YEAR);

                Date newDate = calendar.getTime();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String end_date = dateFormat.format(newDate);
                Intent intent=new Intent(UserDashboardActivity.this,ReportsActivity.class);
                intent.putExtra("start_date",year+"-"+(month+1)+"-"+day);
                intent.putExtra("end_date",end_date);
                startActivity(intent);*/
                Intent intent=new Intent(UserDashboardActivity.this,DailyReportsActivity.class);
                startActivity(intent);

            }
        });
        cd_weekly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserDashboardActivity.this,WeeklySelectionActivity.class);
                startActivity(intent);
                /*Calendar calendar = Calendar.getInstance();
                day = calendar.get(Calendar.DAY_OF_MONTH);
                month = calendar.get(Calendar.MONTH);
                year = calendar.get(Calendar.YEAR);
                calendar.add(Calendar.DAY_OF_YEAR, -7);
                Date newDate = calendar.getTime();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String end_date = dateFormat.format(newDate);
                //showMessage(UserDashboardActivity.this,year+"-"+(month+1)+"-"+day);
                Intent intent=new Intent(UserDashboardActivity.this,ReportsActivity.class);
                intent.putExtra("start_date",year+"-"+(month+1)+"-"+day);
                intent.putExtra("end_date",end_date);
                startActivity(intent);*/
            }
        });
        cd_monthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserDashboardActivity.this,MonthlySelectionActivity.class);
                startActivity(intent);
                /*Calendar calendar = Calendar.getInstance();
                day = calendar.get(Calendar.DAY_OF_MONTH);
                month = calendar.get(Calendar.MONTH);
                year = calendar.get(Calendar.YEAR);
                calendar.add(Calendar.DAY_OF_YEAR, -30);
                Date newDate = calendar.getTime();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String end_date = dateFormat.format(newDate);
                //showMessage(UserDashboardActivity.this,year+"-"+(month+1)+"-"+day);
                Intent intent=new Intent(UserDashboardActivity.this,ReportsActivity.class);
                intent.putExtra("start_date",year+"-"+(month+1)+"-"+day);
                intent.putExtra("end_date",end_date);
                startActivity(intent);*/
            }
        });
        cd_yearly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(UserDashboardActivity.this,YearSelectionActivity.class);
                startActivity(intent);
               /* Calendar calendar = Calendar.getInstance();
                day = calendar.get(Calendar.DAY_OF_MONTH);
                month = calendar.get(Calendar.MONTH);
                year = calendar.get(Calendar.YEAR);
                calendar.add(Calendar.DAY_OF_YEAR, -365);
                Date newDate = calendar.getTime();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String end_date = dateFormat.format(newDate);
                //showMessage(UserDashboardActivity.this,year+"-"+(month+1)+"-"+day);
                Intent intent=new Intent(UserDashboardActivity.this,ReportsActivity.class);
                intent.putExtra("start_date",year+"-"+(month+1)+"-"+day);
                intent.putExtra("end_date",end_date);
                startActivity(intent);*/

            }
        });
    }

    SharedPreferences sharedPreferences;
    String session;

    private void getdata() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url ="http://paytracker.ca/PayTracker/getmonthreport.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url , new Response.Listener<String>() {
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
                        Toast.makeText(UserDashboardActivity.this,data+""+link,Toast.LENGTH_LONG).show();
                        entries.add(new BarEntry(Integer.parseInt(link), t1++));
                        entries1.add(new Entry(Integer.parseInt(link), t1));
                        dates.add(data);
                    }


                    BarDataSet bardataset = new BarDataSet(entries, "Latest Week Report");



                    BarData data = new BarData( dates, bardataset);
                    barChart.setData(data); // set the data and list of labels into chart
                    // set the description
                    bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
                    barChart.animateY(5000);





                    PieDataSet pd = new PieDataSet(entries1, "Latest Week Report");



                    PieData pp = new PieData( dates, pd);
                    p.setData(pp); // set the data and list of labels into chart
                    // set the description
                    pd.setColors(ColorTemplate.COLORFUL_COLORS);
                    p.setDescription("Pie Chart");
                    p.setCenterText("Clock Work");
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
                params.put("email",session);//


                return params;
            }
        };
        requestQueue.add(stringRequest);


    }

    private void navigationView(){
        dl = (DrawerLayout)findViewById(R.id.dl_main);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);
        dl.addDrawerListener(t);
        t.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nv = (NavigationView)findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.myprofile:
                        Intent intent=new Intent(getApplicationContext(), EditProfileActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.myhome:
                        Intent home=new Intent(getApplicationContext(), UserDashboardActivity.class);
                        startActivity(home);
                        break;
                    case R.id.about:
                        Intent about=new Intent(getApplicationContext(), AboutActivity.class);
                        startActivity(about);
                        break;

                    case R.id.myjobprofile:
                        Intent job=new Intent(getApplicationContext(), GetAllJobProfileActivity.class);
                        startActivity(job);
                        break;

                    case R.id.myjobworks:
                        Intent myjobworks=new Intent(getApplicationContext(), MyWorksActivity.class);
                        startActivity(myjobworks);
                        break;
                    case R.id.search_reports:
                        Intent search_report=new Intent(getApplicationContext(), SearchSelectionReportsActivity.class);
                        startActivity(search_report);
                        break;

                    case R.id.mypayment:
                        Intent notification=new Intent(getApplicationContext(), NotificationsActivity.class);
                        startActivity(notification);
                        break;

                    case R.id.logout:
                        Intent logout=new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(logout);
                        break;
                    default:
                        return true;
                }
                dl.closeDrawer(GravityCompat.START);
                return true;

            }
        });
    }
    @Override
    public void onBackPressed() {
        if (dl.isDrawerOpen(GravityCompat.START)) {
            dl.closeDrawer(GravityCompat.START);
        } else {
            closeApp();
        }

    }

    /* @Override
     public boolean onOptionsItemSelected(MenuItem item) {
         int id = item.getItemId();
         if (dl.isDrawerOpen(GravityCompat.START)) {
             dl.closeDrawer(GravityCompat.START);
         } else {
             dl.openDrawer(GravityCompat.START);
         }
         return super.onOptionsItemSelected(item);
     }*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (dl.isDrawerOpen(GravityCompat.START)) {

            dl.closeDrawer(GravityCompat.START);
        }
        else {

            dl.openDrawer(GravityCompat.START);
        }
        switch (id){

            case R.id.search_reports:
                Intent myIntent = new Intent(getApplicationContext(), SearchSelectionReportsActivity.class);
                startActivity(myIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void closeApp(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(UserDashboardActivity.this);
        builder1.setMessage("Do you want to close the Application.");  //message we want to show the end user
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel(); //cancle the alert dialog box
                        finish();//finish the process
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}