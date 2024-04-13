package com.example.btlandroid;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import androidx.appcompat.widget.Toolbar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class ProductDetailActivity extends AppCompatActivity {

    private byte[] imagePath;
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
    private Context context;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail_layout);
        context = this;

        mToolbar = findViewById(R.id.tb);
        setSupportActionBar(mToolbar);
        ivProduct = findViewById(R.id.iv_product);
        ivProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageLibrary();
            }
        });

        edProductName = findViewById(R.id.ed_product);
        edProductPrice = findViewById(R.id.ed_price);
        edProductQuantity = findViewById(R.id.ed_quantity);
        ivQR = findViewById(R.id.ivQR);
        btSave = findViewById(R.id.btnSave);
        btDelete = findViewById(R.id.btDelete);

        getIntentProduct();
        createQRImage();

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy các giá trị từ các EditText
                String productName = edProductName.getText().toString().trim();
                String productPriceText = edProductPrice.getText().toString().trim();
                String productQuantityText = edProductQuantity.getText().toString().trim();

                // Kiểm tra xem các trường đã nhập đủ chưa
                if (!productName.isEmpty() && !productPriceText.isEmpty() && !productQuantityText.isEmpty() && imagePath!=null) {
                    // Đã nhập đủ thông tin, tiến hành thêm sản phẩm vào cơ sở dữ liệu
                    int productQuantity = Integer.parseInt(productQuantityText);
                    double productPrice = Double.parseDouble(productPriceText);

                    //Thêm sản phẩm
                    if(isAddProduct){
                        SQLiteManager db = new SQLiteManager(context);
                        db.addProduct(productName, productQuantity, productPrice, imagePath);
                        db.close();
                    }
                    //Update sản phẩm
                    else {
                        thisProduct.setImage(imagePath);
                        thisProduct.setName(edProductName.getText().toString());
                        thisProduct.setAmount(Integer.parseInt(edProductQuantity.getText().toString()));
                        thisProduct.setPrice(Double.parseDouble(edProductPrice.getText().toString()));
                        SQLiteManager db = new SQLiteManager(context);
                        db.updateProduct(thisProduct);
                        db.close();
                    }

                } else {
                    // Hiển thị thông báo lỗi cho người dùng
                    Toast.makeText(context, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hiển thị hộp thoại xác nhận trước khi xóa sản phẩm
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc chắn muốn xóa sản phẩm này?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Nếu người dùng chọn có, thực hiện xóa sản phẩm
                        SQLiteManager db = new SQLiteManager(context);
                        db.deleteProduct(thisProduct);
                        db.close();

                        // Hiển thị thông báo xóa thành công
                        Toast.makeText(context, "Đã xóa sản phẩm", Toast.LENGTH_SHORT).show();

                        // Kết thúc Activity và quay lại Activity trước đó
                        finish();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Nếu người dùng chọn không, đóng hộp thoại
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });

    }

    private void createQRImage() {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(String.valueOf(thisProduct.getId()), BarcodeFormat.QR_CODE,300,300);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            ivQR.setImageBitmap(bitmap);

        }catch (WriterException e){
            throw new RuntimeException(e);
        }
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

            // Lưu dữ liệu ảnh vào biến imagePath
            imagePath = convertImageUriToBlob(imageUri);
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
            byte[] productImage = intent.getByteArrayExtra("productImage");

            // Tạo mới thisProduct dựa trên các giá trị này
            thisProduct = new Product(productId, productName, productQuantity, productPrice, productImage);

            // Hiển thị thông tin của sản phẩm lên các EditText và ImageView tương ứng
            edProductName.setText(productName);
            edProductPrice.setText(String.valueOf(productPrice));
            edProductQuantity.setText(String.valueOf(productQuantity));

            // Hiển thị hình ảnh sản phẩm nếu có
            if (productImage != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(productImage, 0, productImage.length);
                // Hiển thị hình ảnh từ Bitmap lên ImageView
                ivProduct.setImageBitmap(bitmap);
                // Lưu đường dẫn của hình ảnh
                imagePath = productImage;
            }
        }
    }
    private byte[] convertImageUriToBlob(Uri imageUri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            return baos.toByteArray(); // Trả về mảng byte[] thay vì chuỗi
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}