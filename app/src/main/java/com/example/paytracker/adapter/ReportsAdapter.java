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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ReportsAdapter extends BaseAdapter {

    List<PaymentPojo> paymentPojo,searchbyjob;
    Context cnt;

    public ReportsAdapter(List<PaymentPojo> ar, Context cnt) {
        this.searchbyjob=ar;
        this.cnt = cnt;
        this.paymentPojo = new ArrayList<PaymentPojo>();
        this.paymentPojo.addAll(ar);
    }
    @Override
    public int getCount() {
        return paymentPojo.size();
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
        View obj2 = obj1.inflate(R.layout.row_reports, null);


        TextView tv_work_date = (TextView) obj2.findViewById(R.id.tv_work_date);
        tv_work_date.setText(paymentPojo.get(pos).getWork_date());

        TextView tv_start_time = (TextView) obj2.findViewById(R.id.tv_start_time);
        tv_start_time.setText(paymentPojo.get(pos).getStart_time());

        TextView tv_end_time = (TextView) obj2.findViewById(R.id.tv_end_time);
        tv_end_time.setText(paymentPojo.get(pos).getEnd_time());

        TextView tv_payment = (TextView) obj2.findViewById(R.id.tv_payment);
        tv_payment.setText(paymentPojo.get(pos).getPayment());

        TextView tv_company_name = (TextView) obj2.findViewById(R.id.tv_company_name);
        tv_company_name.setText(paymentPojo.get(pos).getCompany_name());

        TableRow tableRow1=(TableRow)obj2.findViewById(R.id.tableRow1);
        tableRow1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(cnt, PaySlipActivity.class);
                intent.putExtra("date",paymentPojo.get(pos).getWork_date());
                intent.putExtra("cname",paymentPojo.get(pos).getCompany_name());
                intent.putExtra("stime",paymentPojo.get(pos).getStart_time());
                intent.putExtra("etime",paymentPojo.get(pos).getEnd_time());
                intent.putExtra("thours",paymentPojo.get(pos).getTotal_hours());
                intent.putExtra("breaks",paymentPojo.get(pos).getBreak_time());
                intent.putExtra("sal",paymentPojo.get(pos).getSal_hour());
                intent.putExtra("tax",paymentPojo.get(pos).getTax());
                intent.putExtra("dtax",paymentPojo.get(pos).getPayment_deduct());
                intent.putExtra("net",paymentPojo.get(pos).getPayment());
                cnt.startActivity(intent);
            }
        });



        return obj2;
    }
    public void filter(String charText)
    {
        charText = charText.toLowerCase(Locale.getDefault());
        paymentPojo.clear();
        if (charText.length() == 0) {
            paymentPojo.addAll(searchbyjob);
        } else {
            for (PaymentPojo wp : searchbyjob) {
                if (wp.getCompany_name().toLowerCase(Locale.getDefault()).contains(charText) ) {
                    paymentPojo.add(wp);
                }
                /*else{
                    Toast.makeText(cnt, "There is no data related to selected Companyname", Toast.LENGTH_SHORT).show();
                }*/
            }
        }
        notifyDataSetChanged();
    }
}


