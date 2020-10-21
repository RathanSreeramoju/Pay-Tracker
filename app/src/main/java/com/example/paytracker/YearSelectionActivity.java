package com.example.paytracker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;


import com.example.paytracker.adapter.YearSelectionAdapter;
import com.example.paytracker.model.YearSelectionPojo;

import java.util.ArrayList;
import java.util.List;


public class YearSelectionActivity  extends AppCompatActivity {
    List<YearSelectionPojo> list_year=new ArrayList<>();
    ListView gridview;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_selection);

        getSupportActionBar().setTitle(" Year Reports");


        list_year.add(new YearSelectionPojo("2020", "2020"));
        list_year.add(new YearSelectionPojo("2019", "2019"));
        list_year.add(new YearSelectionPojo("2018", "2018"));
        gridview = (ListView)findViewById(R.id.gridview);
        gridview.setAdapter(new YearSelectionAdapter(list_year,YearSelectionActivity.this));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String start_date=list_year.get(position).getYear_no()+"-01-01";
                String end_date=list_year.get(position).getYear_no()+"-12-31";
                Intent intent=new Intent(YearSelectionActivity.this,ReportsActivity.class);
                intent.putExtra("start_date",end_date);
                intent.putExtra("end_date",start_date);
                startActivity(intent);
            }
        });
    }
}
