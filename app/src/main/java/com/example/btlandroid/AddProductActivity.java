package com.example.btlandroid;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import androidx.appcompat.widget.Toolbar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class AddProductActivity extends AppCompatActivity {

    private String imagePath;
    private EditText edProductName;
    private EditText edProductPrice;
    private EditText edProductQuantity;
    private Button btCreate;
    ImageView imageView;
    Toolbar mToolbar;

    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail_layout);

        mToolbar = findViewById(R.id.tb);
        setSupportActionBar(mToolbar);
        imageView = findViewById(R.id.iv_product);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageLibrary();
            }
        });

        imagePath = "";
        edProductName = findViewById(R.id.ed_product);
        edProductPrice = findViewById(R.id.ed_price);
        edProductQuantity = findViewById(R.id.ed_quantity);
        btCreate = findViewById(R.id.btCreate);

        btCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gọi phương thức để thêm sản phẩm
                // Lấy các giá trị từ các EditText
                String productName = edProductName.getText().toString().trim();
                int productQuantity = Integer.parseInt(edProductQuantity.getText().toString().trim());
                double productPrice = Double.parseDouble(edProductPrice.getText().toString().trim());

                // Gọi phương thức để thêm sản phẩm
                SQLiteManager db = new SQLiteManager(AddProductActivity.this);
                db.addProduct(productName, productQuantity, productPrice, imagePath);
            }
        });
    }

    private void openImageLibrary() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Cho phép truy cập vào thư viện ảnh?")
                .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Người dùng đồng ý, mở thư viện ảnh
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Chọn ảnh"), PICK_IMAGE_REQUEST);
                    }
                })
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Người dùng không đồng ý, không làm gì
                    }
                })
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            imageView.setImageURI(imageUri);

            // Lấy đường dẫn của ảnh từ Uri và lưu vào biến imagePath
            imagePath = imageUri.toString();
        }
    }
}