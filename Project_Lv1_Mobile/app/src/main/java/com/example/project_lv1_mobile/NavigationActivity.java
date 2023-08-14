package com.example.project_lv1_mobile;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.project_lv1_mobile.fragment.FragmentDoanhThu;
import com.example.project_lv1_mobile.fragment.FragmentHome;
import com.example.project_lv1_mobile.fragment.FragmentLichSu;
import com.example.project_lv1_mobile.fragment.FragmentTaiKhoan;
import com.example.project_lv1_mobile.fragment.FragmentXuatNhap;
import com.example.project_lv1_mobile.model.Member;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class NavigationActivity extends AppCompatActivity {

    //  widget
    protected DrawerLayout drawerLayout;
    protected Toolbar tbBase;
    protected NavigationView navigationView;
    protected BottomNavigationView bottomNavigationViewBase;

    // nhận dữ liệu
    protected Intent intent;
    protected Bundle bundle;
    protected String idMember;
    protected int rank;

    //  firebase
    private final String COLLECTION_MEMBER = "MEMBER";

    //  header Layout
    protected View headerView;
    protected TextView txtIntroLastName, txtIntroFirtName, txtIntroRank, txtIntroEmail, txtTitleTbBase;
    protected FrameLayout flBase;
    protected FrameLayout frameBottomView;
    protected ImageView ivAvatar;
    protected Member member;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        // nhận dữ liệu
        intent = getIntent();
        bundle = intent.getExtras();
        idMember = bundle.getString("idMember");
        int keySetItem = bundle.getInt("keySetItem");
        rank = bundle.getInt("rank");

        //  widget
        flBase = findViewById(R.id.flBase);
        frameBottomView = findViewById(R.id.frameBottomView);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        bottomNavigationViewBase = findViewById(R.id.bottomNavigationViewBase);
        tbBase = findViewById(R.id.tbBase);
        txtTitleTbBase = findViewById(R.id.txtTitleTbBase);
        navigationView.setItemIconTintList(null);
        bottomNavigationViewBase.setItemIconTintList(null);

        //  setup riêng
        setSupportActionBar(tbBase);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setToolbarTitle("WareFlow");

        //  Set headerLayout
        headerView = navigationView.getHeaderView(0);
        txtIntroLastName = headerView.findViewById(R.id.txtIntroLastName);
        txtIntroFirtName = headerView.findViewById(R.id.txtIntroFirtName);
        txtIntroRank = headerView.findViewById(R.id.txtIntroRank);
        txtIntroEmail = headerView.findViewById(R.id.txtIntroEmail);
        ivAvatar = headerView.findViewById(R.id.ivAvatar);

        //  firebase
        getDataMember();

        //  Navigation
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, tbBase, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //  header layout
        setUpNavigationView();
        setVisibleItem(rank);

        bottomNavigationViewBase.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.itemHomeBottom) {
                    FragmentHome fragmentHome = new FragmentHome();
                    fragmentHome.setArguments(bundle);
                    rePlaceFragHome(fragmentHome);
                    return true;
                } else if (item.getItemId() == R.id.itemXuatNhapBottom) {
                    FragmentXuatNhap fragmentXuatNhap = new FragmentXuatNhap();
                    fragmentXuatNhap.setArguments(bundle);
                    rePlaceFragHome(fragmentXuatNhap);
                    return true;
                } else if (item.getItemId() == R.id.itemLichSuBottom) {
                    FragmentLichSu fragmentLichSu = new FragmentLichSu();
                    fragmentLichSu.setArguments(bundle);
                    rePlaceFragHome(fragmentLichSu);
                    return true;
                } else if (item.getItemId() == R.id.itemDoanhThuBottom) {
                    FragmentDoanhThu fragmentDoanhThu = new FragmentDoanhThu();
                    rePlaceFragHome(fragmentDoanhThu);
                    return true;
                } else if (item.getItemId() == R.id.itemTaiKhoanBottom) {
                    FragmentTaiKhoan fragmentTaiKhoan = new FragmentTaiKhoan();
                    fragmentTaiKhoan.setArguments(bundle);
                    rePlaceFragHome(fragmentTaiKhoan);
                    return true;
                }
                return false;
            }
        });
        boolean checKey = false;
        if (keySetItem == 1) {
            bottomNavigationViewBase.setSelectedItemId(R.id.itemXuatNhapBottom);
            checKey =true;
        } else if (keySetItem == 0) {
            bottomNavigationViewBase.setSelectedItemId(R.id.itemHomeBottom);
            checKey = true;
        }

        if (checKey == false){
            bottomNavigationViewBase.setSelectedItemId(R.id.itemHomeBottom);
        }
    }

    private void rePlaceFragHome(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.frameBottomView, fragment).commit();
    }

    protected void getDataMember() {
        DocumentReference reference = FirebaseFirestore.getInstance().collection(COLLECTION_MEMBER).document(idMember);
        reference.get().addOnCompleteListener(task -> {
            DocumentSnapshot snapshot = task.getResult();

            member = snapshot.toObject(Member.class);
            setDataIntro();
        });
    }

    protected void setDataIntro() {
        txtIntroLastName.setText(member.getLastName());
        txtIntroFirtName.setText(member.getFirtName());

        String textRank = member.getRank() == 0 ? "Admin" : "Thủ kho";
        txtIntroRank.setText(textRank);

        txtIntroEmail.setText(member.getEmail());

        if (member.getImageMember().equals("0")) {
            ivAvatar.setImageResource(R.drawable.addmin_icon);
        } else if (member.getImageMember().equals("1")) {
            if (member.getGender().equals("Nam")) {
                ivAvatar.setImageResource(R.drawable.male_icon);
            } else {
                ivAvatar.setImageResource(R.drawable.female_icon);
            }
        } else {
            Glide.with(this).load(member.getImageMember()).into(ivAvatar);
        }
    }

    protected void setToolbarTitle(String title) {
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        txtTitleTbBase.setText(title);
    }

    // Ẩn view theo điều kiện
    protected void setVisibleItem(int chekID) {
        Menu menu = navigationView.getMenu();
        MenuItem itemAddMember = menu.findItem(R.id.itemManageMember);

        if (chekID == 0) {
            itemAddMember.setVisible(true);
        } else if (chekID == 1) {
            itemAddMember.setVisible(false);
        }
    }

    private void setUpNavigationView() {
        NavigationView navigationView = findViewById(R.id.navigationView);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //  Xử lý sự kiện khi người dùng chọn mục trong NavigationView
                int indexItem = item.getItemId();
                if (indexItem == R.id.itemHome) {
                    Intent toHome = new Intent(NavigationActivity.this, NavigationActivity.class);
                    bundle.putInt("keySetItem",0);
                    toHome.putExtras(bundle);
                    startActivity(toHome);
                    finish();
                    return true;
                } else if (indexItem == R.id.itemManageMember) {
                    Intent toManageMember = new Intent(NavigationActivity.this, ManageMemberActivity.class);
                    toManageMember.putExtras(bundle);
                    startActivity(toManageMember);
                    finish();
                    return true;
                } else if (indexItem == R.id.itemLogout) {
                    startActivity(new Intent(NavigationActivity.this, LoginActivity.class));
                    finish();
                    return true;
                } else if (indexItem == R.id.itemOut) {
                    System.exit(0);
                }
                return false;
            }
        });
    }

    public void onClickItemToolbar(Context context) {
        tbBase.setOnMenuItemClickListener(item -> {
            int itemID = item.getItemId();
            if (itemID == R.id.itemTBBackHome) {
                Intent toHome = new Intent(context, NavigationActivity.class);
                bundle.putInt("keySetItem",0);
                toHome.putExtras(bundle);
                startActivity(toHome);
                finish();
                return true;
            }
            return false;
        });
    }

}