package com.example.project_lv1_mobile;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_lv1_mobile.dao.MemberDAO;
import com.example.project_lv1_mobile.model.Member;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class NavigationActivity extends AppCompatActivity {
    MemberDAO dao;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private final String TABLE_NAME = "MEMBER";

    private List<Member> memberList = new ArrayList<>();

    protected DrawerLayout drawerLayout;

    protected Toolbar tbBase;

    protected NavigationView navigationView;

    protected Intent intent;
    protected Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        tbBase = findViewById(R.id.tbBase);
        setSupportActionBar(tbBase);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("Quản Lý Kho Hàng");
        setToolbarTitle("Quản Lý Kho Hàng");

        FrameLayout flBase = findViewById(R.id.flBase);
        flBase.setVisibility(View.GONE);
        Button btnQLLoaiSP = findViewById(R.id.btnQLLoaiSP);
        Button btnQLSP = findViewById(R.id.btnQLSP);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, tbBase, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        setUpNavihgationView();

        dao = new MemberDAO(firestore, NavigationActivity.this);
        // Lấy dữ liệu trả về
        intent = getIntent();
        bundle = intent.getExtras();
        String userName = bundle.getString("userName");
        String fullName = bundle.getString("fullName");
        String email = bundle.getString("email");
        String gender = bundle.getString("gender");
        int rank = bundle.getInt("rank",-1);

        checkView(rank);

        //  Set headerLayout
        View headerView = navigationView.getHeaderView(0);
        TextView txtIMUsername = headerView.findViewById(R.id.txtIMUsername);
        TextView txtIMUFullname = headerView.findViewById(R.id.txtIMUFullname);
        TextView txtIMUEmail = headerView.findViewById(R.id.txtIMUEmail);
        ImageView ivAvatar = headerView.findViewById(R.id.ivAvatar);

        txtIMUsername.setText(userName);
        txtIMUFullname.setText(fullName);
        txtIMUEmail.setText(email);
        if (gender.equalsIgnoreCase("Nam")){
            ivAvatar.setImageResource(R.drawable.male_icon);
        } else if (gender.equalsIgnoreCase("Nữ")) {
            ivAvatar.setImageResource(R.drawable.female_icon);
        }

        btnQLLoaiSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toProductType = new Intent(NavigationActivity.this, ProductTypeActivity.class);
                toProductType.putExtras(bundle);
                startActivity(toProductType);
                finish();
            }
        });

        btnQLSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toProduct = new Intent(NavigationActivity.this, ProductActivity.class);
                toProduct.putExtras(bundle);
                startActivity(toProduct);
                finish();
            }
        });
    }

    protected void setToolbarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    // Ẩn view theo điều kiện
    protected void checkView(int chekID) {
        Menu menu = navigationView.getMenu();
        MenuItem itemAddMember = menu.findItem(R.id.itemAddMember);

        if (chekID == 0) {
            itemAddMember.setVisible(true);
        } else if (chekID == 1) {
            itemAddMember.setVisible(false);
        }
    }

    private void setUpNavihgationView() {
        NavigationView navigationView = findViewById(R.id.navigationView);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //  Xử lý sự kiện khi người dùng chọn mục trong NavigationView
                int indexItem = item.getItemId();
                if (indexItem == R.id.itemHome) {
                    Intent toHome = new Intent(NavigationActivity.this, NavigationActivity.class);
                    toHome.putExtras(bundle);
                    startActivity(toHome);
                    finish();
                    return true;
                } else if (indexItem == R.id.itemStatisticalOut) {
                    Toast.makeText(NavigationActivity.this, "Chức năng đang trong quá trình phát triển", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (indexItem == R.id.itemStatisticalOdd) {
                    Toast.makeText(NavigationActivity.this, "Chức năng đang trong quá trình phát triển", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (indexItem == R.id.itemAddMember) {
                    Toast.makeText(NavigationActivity.this, "Chức năng đang trong quá trình phát triển", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (indexItem == R.id.itemLogout) {
                    startActivity(new Intent(NavigationActivity.this, LoginActivity.class));
                    finish();
                    return true;
                }
                return false;
            }
        });
    }

}