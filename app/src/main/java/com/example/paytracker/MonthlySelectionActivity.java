package com.example.paytracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.paytracker.adapter.MonthSelectionAdapter;
import com.example.paytracker.model.MonthSelectionPojo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Activity to display monthly reports of salary of user
 */
public class MonthlySelectionActivity  extends AppCompatActivity {
    List<MonthSelectionPojo> list_month=new ArrayList<>();
    ListView gridview;

    /**
     * Display monthly reports
     * @param savedInstanceState : parameter of type Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_selection);
        getSupportActionBar().setTitle(" Monthly Reports");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        gridview = (ListView)findViewById(R.id.gridview);
        gridview.setAdapter(new MonthSelectionAdapter(list_month,MonthlySelectionActivity.this));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Calendar c = Calendar.getInstance();
                c.set(2020, Integer.parseInt(list_month.get(position).getMonth_no()), 1); //------>
                c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String end_date = sdf.format(c.getTime());
                String start_date = "2020-" + (Integer.parseInt(list_month.get(position).getMonth_no()) + 1) + "-01";
                System.out.println(sdf.format(c.getTime()));
                // Toast.makeText(getApplicationContext(),sdf.format(c.getTime()),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MonthlySelectionActivity.this, ReportsActivity.class);
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


