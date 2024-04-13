package com.example.btlandroid;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Test extends AppCompatActivity {
    ImageView ivTest;
    TextView tvTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ivTest = findViewById(R.id.ivTest);
        getIntentProduct();
    }
    private void getIntentProduct() {
        Intent intent = getIntent();
        if (intent != null) {


            int productId = intent.getIntExtra("productId", -1);
            String productName = intent.getStringExtra("productName");
            int productQuantity = intent.getIntExtra("productQuantity", 0);
            double productPrice = intent.getDoubleExtra("productPrice", 0.0);
            String productImage = intent.getStringExtra("productImage");


            // Hiển thị hình ảnh sản phẩm nếu có
            if (productImage != null && !productImage.isEmpty()) {
                // Convert đường dẫn string của hình ảnh sang Uri
                Uri imageUri = Uri.parse(productImage);
                // Hiển thị hình ảnh lên ImageView
                ivTest.setImageURI(imageUri);
                tvTest.setText(imageUri.toString());
            }
        }
    }
}