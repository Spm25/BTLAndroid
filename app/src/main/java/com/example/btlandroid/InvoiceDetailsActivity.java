package com.example.btlandroid;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import java.text.SimpleDateFormat;
import java.util.Calendar;
public class InvoiceDetailsActivity extends AppCompatActivity {
    TextView txtTime;
    ImageButton btnScan;
    ImageButton lvqr;
    Button btnhuy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invoice_details_layout);
        btnScan = findViewById(R.id.btnScan);
        txtTime = findViewById(R.id.tvdate);
        btnhuy  = findViewById(R.id.btnHuy);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy ");
        SimpleDateFormat hourFormat = new SimpleDateFormat("hh:mm:ss a");
        txtTime.setText(dateFormat.format(calendar.getTime()));
        txtTime.append(hourFormat.format(calendar.getTime()));
        View itemView = getLayoutInflater().inflate(R.layout.lvitem_product_layout, null);
        lvqr = itemView.findViewById(R.id.lvqr);
        btnScan.setOnClickListener(v -> scanCode());
        lvqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// Xử lý sự kiện khi nhấn vào ImageButton lvqr
                Intent intent = new Intent(InvoiceDetailsActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// Xử lý sự kiện khi nhấn vào ImageButton lvqr
                Intent intent = new Intent(InvoiceDetailsActivity.this, InvoiceActivity.class);
                startActivity(intent);
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
            AlertDialog.Builder builder = new AlertDialog.Builder(InvoiceDetailsActivity.this);
            builder.setTitle("Result");
            builder.setMessage(result.getContents());
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).show();
        }
    });
}