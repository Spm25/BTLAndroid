package com.example.btlandroid;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<Product> listproduct;
    private Spinner actionMenu;
    private AdapterProduct adapterProduct;
    private ArrayList<String> menuItems;
    private ImageView imageView;

    IntentFilter intentFilter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView  = findViewById(R.id.list_item);
        actionMenu = findViewById(R.id.mySpinner);

        menuItems = new ArrayList<>();
        menuItems.add("Sắp xếp theo tên");
        menuItems.add("Sắp xếp theo giá");
        menuItems.add("Sắp xếp theo số lượng");
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
                Toast.makeText(MainActivity.this, "Selected item: " + selectedItem, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        listproduct = new ArrayList<Product>();
        listproduct.add(new Product(1,2,100,"https://png.pngtree.com/element_origin_min_pic/16/11/12/70085543ffb787b0212163a5c5ba6635.jpg"));
        listproduct.add(new Product(1,2,100,"https://png.pngtree.com/element_origin_min_pic/16/11/12/70085543ffb787b0212163a5c5ba6635.jpg"));
        listproduct.add(new Product(1,2,100,"https://png.pngtree.com/element_origin_min_pic/16/11/12/70085543ffb787b0212163a5c5ba6635.jpg"));
        listproduct.add(new Product(1,2,100,"https://png.pngtree.com/element_origin_min_pic/16/11/12/70085543ffb787b0212163a5c5ba6635.jpg"));

        adapterProduct = new AdapterProduct(this,listproduct);
        listView.setAdapter(adapterProduct);
        imageView = findViewById(R.id.ivList);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang Activity 2 khi nhấp vào ImageView
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });
    }
}