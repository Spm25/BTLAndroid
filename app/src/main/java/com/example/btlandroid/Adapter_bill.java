package com.example.btlandroid;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Adapter_bill extends BaseAdapter {
    private Activity activity;
    private ArrayList<Bill> data;
    private LayoutInflater inflate;
    public Adapter_bill(Activity activity,ArrayList<Bill> item){
        this.activity = activity;
        this.data = item;
        inflate = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v == null){
            v = inflate.inflate(R.layout.bill_layout,null);
        }

        TextView id = v.findViewById(R.id.txtid);
        TextView date = v.findViewById(R.id.txtdate);
        TextView price = v.findViewById(R.id.txtprice);
        TextView create = v.findViewById(R.id.txtcreate);
        id.setText(String.valueOf(data.get(position).getId()));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = dateFormat.format(data.get(position).getDate());
        date.setText(dateString);
        price.setText(String.valueOf(data.get(position).getPrice()));
        create.setText(data.get(position).getCreate());
        return v;
    }
}
