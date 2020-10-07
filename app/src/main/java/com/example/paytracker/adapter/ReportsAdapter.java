package com.example.paytracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.paytracker.R;
import com.example.paytracker.model.PaymentPojo;

import java.util.List;

public class ReportsAdapter extends BaseAdapter {

    List<PaymentPojo> ar;
    Context cnt;

    public ReportsAdapter(List<PaymentPojo> ar, Context cnt) {
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
        View obj2 = obj1.inflate(R.layout.row_reports, null);


        TextView tv_work_date = (TextView) obj2.findViewById(R.id.tv_work_date);
        tv_work_date.setText("Date : " + ar.get(pos).getWork_date());

        TextView tv_start_time = (TextView) obj2.findViewById(R.id.tv_start_time);
        tv_start_time.setText("Start Date : " + ar.get(pos).getStart_time());

        TextView tv_end_time = (TextView) obj2.findViewById(R.id.tv_end_time);
        tv_end_time.setText("End Date : " + ar.get(pos).getEnd_time());

        TextView tv_payment = (TextView) obj2.findViewById(R.id.tv_payment);
        tv_payment.setText("Payment : " + ar.get(pos).getEnd_time());

        TextView tv_payment_deduct = (TextView) obj2.findViewById(R.id.tv_payment_deduct);
        tv_payment_deduct.setText("Tax Deduct: " + ar.get(pos).getPayment_deduct());

        return obj2;
    }
}
