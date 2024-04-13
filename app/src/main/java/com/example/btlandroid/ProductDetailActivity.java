package com.example.btlandroid;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class ProductDetailActivity extends AppCompatActivity {

    private String imagePath;
    private EditText edProductName;
    private EditText edProductPrice;
    private EditText edProductQuantity;
    private Button btSave;
    private Button btDelete;
    ImageView ivProduct;
    ImageView ivQR;
    Toolbar mToolbar;
    boolean isAddProduct = false;
    Product thisProduct;


    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail_layout);

        mToolbar = findViewById(R.id.tb);
        setSupportActionBar(mToolbar);
        ivProduct = findViewById(R.id.iv_product);
        ivProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageLibrary();
            }
        });

        imagePath = "";
        edProductName = findViewById(R.id.ed_product);
        edProductPrice = findViewById(R.id.ed_price);
        edProductQuantity = findViewById(R.id.ed_quantity);
        ivQR = findViewById(R.id.ivQR);
        btSave = findViewById(R.id.btnSave);
        btDelete = findViewById(R.id.btDelete);

        getIntentProduct();

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy các giá trị từ các EditText
                String productName = edProductName.getText().toString().trim();
                String productPriceText = edProductPrice.getText().toString().trim();
                String productQuantityText = edProductQuantity.getText().toString().trim();

                // Kiểm tra xem các trường đã nhập đủ chưa
                if (!productName.isEmpty() && !productPriceText.isEmpty() && !productQuantityText.isEmpty() && !imagePath.isEmpty()) {
                    // Đã nhập đủ thông tin, tiến hành thêm sản phẩm vào cơ sở dữ liệu
                    int productQuantity = Integer.parseInt(productQuantityText);
                    double productPrice = Double.parseDouble(productPriceText);

                    //Thêm sản phẩm
                    if(isAddProduct){
                        SQLiteManager db = new SQLiteManager(ProductDetailActivity.this);
                        db.addProduct(productName, productQuantity, productPrice, imagePath);
                    }
                    //Update sản phẩm
                    else {
                        SQLiteManager db = new SQLiteManager(ProductDetailActivity.this);
                    }
                } else {
                    // Hiển thị thông báo lỗi cho người dùng
                    Toast.makeText(ProductDetailActivity.this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                }
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
            ivProduct.setImageURI(imageUri);

            // Lấy đường dẫn của ảnh từ Uri và lưu vào biến imagePath
            imagePath = imageUri.toString();
        }
    }
    private void getIntentProduct() {
        Intent intent = getIntent();
        if (intent != null) {
            String btnXoa = intent.getStringExtra("btnXoa");
            if (btnXoa != null && btnXoa.equals("false")) {
                // Ẩn nút xóa
                btDelete.setVisibility(View.GONE);
                ivQR.setVisibility(View.GONE);
                isAddProduct =true;
            }

            int productId = intent.getIntExtra("productId", -1);
            String productName = intent.getStringExtra("productName");
            int productQuantity = intent.getIntExtra("productQuantity", 0);
            double productPrice = intent.getDoubleExtra("productPrice", 0.0);
            String productImage = intent.getStringExtra("productImage");

            // Tạo mới thisProduct dựa trên các giá trị này
            thisProduct = new Product(productId, productName, productQuantity, productPrice, productImage);

            // Hiển thị thông tin của sản phẩm lên các EditText và ImageView tương ứng
            edProductName.setText(productName);
            edProductPrice.setText(String.valueOf(productPrice));
            edProductQuantity.setText(String.valueOf(productQuantity));

            // Hiển thị hình ảnh sản phẩm nếu có
//            if (productImage != null && !productImage.isEmpty()) {
//                // Convert đường dẫn string của hình ảnh sang Uri
//                Uri imageUri = Uri.parse(productImage);
//                // Hiển thị hình ảnh lên ImageView
//                ivProduct.setImageURI(imageUri);
//                // Lưu đường dẫn của hình ảnh
//                imagePath = productImage;
//            }
        }
    }
}