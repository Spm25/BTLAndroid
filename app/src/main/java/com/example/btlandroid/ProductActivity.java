package com.example.btlandroid;

import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProductActivity extends AppCompatActivity {
    private ListView listProduct;
    private ArrayList<Product> productList ;
    private Spinner mnSort;
    private AdapterProduct adapterProduct;
    private ArrayList<String> menuItems;
    private ImageView ivInvoice;
    private static List<Product> data;
    private FloatingActionButton btnAdd;
    IntentFilter intentFilter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_layout);
        listProduct = findViewById(R.id.list_item);
        mnSort = findViewById(R.id.mySpinner);
        btnAdd = findViewById(R.id.btn_add);

        menuItems = new ArrayList<>();
        menuItems.add(0,"Sắp xếp theo id");
        menuItems.add(1,"Sắp xếp theo số lượng");
        menuItems.add(2,"Sắp xếp theo giá");
        // Khởi tạo Adapter cho Spinner
        ArrayAdapter<String> adapterMenu = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, menuItems);
        adapterMenu.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Đặt Adapter cho Spinner
        mnSort.setAdapter(adapterMenu);

        // Xử lý sự kiện khi một mục được chọn từ Spinner
        mnSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: // Sắp xếp theo id
                        Collections.sort(productList, Comparator.comparingInt(Product::getId));
                        break;
                    case 1: // Sắp xếp theo amount
                        Collections.sort(productList, Comparator.comparingInt(Product::getAmount));
                        break;
                    case 2: // Sắp xếp theo giá
                        Collections.sort(productList, Comparator.comparingDouble(Product::getAmount));
                        break;
                }
                // Cập nhật ListView sau khi đã sắp xếp
                adapterProduct.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        productList  = readAllProductsFromDB();

        adapterProduct = new AdapterProduct(this,productList );
        listProduct.setAdapter(adapterProduct);
        ivInvoice = findViewById(R.id.ivList);
        ivInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang Activity 2 khi nhấp vào ImageView
                Intent intent = new Intent(ProductActivity.this, InvoiceActivity.class);
                startActivity(intent);
            }
        });


        listProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product product = productList.get(position);
                Intent intent = new Intent(ProductActivity.this, ProductDetailActivity.class);

                // Truyền thông tin của sản phẩm được chọn sang AddProductActivity
                intent.putExtra("productId", product.getId());
                intent.putExtra("productName", product.getName());
                intent.putExtra("productQuantity", product.getAmount());
                intent.putExtra("productPrice", product.getPrice());
                intent.putExtra("productImage", product.getImage());

                startActivity(intent);
            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductActivity.this, ProductDetailActivity.class);
                intent.putExtra("btnXoa","false");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        productList  = readAllProductsFromDB();

        adapterProduct = new AdapterProduct(this,productList );
        listProduct.setAdapter(adapterProduct);
    }

    // Phương thức để đọc tất cả các sản phẩm từ cơ sở dữ liệu
    private ArrayList<Product> readAllProductsFromDB() {
        ArrayList<Product> productList = new ArrayList<>();
        SQLiteManager db = new SQLiteManager(ProductActivity.this);
        Cursor cursor = db.readAllProducts();
        while(cursor.moveToNext()){
            int id = Integer.parseInt(cursor.getString(0));
            String name = cursor.getString(1);
            int amount = Integer.parseInt(cursor.getString(2));
            double price = Double.parseDouble(cursor.getString(3));
            byte[] image = cursor.getBlob(4); // Chuyển dữ liệu ảnh từ BLOB thành mảng byte
            productList.add(new Product(id, name, amount, price, image));
        }
        return productList;
    }

}