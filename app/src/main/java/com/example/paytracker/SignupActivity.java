package com.example.paytracker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import pub.devrel.easypermissions.EasyPermissions;

public class SignupActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    ProgressDialog pd;
    LinearLayout ll_reg_form, ll_sal_per_hour, ll_job_title, ll_company_name, ll_img_upload;
    Button btn_submit, btn_sal_prev, btn_sal_next, job_previous, job_next, btn_company_prev, btn_company_next, btn_submit_all;
    EditText et_ename, et_email, et_phno, et_username, et_password, et_sal, et_job_title, et_company_name;
    Button btn_upload_img;
    private static final String TAG = RegistrationActivity.class.getSimpleName();
    private static final int REQUEST_GALLERY_CODE = 200;
    private static final int READ_REQUEST_CODE = 300;
    private static final String SERVER_PATH = "http://cegepclm.com/";
    private Uri uri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Registration");

        et_ename = (EditText) findViewById(R.id.et_ename);
        et_email = (EditText) findViewById(R.id.et_email);
        et_phno = (EditText) findViewById(R.id.et_phno);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        et_sal = (EditText) findViewById(R.id.et_sal);
        et_job_title = (EditText) findViewById(R.id.et_job_title);
        et_company_name = (EditText) findViewById(R.id.et_company_name);

        btn_upload_img = (Button) findViewById(R.id.btn_upload_img);
        btn_upload_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
                openGalleryIntent.setType("image/*");
                startActivityForResult(openGalleryIntent, REQUEST_GALLERY_CODE);
            }
        });


        ll_reg_form = (LinearLayout) findViewById(R.id.ll_reg_form);
        ll_sal_per_hour = (LinearLayout) findViewById(R.id.ll_sal_per_hour);
        ll_job_title = (LinearLayout) findViewById(R.id.ll_job_title);
        ll_company_name = (LinearLayout) findViewById(R.id.ll_company_name);
        ll_img_upload = (LinearLayout) findViewById(R.id.ll_img_upload);

        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ll_reg_form.setVisibility(View.GONE);
                ll_sal_per_hour.setVisibility(View.VISIBLE);


            }
        });

        btn_sal_prev = (Button) findViewById(R.id.btn_sal_prev);
        btn_sal_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ll_reg_form.setVisibility(View.VISIBLE);
                ll_sal_per_hour.setVisibility(View.GONE);

            }
        });
        btn_sal_next = (Button) findViewById(R.id.btn_sal_next);
        btn_sal_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ll_sal_per_hour.setVisibility(View.GONE);
                ll_job_title.setVisibility(View.VISIBLE);

            }
        });

        job_previous = (Button) findViewById(R.id.job_previous);
        job_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ll_sal_per_hour.setVisibility(View.VISIBLE);
                ll_job_title.setVisibility(View.GONE);

            }
        });
        job_next = (Button) findViewById(R.id.job_next);
        job_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ll_company_name.setVisibility(View.VISIBLE);
                ll_job_title.setVisibility(View.GONE);

            }
        });

        btn_company_prev = (Button) findViewById(R.id.btn_company_prev);
        btn_company_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ll_job_title.setVisibility(View.VISIBLE);
                ll_company_name.setVisibility(View.GONE);

            }
        });

        btn_company_next = (Button) findViewById(R.id.btn_company_next);
        btn_company_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ll_img_upload.setVisibility(View.VISIBLE);
                ll_company_name.setVisibility(View.GONE);


            }
        });

        btn_submit_all = (Button) findViewById(R.id.btn_submit_all);
        btn_submit_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*Intent intent=new Intent(RegistrationActivity.this,LoginActivity.class);
                startActivity(intent);*/
                uploadImageToServer();
                finish();

            }
        });

        private void uploadImageToServer () {
        }

    }
}