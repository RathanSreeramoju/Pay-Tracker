package com.example.paytracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.paytracker.R;
import com.example.paytracker.model.WeekSelectionPojo;

import java.util.List;

public class WeekSelectionAdapter extends BaseAdapter {
    List<String> ar;
    Context cnt;

    public WeekSelectionAdapter(List<String> ar, Context cnt) {
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
        View obj2 = obj1.inflate(R.layout.row_week_selection, null);

        TextView tv_time = (TextView) obj2.findViewById(R.id.tv_time);
        tv_time.setText(ar.get(pos));

        return obj2;
    }
}