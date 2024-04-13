package com.example.btlandroid;

import android.content.DialogInterface;
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
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invoice_details_layout);
        btnScan = findViewById(R.id.btnScan);
        btnScan.setOnClickListener(v->
        {
            scanCode();
        });

        txtTime=(TextView) findViewById(R.id.tvdate);
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy  ");
        txtTime.append(dateFormat.format(calendar.getTime()));
        SimpleDateFormat hourFormat = new SimpleDateFormat("hh:mm:ss a");
        txtTime.append(hourFormat.format(calendar.getTime()));
        Button btnCancel = findViewById(R.id.btnHuy);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kết thúc hoạt động hiện tại và quay lại hoạt động trước đó
                finish();
            }
        });
        

    }
    private void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volumn on to flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLaucher.launch(options);
    }
    ActivityResultLauncher<ScanOptions> barLaucher = registerForActivityResult(new ScanContract(), result->{
        if(result.getContents()!=null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(InvoiceDetailsActivity.this);
            builder.setTitle("Result");
            builder.setMessage(result.getContents());
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();;
                }
            }).show();
        }
    });
}
