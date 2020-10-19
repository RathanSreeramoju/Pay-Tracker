package com.example.paytracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.paytracker.adapter.MonthSelectionAdapter;
import com.example.paytracker.model.MonthSelectionPojo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MonthlySelectionActivity  extends AppCompatActivity {
    List<MonthSelectionPojo> list_month=new ArrayList<>();
    GridView gridview;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_selection);
        list_month.add(new MonthSelectionPojo("0", "Jan"));
        list_month.add(new MonthSelectionPojo("1", "Feb"));
        list_month.add(new MonthSelectionPojo("2", "Mar"));
        list_month.add(new MonthSelectionPojo("3", "Apr"));
        list_month.add(new MonthSelectionPojo("4", "May"));
        list_month.add(new MonthSelectionPojo("5", "June"));
        list_month.add(new MonthSelectionPojo("6", "July"));
        list_month.add(new MonthSelectionPojo("7", "Aug"));
        list_month.add(new MonthSelectionPojo("8", "Sep"));
        list_month.add(new MonthSelectionPojo("9", "Oct"));
        list_month.add(new MonthSelectionPojo("10", "Nov"));
        list_month.add(new MonthSelectionPojo("11", "Dec"));
        gridview = (GridView)findViewById(R.id.gridview);
        gridview.setAdapter(new MonthSelectionAdapter(list_month,MonthlySelectionActivity.this));

    }
}
