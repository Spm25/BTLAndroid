package com.example.btlandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

public class BillActivity extends AppCompatActivity {

    private ListView lst;
    private ArrayList<Bill> listbill;
    private Adapter_bill adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bill_activity);
        lst = findViewById(R.id.lstv1);

        listbill = new ArrayList<Bill>();
        listbill.add(new Bill(1,new Date(2023, 3, 10),100000,"Ha"));

        adapter = new Adapter_bill(this,listbill);
        lst.setAdapter(adapter);
    }
}