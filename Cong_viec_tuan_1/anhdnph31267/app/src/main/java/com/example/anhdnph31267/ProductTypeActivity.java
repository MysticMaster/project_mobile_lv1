package com.example.anhdnph31267;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;

public class ProductTypeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater.from(this).inflate(R.layout.activity_product_type, findViewById(R.id.flBase),true);

        setToolbarTitle("Quản Lý Loại Sản Phẩm");
    }
}