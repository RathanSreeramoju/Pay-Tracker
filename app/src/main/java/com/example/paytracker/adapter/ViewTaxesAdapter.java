package com.example.paytracker.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.example.paytracker.R;

import com.example.paytracker.EditTaxesActivity;
import com.example.paytracker.ResponseData;
import com.example.paytracker.ViewTaxActivity;
import com.example.paytracker.api.ApiService;
import com.example.paytracker.api.RetroClient;
import com.example.paytracker.model.ViewTaxesPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewTaxesAdapter extends BaseAdapter {

    List<ViewTaxesPojo> ar;
    Context cnt;

    public ViewTaxesAdapter(List<ViewTaxesPojo> ar, Context cnt) {
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
        View obj2 = obj1.inflate(R.layout.list__view_taxes, null);


        TextView tv_name = (TextView) obj2.findViewById(R.id.tv_name);
        tv_name.setText("Province Name  :" + ar.get(pos).getProvinces_name());

        TextView tv_amount = (TextView) obj2.findViewById(R.id.tv_amount);
        tv_amount.setText("Amount  :" + ar.get(pos).getTax());

        Button btn_edit=(Button)obj2.findViewById(R.id.btn_edit);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(cnt, EditTaxesActivity.class);
                intent.putExtra("id",ar.get(pos).getId());
                intent.putExtra("Provinces_name",ar.get(pos).getProvinces_name());
                intent.putExtra("tax",ar.get(pos).getTax());
                cnt.startActivity(intent);

            }
        });


        Button btn_delete=(Button)obj2.findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                serverData(ar.get(pos).getId());


            }
        });

        return obj2;

    }

    ProgressDialog progressDialog;
    public void serverData(String id){
        progressDialog = new ProgressDialog(cnt);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseData> call = service.delete_taxes(id);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(cnt,"Server issue",Toast.LENGTH_SHORT).show();
                }else {
                    /*Intent intent=new Intent(cnt, ViewProvincesActivity.class);
                    cnt.startActivity(intent);
                    Toast.makeText(cnt,"Deleted successfully",Toast.LENGTH_SHORT).show();*/

                }
            }
            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(cnt, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
