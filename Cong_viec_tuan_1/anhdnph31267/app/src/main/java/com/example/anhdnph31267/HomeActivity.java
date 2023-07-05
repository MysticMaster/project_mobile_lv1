package com.example.anhdnph31267;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater.from(this).inflate(R.layout.activity_home, findViewById(R.id.flBase),true);

        setToolbarTitle("Quản Lý Kho Hàng");

        Button btnQLLoaiSP = findViewById(R.id.btnQLLoaiSP);
        Button btnQLSP = findViewById(R.id.btnQLSP);

        btnQLLoaiSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ProductTypeActivity.class));
            }
        });

        btnQLSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ProductActivity.class));
            }
        });
    }
}