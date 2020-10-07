package com.example.paytracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

public class BaseActivity extends AppCompatActivity {

    protected void showMessage(Context cnt, String msg){
        Toast.makeText(cnt, msg, Toast.LENGTH_LONG).show();
    }
}