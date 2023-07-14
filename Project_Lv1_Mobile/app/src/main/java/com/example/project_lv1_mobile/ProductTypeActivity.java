package com.example.project_lv1_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class ProductTypeActivity extends NavigationActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater.from(this).inflate(R.layout.activity_product_type, findViewById(R.id.flBase), true);

        FrameLayout flBase = findViewById(R.id.flBase);
        flBase.setVisibility(View.VISIBLE);
        Button btnQLLoaiSP = findViewById(R.id.btnQLLoaiSP);
        Button btnQLSP = findViewById(R.id.btnQLSP);
        btnQLLoaiSP.setVisibility(View.GONE);
        btnQLSP.setVisibility(View.GONE);

        setToolbarTitle("Quản Lý Loại Sản Phẩm");
    }
}