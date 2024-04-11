package com.example.btlandroid;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddProductActivity extends AppCompatActivity {

    private EditText edProductName;
    private EditText edProductId;
    private EditText edProductPrice;
    private EditText edProductQuantity;
    private Button btCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product);

//        // Ánh xạ các thành phần UI từ XML
//        edProductName = findViewById(R.id.ed_product);
//        edProductId = findViewById(R.id.ed_id_product);
//        edProductPrice = findViewById(R.id.ed_price);
//        edProductQuantity = findViewById(R.id.ed_quantity_product);
//        btCreate = findViewById(R.id.btCreate);

        // Xử lý sự kiện khi nhấp vào nút "Thêm mới"
       /* btCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy thông tin từ các trường nhập liệu
                String productName = edProductName.getText().toString();
                String productId = edProductId.getText().toString();
                String productPrice = edProductPrice.getText().toString();
                String productQuantity = edProductQuantity.getText().toString();

                // Thực hiện xử lý thêm mới sản phẩm ở đây
                // Ví dụ: Gửi thông tin sản phẩm đến cơ sở dữ liệu hoặc thực hiện các thao tác khác

                // Sau khi xử lý, bạn có thể kết thúc hoạt động này và quay trở lại ProductActivity (hoặc hoạt động khác)
                finish();
            }
        });*/
    }
}