package com.example.paytracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class AdminDashBoardActivity extends AppCompatActivity {
    CardView cd_add_provinces,cd_view_provinces,cd_view_texas,cd_add_texas;
    Button btn_logout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Admin Dashboard");

        btn_logout=(Button)findViewById(R.id.btn_logout);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminDashBoardActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        cd_add_provinces=(CardView)findViewById(R.id.cd_add_provinces);
        cd_view_provinces=(CardView)findViewById(R.id.cd_view_provinces);
        cd_view_texas=(CardView)findViewById(R.id.cd_view_texas);
        cd_add_texas=(CardView)findViewById(R.id.cd_add_texas);

        cd_add_provinces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminDashBoardActivity.this,AddProvincesActivity.class);
                startActivity(intent);

            }
        });

        cd_view_provinces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(AdminDashBoardActivity.this,ViewProvincesActivity.class);
                startActivity(intent);

            }
        });

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
    @Override                                                                                                                    //add this method in your program
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        closeApp();
    }

    private void closeApp(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(AdminDashBoardActivity.this);
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


