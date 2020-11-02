package com.example.paytracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.paytracker.adapter.WeekSelectionAdapter;
import com.example.paytracker.model.WeekSelectionPojo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class WeeklySelectionActivity extends AppCompatActivity {
    List<WeekSelectionPojo> list_week=new ArrayList<>();
    GridView gridview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weeks_work);

        getSupportActionBar().setTitle("Weekly Reports");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list_week.add(new WeekSelectionPojo("1","Week-1"));
        list_week.add(new WeekSelectionPojo("2","Week-2"));
        list_week.add(new WeekSelectionPojo("3","Week-3"));
        list_week.add(new WeekSelectionPojo("4","Week-4"));
        list_week.add(new WeekSelectionPojo("5","Week-5"));
        list_week.add(new WeekSelectionPojo("6","Week-6"));
        list_week.add(new WeekSelectionPojo("7","Week-7"));
        list_week.add(new WeekSelectionPojo("8","Week-8"));
        list_week.add(new WeekSelectionPojo("9","Week-9"));
        list_week.add(new WeekSelectionPojo("10","Week-10"));
        list_week.add(new WeekSelectionPojo("11","Week-11"));
        list_week.add(new WeekSelectionPojo("12","Week-12"));
        list_week.add(new WeekSelectionPojo("13","Week-13"));
        list_week.add(new WeekSelectionPojo("14","Week-14"));
        list_week.add(new WeekSelectionPojo("15","Week-15"));
        list_week.add(new WeekSelectionPojo("16","Week-16"));
        list_week.add(new WeekSelectionPojo("17","Week-17"));
        list_week.add(new WeekSelectionPojo("18","Week-18"));
        list_week.add(new WeekSelectionPojo("19","Week-19"));
        list_week.add(new WeekSelectionPojo("20","Week-20"));

        list_week.add(new WeekSelectionPojo("21","Week-21"));
        list_week.add(new WeekSelectionPojo("22","Week-22"));
        list_week.add(new WeekSelectionPojo("23","Week-23"));
        list_week.add(new WeekSelectionPojo("24","Week-24"));
        list_week.add(new WeekSelectionPojo("25","Week-25"));
        list_week.add(new WeekSelectionPojo("26","Week-26"));
        list_week.add(new WeekSelectionPojo("27","Week-27"));
        list_week.add(new WeekSelectionPojo("28","Week-28"));
        list_week.add(new WeekSelectionPojo("29","Week-29"));
        list_week.add(new WeekSelectionPojo("30","Week-30"));

        list_week.add(new WeekSelectionPojo("31","Week-31"));
        list_week.add(new WeekSelectionPojo("32","Week-32"));
        list_week.add(new WeekSelectionPojo("33","Week-33"));
        list_week.add(new WeekSelectionPojo("34","Week-34"));
        list_week.add(new WeekSelectionPojo("35","Week-35"));
        list_week.add(new WeekSelectionPojo("36","Week-36"));
        list_week.add(new WeekSelectionPojo("37","Week-37"));
        list_week.add(new WeekSelectionPojo("38","Week-38"));
        list_week.add(new WeekSelectionPojo("39","Week-39"));
        list_week.add(new WeekSelectionPojo("40","Week-40"));

        list_week.add(new WeekSelectionPojo("41","Week-41"));
        list_week.add(new WeekSelectionPojo("42","Week-42"));
        list_week.add(new WeekSelectionPojo("43","Week-43"));
        list_week.add(new WeekSelectionPojo("44","Week-44"));
        list_week.add(new WeekSelectionPojo("45","Week-45"));
        list_week.add(new WeekSelectionPojo("46","Week-46"));
        list_week.add(new WeekSelectionPojo("47","Week-47"));
        list_week.add(new WeekSelectionPojo("48","Week-48"));
        list_week.add(new WeekSelectionPojo("49","Week-49"));
        list_week.add(new WeekSelectionPojo("50","Week-50"));

        list_week.add(new WeekSelectionPojo("51","Week-51"));
        list_week.add(new WeekSelectionPojo("52","Week-52"));


        gridview = (GridView)findViewById(R.id.gridview);
        gridview.setAdapter(new WeekSelectionAdapter(list_week,WeeklySelectionActivity.this));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),list_week.get(position).getWeek_no(),Toast.LENGTH_SHORT).show();
                int year = 2020;
                int week = 50;
                Calendar calendar = Calendar.getInstance();
                //calendar.setWeekDate(year, week, Calendar.MONDAY);
                calendar.setTime(new Date());
                calendar.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(list_week.get(position).getWeek_no()));
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);//1
                System.out.println(formatter.format(calendar.getTime()));
                String start_date = formatter.format(calendar.getTime());
                //Toast.makeText(getApplicationContext(),formatter.format(calendar.getTime()),Toast.LENGTH_SHORT).show();
                calendar.add(Calendar.DAY_OF_WEEK, 6);
                String end_date = formatter.format(calendar.getTime());
                Intent intent=new Intent(WeeklySelectionActivity.this,WeekReportsActivity.class);
                intent.putExtra("start_date",end_date);
                intent.putExtra("end_date",start_date);
                startActivity(intent);
                //System.out.println(formatter.format(calendar.getTime()));
                //Toast.makeText(getApplicationContext(),start_date+"    "+formatter.format(calendar.getTime()),Toast.LENGTH_SHORT).show();
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

