package com.example.anhdnph31267;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;

public class ProductActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater.from(this).inflate(R.layout.activity_product, findViewById(R.id.flBase),true);

        setToolbarTitle("Quản Lý Loại Sản Phẩm");
    }
}