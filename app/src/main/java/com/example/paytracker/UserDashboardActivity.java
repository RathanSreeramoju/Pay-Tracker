package com.example.paytracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UserDashboardActivity extends AppCompatActivity {
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private DrawerLayout dl;

    int day,month,year;
    ImageView image_add;
    TextView tv_add_work,addjob;
    CardView cd_daily,cd_weekly,cd_monthly,cd_yearly;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        navigationView();
        getSupportActionBar().setTitle("User Dashboard");

        tv_add_work=(TextView)findViewById(R.id.tv_add_work);

        image_add=(ImageView)findViewById(R.id.image_add);
        image_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UserDashboardActivity.this,AddWorkActivity.class);
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

                    case R.id.settings:
                        Intent set=new Intent(getApplicationContext(), SettingsActivity.class);
                        startActivity(set);
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