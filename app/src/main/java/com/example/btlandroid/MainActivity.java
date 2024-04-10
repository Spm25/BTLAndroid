package com.example.btlandroid;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<product> listproduct;
    private adapter adapter;
    IntentFilter intentFilter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView  = findViewById(R.id.list_item);

        listproduct = new ArrayList<product>();
        listproduct.add(new product(1,2,100,"https://png.pngtree.com/element_origin_min_pic/16/11/12/70085543ffb787b0212163a5c5ba6635.jpg"));
        listproduct.add(new product(1,2,100,"https://png.pngtree.com/element_origin_min_pic/16/11/12/70085543ffb787b0212163a5c5ba6635.jpg"));
        listproduct.add(new product(1,2,100,"https://png.pngtree.com/element_origin_min_pic/16/11/12/70085543ffb787b0212163a5c5ba6635.jpg"));
        listproduct.add(new product(1,2,100,"https://png.pngtree.com/element_origin_min_pic/16/11/12/70085543ffb787b0212163a5c5ba6635.jpg"));

       adapter = new adapter(this,listproduct);
        listView.setAdapter(adapter);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.sid) {
            Toast.makeText(MainActivity.this,"hehe",Toast.LENGTH_SHORT).show();
        }
        if(item.getItemId() == R.id.sn) {
            Toast.makeText(MainActivity.this,"hehe",Toast.LENGTH_SHORT).show();
        }
        if(item.getItemId() == R.id.ssl) {
            Intent intent = new Intent("com.example.myapplication.SOME_ACTION");
            sendBroadcast(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

}