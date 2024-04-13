package com.example.btlandroid;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateFormatActivity extends AppCompatActivity {
    TextView txtTime;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invoice_details_layout);
        txtTime=(TextView) findViewById(R.id.tvdate);
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy  ");
        txtTime.append(dateFormat.format(calendar.getTime()));
        SimpleDateFormat hourFormat = new SimpleDateFormat("hh:mm:ss a");
        txtTime.append(hourFormat.format(calendar.getTime()));
    }
}
