package com.example.anhdnph31267;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class BaseActivity extends AppCompatActivity {

    protected DrawerLayout drawerLayout;

    protected Toolbar tbBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        tbBase = findViewById(R.id.tbBase);
        setSupportActionBar(tbBase);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("Quản Lý Kho Hàng");

        drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView nvViewBase = findViewById(R.id.nvViewBase);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, tbBase, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        setUpNavihgationView();
    }

    protected void setToolbarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    private void setUpNavihgationView() {
        NavigationView navigationView = findViewById(R.id.nvViewBase);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //  Xử lý sự kiện khi người dùng chọn mục trong NavigationView
                int indexItem = item.getItemId();
                if (indexItem == R.id.itemHome) {
                    startActivity(new Intent(BaseActivity.this, HomeActivity.class));
                    finish();
                    return true;
                } else if (indexItem == R.id.itemStatisticalOut) {
                    Toast.makeText(BaseActivity.this, "Chức năng đang trong quá trình phát triển", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (indexItem == R.id.itemStatisticalOdd) {
                    Toast.makeText(BaseActivity.this, "Chức năng đang trong quá trình phát triển", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (indexItem == R.id.itemAddMember) {
                    Toast.makeText(BaseActivity.this, "Chức năng đang trong quá trình phát triển", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (indexItem == R.id.itemLogout) {
                    startActivity(new Intent(BaseActivity.this, LoginActivity.class));
                    finish();
                    return true;
                }
                return false;
            }
        });
    }
}