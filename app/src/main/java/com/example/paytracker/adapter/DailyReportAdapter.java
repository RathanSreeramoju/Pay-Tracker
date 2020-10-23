package com.example.paytracker.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.paytracker.PaySlipActivity;
import com.example.paytracker.R;

import com.example.paytracker.model.PaymentPojo;

import java.util.List;

public class DailyReportAdapter extends BaseAdapter {
    List<PaymentPojo> ar;
    Context cnt;

    public DailyReportAdapter(List<PaymentPojo> ar, Context cnt) {
        this.ar = ar;
        this.cnt = cnt;
    }

    @Override
    public int getCount() {
        return ar.size();
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
    public View getView(final int pos, View view, ViewGroup viewGroup) {
        LayoutInflater obj1 = (LayoutInflater) cnt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View obj2 = obj1.inflate(R.layout.row_dailyreports, null);

        TextView tv_work_date = (TextView) obj2.findViewById(R.id.tv_work_date);
        tv_work_date.setText(ar.get(pos).getWork_date());

        TextView tv_start_time = (TextView) obj2.findViewById(R.id.tv_start_time);
        tv_start_time.setText(ar.get(pos).getStart_time());

        TextView tv_end_time = (TextView) obj2.findViewById(R.id.tv_end_time);
        tv_end_time.setText(ar.get(pos).getEnd_time());

        TextView tv_payment = (TextView) obj2.findViewById(R.id.tv_payment);
        tv_payment.setText(ar.get(pos).getPayment()+"$");


        TextView ps_date = (TextView) obj2.findViewById(R.id.ps_date);
        ps_date.setText("Date : "+ar.get(pos).getWork_date());


        TextView ps_cname = (TextView) obj2.findViewById(R.id.ps_cname);
        ps_cname.setText("Company Name : "+ar.get(pos).getCompany_name());

        TextView ps_stime = (TextView) obj2.findViewById(R.id.ps_stime);
        ps_stime.setText("Start Time : "+ar.get(pos).getStart_time());

        TextView ps_etime = (TextView) obj2.findViewById(R.id.ps_etime);
        ps_etime.setText("End Time : "+ar.get(pos).getEnd_time());

        TextView ps_totalhours = (TextView) obj2.findViewById(R.id.ps_totalhours);
        ps_totalhours.setText("Total Hours : "+ar.get(pos).getTotal_hours());

        TextView ps_breaks = (TextView) obj2.findViewById(R.id.ps_breaks);
        ps_breaks.setText("Breaks : "+ar.get(pos).getBreak_time()+"Mins");


        TextView ps_sph = (TextView) obj2.findViewById(R.id.ps_sph);
        ps_sph.setText("Salary Per Hour : "+ar.get(pos).getSal_hour()+"$");

        TextView ps_ptax = (TextView) obj2.findViewById(R.id.ps_ptax);
        ps_ptax.setText("Provience Tax : "+ar.get(pos).getTax()+"%");

        TextView ps_tdeductions = (TextView) obj2.findViewById(R.id.ps_tdeductions);
        ps_tdeductions.setText("Total Deductions : "+ar.get(pos).getPayment_deduct()+"$");

        TextView ps_netincome = (TextView) obj2.findViewById(R.id.ps_netincome);
        ps_netincome.setText("Net Income : "+ar.get(pos).getPayment()+"$");


//
//
//        ps_tdeductions=(TextView)findViewById(R.id.ps_tdeductions);
//        ps_tdeductions.setText("Total Deductions : "+getIntent().getStringExtra("dtax")+"$");
//
//        ps_netincome=(TextView)findViewById(R.id.ps_netincome);
//        ps_netincome.setText("Net Income : "+getIntent().getStringExtra("net")+"$");
//
//

        TableRow tableRow1=(TableRow)obj2.findViewById(R.id.tableRow1);
        tableRow1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(cnt, PaySlipActivity.class);
                intent.putExtra("date",ar.get(pos).getWork_date());
                intent.putExtra("cname",ar.get(pos).getCompany_name());
                intent.putExtra("stime",ar.get(pos).getStart_time());
                intent.putExtra("etime",ar.get(pos).getEnd_time());
                intent.putExtra("thours",ar.get(pos).getTotal_hours());
                intent.putExtra("breaks",ar.get(pos).getBreak_time());
                intent.putExtra("sal",ar.get(pos).getSal_hour());
                intent.putExtra("tax",ar.get(pos).getTax());
                intent.putExtra("dtax",ar.get(pos).getPayment_deduct());
                intent.putExtra("net",ar.get(pos).getPayment());
                cnt.startActivity(intent);
            }
        });

        return obj2;
    }
}
