package com.example.paytracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class AdminDashBoardActivity extends AppCompatActivity {
    CardView cd_add_provinces,cd_view_provinces,cd_view_texas,cd_add_texas;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Admin Dashboard");

        cd_add_provinces=(CardView)findViewById(R.id.cd_add_provinces);
        cd_view_provinces=(CardView)findViewById(R.id.cd_view_provinces);
        cd_view_texas=(CardView)findViewById(R.id.cd_view_texas);
        cd_add_texas=(CardView)findViewById(R.id.cd_add_texas);




        cd_add_texas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminDashBoardActivity.this,AddTaxActivity.class);
                startActivity(intent);

            }
        });

        cd_view_texas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminDashBoardActivity.this,ViewTaxActivity.class);
                startActivity(intent);

            }
        });
    }
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


