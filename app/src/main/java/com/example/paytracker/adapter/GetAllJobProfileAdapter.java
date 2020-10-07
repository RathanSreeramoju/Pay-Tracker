package com.example.paytracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paytracker.R;
import com.example.paytracker.model.GetAllJobProfilePojo;

import java.util.List;

public class GetAllJobProfileAdapter extends BaseAdapter {


    List<GetAllJobProfilePojo> getAllJobProfilePojos;
    Context cnt;
    public GetAllJobProfileAdapter(List<GetAllJobProfilePojo> ar, Context cnt)
    {
        this.getAllJobProfilePojos=ar;
        this.cnt=cnt;
    }
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int pos, View view, ViewGroup viewGroup) {

        LayoutInflater obj1 = (LayoutInflater)cnt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View obj2=obj1.inflate(R.layout.list_get_all_jobs_profile,null);


        TextView tv_company_name=(TextView)obj2.findViewById(R.id.tv_company_name);
        tv_company_name.setText(getAllJobProfilePojos.get(pos).getCompanyname());

        TextView tv_job_title=(TextView)obj2.findViewById(R.id.tv_job_title);
        tv_job_title.setText(getAllJobProfilePojos.get(pos).getJobtitle());

        TextView tv_sal_hour=(TextView)obj2.findViewById(R.id.tv_sal_hour);
        tv_sal_hour.setText(getAllJobProfilePojos.get(pos).getSalaryperhour());

        TextView tv_prov_name=(TextView)obj2.findViewById(R.id.tv_prov_name);
        tv_prov_name.setText(getAllJobProfilePojos.get(pos).getProvince_name());

        ImageView im_edit=(ImageView)obj2.findViewById(R.id.im_edit);
        im_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(cnt, "Edit Option Clicked", Toast.LENGTH_SHORT).show();
              /*  Intent intent=new Intent(cnt, EditTeamsActivity.class);
                intent.putExtra("name",getAllTeams.get(pos).getT_name());
                intent.putExtra("image",getAllTeams.get(pos).getT_logo());
                intent.putExtra("id",getAllTeams.get(pos).getTeam_id());
                cnt.startActivity(intent);
                ((Activity)cnt).finish();*/
            }
        });

        ImageView im_delete=(ImageView)obj2.findViewById(R.id.im_delete);
        im_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(cnt, "Delete Option Clicked", Toast.LENGTH_SHORT).show();
                // alertDioluge(getAllTeams.get(pos).getTeam_id());
            }
        });


        return obj2;

    }
}