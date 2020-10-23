package com.example.paytracker.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.paytracker.EditWorkActivity;
import com.example.paytracker.R;
import com.example.paytracker.MyWorksActivity;
import com.example.paytracker.model.PaymentPojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GetMyWorksAdapter extends BaseAdapter {
    List<PaymentPojo> paymentPojo,searchpaymentpojo;
    Context cnt;

    public GetMyWorksAdapter(List<PaymentPojo> ar, Context cnt) {
        this.searchpaymentpojo=ar;
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
        View obj2 = obj1.inflate(R.layout.row_my_works, null);

        TextView tv_company_name = (TextView) obj2.findViewById(R.id.tv_company_name);
        tv_company_name.setText("Company name : "+paymentPojo.get(pos).getCompany_name());

        TextView tv_totalhours = (TextView) obj2.findViewById(R.id.tv_totalhours);
        tv_totalhours.setText("Total Hours : "+paymentPojo.get(pos).getTotal_hours());

        TextView tv_payment = (TextView) obj2.findViewById(R.id.tv_payment);
        tv_payment.setText("Payment : "+paymentPojo.get(pos).getPayment());

        TextView tv_workdate = (TextView) obj2.findViewById(R.id.tv_workdate);
        tv_workdate.setText(paymentPojo.get(pos).getWork_date());

        ImageView img_editwork = (ImageView) obj2.findViewById(R.id.img_editwork);

        img_editwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(cnt, EditWorkActivity.class);
                intent.putExtra("id",paymentPojo.get(pos).getId());
                intent.putExtra("uname",paymentPojo.get(pos).getUname());
                intent.putExtra("stime",paymentPojo.get(pos).getStart_time());
                intent.putExtra("etime",paymentPojo.get(pos).getEnd_time());
                intent.putExtra("thours",paymentPojo.get(pos).getTotal_hours());
                intent.putExtra("sal",paymentPojo.get(pos).getSal_hour());
                intent.putExtra("td",paymentPojo.get(pos).getPayment_deduct());
                intent.putExtra("ni",paymentPojo.get(pos).getPayment());
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
            paymentPojo.addAll(searchpaymentpojo);
        } else {
            for (PaymentPojo wp : searchpaymentpojo) {
                if (wp.getCompany_name().toLowerCase(Locale.getDefault()).contains(charText)) {
                    paymentPojo.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void filterbyDate(String charText)
    {
        charText = charText.toLowerCase(Locale.getDefault());
        paymentPojo.clear();
        if (charText.length() == 0) {
            paymentPojo.addAll(searchpaymentpojo);
        } else {
            for (PaymentPojo wp : searchpaymentpojo) {
                if (wp.getWork_date().toLowerCase(Locale.getDefault()).contains(charText) ) {
                    paymentPojo.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
