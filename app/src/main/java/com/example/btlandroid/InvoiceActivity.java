package com.example.btlandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;

public class InvoiceActivity extends AppCompatActivity {
    private ImageView imageView;
    private Spinner actionMenu;
    private ArrayList<String> menuItems;
    private ListView listView;
    private ArrayList<Invoice> listInvoice;
    private AdapterInvoice adapterInvoice;
    private FloatingActionButton btncaigiday;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_layout);
        listView = findViewById(R.id.list_item);
        imageView = findViewById(R.id.ivCart);
        btncaigiday = findViewById(R.id.btn_add);
        actionMenu = findViewById(R.id.mySpinner);

        menuItems = new ArrayList<>();
        menuItems.add("Sắp xếp theo thời gian");
        menuItems.add("Sắp xếp theo giá trị");
        // Khởi tạo Adapter cho Spinner
        ArrayAdapter<String> adapterMenu = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, menuItems);
        adapterMenu.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Đặt Adapter cho Spinner
        actionMenu.setAdapter(adapterMenu);

        // Xử lý sự kiện khi một mục được chọn từ Spinner
        actionMenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Lấy mục được chọn
                String selectedItem = parent.getItemAtPosition(position).toString();
                //Toast.makeText(InvoiceActivity.this, "Selected item: " + selectedItem, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang Activity 2 khi nhấp vào ImageView
                Intent intent = new Intent(InvoiceActivity.this, ProductActivity.class);
                startActivity(intent);
            }
        });
        btncaigiday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InvoiceActivity.this, InvoiceDetailsActivity.class);
                startActivity(intent);
            }
        });

        listInvoice = new ArrayList<Invoice>();
        listInvoice.add(new Invoice(1,new Date(2023, 3, 10),100000,"Ha"));
        listInvoice.add(new Invoice(2, new Date(2023, 3, 11), 150000, "John"));
        listInvoice.add(new Invoice(3, new Date(2023, 3, 12), 200000, "Alice"));
        listInvoice.add(new Invoice(4, new Date(2023, 3, 13), 120000, "Bob"));
        listInvoice.add(new Invoice(5, new Date(2023, 3, 14), 180000, "Emily"));

        adapterInvoice = new AdapterInvoice(this,listInvoice);
        listView.setAdapter(adapterInvoice);
    }
}