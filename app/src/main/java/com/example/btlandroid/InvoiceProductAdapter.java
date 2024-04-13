package com.example.btlandroid;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class InvoiceProductAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<Product> data;
    private LayoutInflater inflater;

    public InvoiceProductAdapter(Activity activity, ArrayList<Product> data) {
        this.activity = activity;
        this.data = data;
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.lvitem_product_layout, null);
            holder = new ViewHolder();
            holder.tvTen = convertView.findViewById(R.id.tvten);
            holder.tvSL = convertView.findViewById(R.id.tvsl);
            holder.tvGia = convertView.findViewById(R.id.tvgia);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Product product = data.get(position);
        holder.tvTen.setText(product.getName());
        holder.tvSL.setText(String.valueOf(product.getAmount()));
        holder.tvGia.setText(String.valueOf(product.getPrice()));

        return convertView;
    }

    private static class ViewHolder {
        TextView tvTen;
        TextView tvSL;
        TextView tvGia;
    }
}
