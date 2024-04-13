package com.example.btlandroid;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
public class InvoiceDetailsActivity extends AppCompatActivity {
    TextView txtTime;
    ImageButton btnScan;
    ImageButton lvqr;
    Button btnhuy;
    private ListView listView;
    private AdapterProduct adapterProduct;
    private ArrayList<Product> productList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invoice_details_layout);
        btnScan = findViewById(R.id.btnScan);
        txtTime = findViewById(R.id.tvdate);
        btnhuy  = findViewById(R.id.btnHuy);

        // Khởi tạo danh sách sản phẩm
        productList = new ArrayList<>();
        // Thêm các sản phẩm vào danh sách
//        productList.add(new Product(1, "Product 1", 10, 100.0, null));
//        productList.add(new Product(2, "Product 2", 20, 200.0, null));
        // Thêm các sản phẩm vào danh sách...

        // Khởi tạo AdapterProduct
        adapterProduct = new AdapterProduct(this, productList);

        // Ánh xạ ListView từ layout
        listView = findViewById(R.id.list_LVitem);

        // Đặt Adapter cho ListView
        listView.setAdapter(adapterProduct);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy ");
        SimpleDateFormat hourFormat = new SimpleDateFormat("hh:mm:ss a");
        txtTime.setText(dateFormat.format(calendar.getTime()));
        txtTime.append(hourFormat.format(calendar.getTime()));

        btnScan.setOnClickListener(v -> scanCode());
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    // Phương thức để quét mã
    private void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume on to flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
    }
    // Kết quả của quét mã
    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {
            // Lấy ID của sản phẩm từ mã QR đã quét
            int productId = Integer.parseInt(result.getContents());

            // Gọi phương thức để tìm sản phẩm theo ID từ cơ sở dữ liệu
            SQLiteManager dbHelper = new SQLiteManager(InvoiceDetailsActivity.this);
            Product scannedProduct = dbHelper.getProductById(productId);

            if (scannedProduct != null) {
                // Hiển thị thông tin sản phẩm hoặc thêm vào danh sách sản phẩm
                productList.add(scannedProduct);
                adapterProduct.notifyDataSetChanged(); // Cập nhật ListView
            } else {
                // Hiển thị thông báo nếu không tìm thấy sản phẩm
                Toast.makeText(InvoiceDetailsActivity.this, "Không tìm thấy sản phẩm với ID: " + productId, Toast.LENGTH_SHORT).show();
            }
        }
    });

}