package com.example.paytracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_selection);

        getSupportActionBar().setTitle(" Year Reports");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        list_year.add(new YearSelectionPojo("2020", "2020"));
        list_year.add(new YearSelectionPojo("2019", "2019"));
        list_year.add(new YearSelectionPojo("2018", "2018"));
        list_year.add(new YearSelectionPojo("2017", "2017"));
        list_year.add(new YearSelectionPojo("2016", "2016"));
        list_year.add(new YearSelectionPojo("2015", "2015"));
        gridview = (ListView)findViewById(R.id.gridview);
        gridview.setAdapter(new YearSelectionAdapter(list_year,YearSelectionActivity.this));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String start_date = list_year.get(position).getYear_no() + "-01-01";
                String end_date = list_year.get(position).getYear_no() + "-12-31";
                Intent intent = new Intent(YearSelectionActivity.this, YearlyReportsActivity.class);
                intent.putExtra("start_date", end_date);
                intent.putExtra("end_date", start_date);
                startActivity(intent);

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

