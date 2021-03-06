package com.example.paytracker.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paytracker.EditJobActivity;
import com.example.paytracker.R;
import com.example.paytracker.model.GetAllJobProfilePojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GetAllJobProfileAdapter extends BaseAdapter {

    List<GetAllJobProfilePojo> getAllJobProfilePojos,searchpojo;
    Context cnt;
    public GetAllJobProfileAdapter(List<GetAllJobProfilePojo> ar, Context cnt)
    {

        this.searchpojo=ar;
        this.cnt = cnt;
        this.getAllJobProfilePojos = new ArrayList<GetAllJobProfilePojo>();
        this.getAllJobProfilePojos.addAll(ar);
    }
    @Override
    public int getCount() {
        return getAllJobProfilePojos.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int pos, View view, ViewGroup viewGroup)
    {
        LayoutInflater obj1 = (LayoutInflater)cnt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View obj2=obj1.inflate(R.layout.list_get_all_jobs_profile,null);


        TextView tv_company_name=(TextView)obj2.findViewById(R.id.tv_company_name);
        tv_company_name.setText("Company Name : "+getAllJobProfilePojos.get(pos).getCompanyname());

        TextView tv_job_title=(TextView)obj2.findViewById(R.id.tv_job_title);
        tv_job_title.setText("Job Title : "+getAllJobProfilePojos.get(pos).getJobtitle());

        TextView tv_sal_hour=(TextView)obj2.findViewById(R.id.tv_sal_hour);
        tv_sal_hour.setText("Salary Per Hour : "+getAllJobProfilePojos.get(pos).getSalaryperhour());

        TextView tv_prov_name=(TextView)obj2.findViewById(R.id.tv_prov_name);
        tv_prov_name.setText("Provience : "+getAllJobProfilePojos.get(pos).getProvince_name());

        ImageView im_edit=(ImageView)obj2.findViewById(R.id.im_edit);
        im_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(cnt, "Edit Option Clicked", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(cnt, EditJobActivity.class);
                intent.putExtra("cname",getAllJobProfilePojos.get(pos).getCompanyname());
                intent.putExtra("ctitle",getAllJobProfilePojos.get(pos).getJobtitle());
                intent.putExtra("csalary",getAllJobProfilePojos.get(pos).getSalaryperhour());
                intent.putExtra("cprovince",getAllJobProfilePojos.get(pos).getProvince_name());

                intent.putExtra("jid",getAllJobProfilePojos.get(pos).getJid());
                cnt.startActivity(intent);
            }
        });

//        ImageView im_delete=(ImageView)obj2.findViewById(R.id.im_delete);
//        im_delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(cnt, "Delete Option Clicked", Toast.LENGTH_SHORT).show();
//               // alertDioluge(getAllTeams.get(pos).getTeam_id());
//            }
//        });


        return obj2;
    }
    public void filter(String charText)
    {
        charText = charText.toLowerCase(Locale.getDefault());
        getAllJobProfilePojos.clear();
        if (charText.length() == 0) {
            getAllJobProfilePojos.addAll(searchpojo);
        } else {
            for (GetAllJobProfilePojo wp : searchpojo) {
                if (wp.getCompanyname().toLowerCase(Locale.getDefault()).contains(charText)) {
                    getAllJobProfilePojos.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}