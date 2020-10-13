package com.example.paytracker;

import com.example.paytracker.model.ProvincesPojo;
import com.example.paytracker.api.ApiService;
import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;



import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrationActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    ProgressDialog pd;
    TextView tv_dob, tv_skip;
    int mYear,mMonth,mDay;
    String DAY,MONTH,YEAR;
    LinearLayout ll_reg_form,ll_sal_per_hour,ll_job_title,ll_company_name,ll_img_upload,ll_provinces;
    Button btn_submit,btn_sal_prev,btn_sal_next,job_previous,job_next,btn_company_prev,btn_company_next,btn_submit_all;
    EditText et_first_name,et_last_name,et_email,et_username,et_password,et_sal,et_job_title,et_company_name;
    Button btn_upload_img,btn_provinces_submit;
    private static final String TAG = RegistrationActivity.class.getSimpleName();
    private static final int REQUEST_GALLERY_CODE = 200;
    private static final int READ_REQUEST_CODE = 300;
    private static final String SERVER_PATH = "http://paytracker.ca/";
    private Uri uri;
    Spinner spin_provinces;
    List<ProvincesPojo> a2;
    String[] proviences,ids;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    String date;
    int limit_year=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);



        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Registration");

        tv_dob=(TextView)findViewById(R.id.tv_dob);
        tv_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                datepicker();
            }
        });

        et_first_name=(EditText)findViewById(R.id.et_first_name);

        et_last_name=(EditText)findViewById(R.id.et_last_name);

        et_email=(EditText)findViewById(R.id.et_email);
        et_username=(EditText)findViewById(R.id.et_username);
        et_password=(EditText)findViewById(R.id.et_password);
        et_sal=(EditText)findViewById(R.id.et_sal);
        et_job_title=(EditText)findViewById(R.id.et_job_title);
        et_company_name=(EditText)findViewById(R.id.et_company_name);
        spin_provinces=(Spinner)findViewById(R.id.spin_provinces);
        getProvinces();

        tv_skip=(TextView)findViewById(R.id.tv_skip);
tv_skip.setOnClickListener( new View.OnClickListener()  {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(RegistrationActivity.this, LoginActivity.class);
        startActivity(intent);
    }
});



        btn_upload_img=(Button)findViewById(R.id.btn_upload_img);
        btn_upload_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
                openGalleryIntent.setType("image/*");
                startActivityForResult(openGalleryIntent, REQUEST_GALLERY_CODE);
            }
        });


        ll_reg_form=(LinearLayout)findViewById(R.id.ll_reg_form);
        ll_sal_per_hour=(LinearLayout)findViewById(R.id.ll_sal_per_hour);
        ll_job_title=(LinearLayout)findViewById(R.id.ll_job_title);
        ll_company_name=(LinearLayout)findViewById(R.id.ll_company_name);
        ll_provinces=(LinearLayout)findViewById(R.id.ll_provinces);

        btn_submit=(Button)findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(et_first_name.getText().toString().equals(""))
                {
                    Toast.makeText(RegistrationActivity.this, "Enter First Name", Toast.LENGTH_LONG).show();
                }
                else if(et_last_name.getText().toString().equals(""))
                {
                    Toast.makeText(RegistrationActivity.this, "Enter Lastname", Toast.LENGTH_LONG).show();
                }

                else if(et_email.getText().toString().equals(""))
                {
                    Toast.makeText(RegistrationActivity.this, "Enter Email", Toast.LENGTH_LONG).show();
                }

                else if(tv_dob.getText().toString().equals(""))
                {
                    Toast.makeText(RegistrationActivity.this, "Select DOB", Toast.LENGTH_LONG).show();
                }

                else if(et_password.getText().toString().equals(""))
                {
                    Toast.makeText(RegistrationActivity.this, "Enter Password", Toast.LENGTH_LONG).show();
                }


                else {
                    uploadImageToServer();
                    ll_reg_form.setVisibility(View.GONE);
                    ll_company_name.setVisibility(View.VISIBLE);
                }


                //Toast.makeText(RegistrationActivity.this, "Submitted to server", Toast.LENGTH_SHORT).show();



            }
        });

        btn_company_prev=(Button)findViewById(R.id.btn_company_prev);
        btn_company_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

          //      ll_reg_form.setVisibility(View.VISIBLE);
          //      ll_company_name.setVisibility(View.GONE);
                Intent intent=new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);


            }
        });

        btn_company_next=(Button)findViewById(R.id.btn_company_next);
        btn_company_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(et_company_name.getText().toString().equals(""))
                {
                    Toast.makeText(RegistrationActivity.this, "Enter Company Name", Toast.LENGTH_SHORT).show();
                }
                else {
                    ll_job_title.setVisibility(View.VISIBLE);
                    ll_company_name.setVisibility(View.GONE);
                }


                }
        });

        job_previous=(Button)findViewById(R.id.job_previous);
        job_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_company_name.setVisibility(View.VISIBLE);
                ll_job_title.setVisibility(View.GONE);

            }
        });
        job_next=(Button)findViewById(R.id.job_next);
        job_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_job_title.getText().toString().equals("")) {
                    Toast.makeText(RegistrationActivity.this, "Job Title", Toast.LENGTH_SHORT).show();
                } else {

                    ll_sal_per_hour.setVisibility(View.VISIBLE);
                    ll_job_title.setVisibility(View.GONE);

                }
            }
        });


        btn_sal_prev=(Button)findViewById(R.id.btn_sal_prev);
        btn_sal_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ll_job_title.setVisibility(View.VISIBLE);
                ll_sal_per_hour.setVisibility(View.GONE);

            }
        });
        btn_sal_next=(Button)findViewById(R.id.btn_sal_next);
        btn_sal_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_sal.getText().toString().equals(""))
                {
                    Toast.makeText(RegistrationActivity.this, "Enter Salary Per Hour", Toast.LENGTH_SHORT).show();
                }
                else {


                    ll_sal_per_hour.setVisibility(View.GONE);
                    ll_provinces.setVisibility(View.VISIBLE);
                }

            }
        });

        btn_provinces_submit=(Button)findViewById(R.id.btn_provinces_submit);
        btn_provinces_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(RegistrationActivity.this, "Test", Toast.LENGTH_SHORT).show();

               /* ll_sal_per_hour.setVisibility(View.GONE);
                ll_provinces.setVisibility(View.VISIBLE);
*/
                if(spin_provinces.getSelectedItem().toString().equals("Select Provinces"))
                {
                    Toast.makeText(RegistrationActivity.this, "Select Province", Toast.LENGTH_SHORT).show();
                }
                else {
                    submitData();

                }
            }
        });


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, RegistrationActivity.this);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_GALLERY_CODE && resultCode == Activity.RESULT_OK){
            uri = data.getData();
            if(EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                String filePath = getRealPathFromURIPath(uri, RegistrationActivity.this);
                file = new File(filePath);

            }else{
                EasyPermissions.requestPermissions(this, getString(R.string.read_file), READ_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        }
    }
    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }
    File file;
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if(uri != null){
            String filePath = getRealPathFromURIPath(uri, RegistrationActivity.this);
            file = new File(filePath);
            // uploadImageToServer();
        }
    }
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d(TAG, "Permission has been denied");

    }

    private void uploadImageToServer(){
        pd=new ProgressDialog(RegistrationActivity.this);
        pd.setTitle("Loading");
        pd.show();
        Map<String, String> map = new HashMap<>();
        map.put("fname",et_first_name.getText().toString());
        map.put("lname",et_last_name.getText().toString());
        map.put("email",et_email.getText().toString());
        map.put("dob",tv_dob.getText().toString());
        // map.put("uname",et_username.getText().toString());
        map.put("pwd",et_password.getText().toString());

        RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_PATH)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService uploadImage = retrofit.create(ApiService.class);
        Call<ResponseData> fileUpload = uploadImage.userRegistration(fileToUpload, map);
        fileUpload.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                pd.dismiss();
                Toast.makeText(RegistrationActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                //finish();
            }
            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(RegistrationActivity.this, "Error"+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    private void submitData() {
        String sal = et_sal.getText().toString();
        String company_name = et_company_name.getText().toString();
        String jobtitle = et_job_title.getText().toString();
        String email = et_email.getText().toString();

        pd = new ProgressDialog(RegistrationActivity.this);
        pd.setMessage("Loading....");
        pd.show();

        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseData> call = service.add_jobprofile(company_name,jobtitle,sal,email,ids[spin_provinces.getSelectedItemPosition()]);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                pd.dismiss();
                if (response.body().status.equals("true")) {
                    Toast.makeText(RegistrationActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(RegistrationActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(RegistrationActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(RegistrationActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    private void getProvinces() {
        ApiService apiService = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<ProvincesPojo>> call = apiService.get_provinces();
        call.enqueue(new Callback<List<ProvincesPojo>>() {
            @Override
            public void onResponse(Call<List<ProvincesPojo>> call, Response<List<ProvincesPojo>> response) {
                a2 = response.body();
                Log.d("TAG", "Response = " + a2);
                proviences = new String[a2.size() + 1];
                ids=new String[a2.size()+1];
                ids[0] = "-1";
                proviences[0] = "Select Provinces";
                for (int i = 0; i < a2.size(); i++) {
                    proviences[i + 1] = a2.get(i).getProvince_name();
                    ids[i + 1] = a2.get(i).getId();

                }
                ArrayAdapter aa = new ArrayAdapter(RegistrationActivity.this, android.R.layout.simple_spinner_item, proviences);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin_provinces.setAdapter(aa);
                spin_provinces.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                        if (pos > 0) {
                            // Toast.makeText(getApplicationContext(), cities[pos], Toast.LENGTH_LONG).show();
                            //getAreas(state,cities[pos]);
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<ProvincesPojo>> call, Throwable t) {
                Log.d("TAG", "Response = " + t.toString());
            }
        });
    }

    public void datepicker() {
        Calendar cal_limit = Calendar.getInstance();
        limit_year=cal_limit.get(Calendar.YEAR)-18;

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(String.valueOf(RegistrationActivity.this), "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
                date = month + "/" + day + "/" + year;
                DAY = month + "";
                MONTH = day + "";
                YEAR = year + "";
                tv_dob.setText(date);
            }
        };
        DatePickerDialog dialog = new DatePickerDialog(
                RegistrationActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, limit_year, 0, 1);
        Calendar c = Calendar.getInstance();
        c.set(limit_year, 11, 31);//Year,Mounth -1,Day
        DatePicker datePicker = dialog.getDatePicker();

        datePicker.setMaxDate(c.getTimeInMillis());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
    /*public void datepicker() {

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR -18);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        DAY = dayOfMonth + "";
                        MONTH = monthOfYear + 1 + "";
                        YEAR = year + "";

                        //tv_dob.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        tv_dob.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }*/

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
}
