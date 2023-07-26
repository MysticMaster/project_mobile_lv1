package com.example.project_lv1_mobile;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.project_lv1_mobile.adapter.ProductAdapter;
import com.example.project_lv1_mobile.adapter.ProductTypeAdapter;
import com.example.project_lv1_mobile.dao.ProductDAO;
import com.example.project_lv1_mobile.dao.ProductTypeDAO;
import com.example.project_lv1_mobile.model.Product;
import com.example.project_lv1_mobile.model.ProductType;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class IntroProductActivity extends AppCompatActivity {

    private Context context;
    private List<Product> productList;
    private List<ProductType> productTypeList;
    private FirebaseFirestore firestore;

    private final String collectionType = "TYPE";
    private final String collectionProduct = "PRODUCT";

    private BottomNavigationView bottomNavigationIntroProduct;
    private ImageView ivImageProductFill;
    private TextView txtFillProductName, txtFillProductType, txtFIllProductUnitPrice, txtFillSoLuong;
    private ImageButton iBtnExitFillProduct;

    // Nhận dữ liệu
    private Intent intent;
    private Bundle bundle;
    private String idProduct;
    private String idMember;
    private String productTyeName;

    private Product product;
    private ProductType productType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_product);

        context = IntroProductActivity.this;
        productList = new ArrayList<>();
        productTypeList = new ArrayList<>();
        firestore = FirebaseFirestore.getInstance();

        intent = getIntent();
        bundle = intent.getExtras();

        idProduct = bundle.getString("idProduct");
        idMember = bundle.getString("idMember");

        bottomNavigationIntroProduct = findViewById(R.id.bottomNavigationIntroProduct);
        ivImageProductFill = findViewById(R.id.ivImageProductFill);
        txtFillProductName = findViewById(R.id.txtFillProductName);
        txtFillProductType = findViewById(R.id.txtFillProductType);
        txtFIllProductUnitPrice = findViewById(R.id.txtFIllProductUnitPrice);
        txtFillSoLuong = findViewById(R.id.txtFillSoLuong);
        iBtnExitFillProduct = findViewById(R.id.iBtnExitFillProduct);


        getDataProduct();

        iBtnExitFillProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void getDataProduct() {
        DocumentReference reference = FirebaseFirestore.getInstance().collection(collectionProduct).document(idProduct);
        reference.get().addOnCompleteListener(task -> {
            DocumentSnapshot snapshot = task.getResult();
            product = snapshot.toObject(Product.class);

            getDataProductType();
        });
    }

    public void getDataProductType() {
        DocumentReference reference = FirebaseFirestore.getInstance().collection(collectionType).document(product.getIdProductType());
        reference.get().addOnCompleteListener(task -> {
            DocumentSnapshot snapshot = task.getResult();

            productType = snapshot.toObject(ProductType.class);
            productTyeName = productType.getNameProductType();

            fillData();
        });

    }


    public void fillData() {
        Glide.with(context).load(product.getProductImageUri()).into(ivImageProductFill);

        txtFillProductName.setText(product.getProductName());
        txtFillProductType.setText(productTyeName);
        txtFIllProductUnitPrice.setText(Integer.toString(product.getUnitPrice()));
        txtFillSoLuong.setText(Integer.toString(product.getQuantity()));

        bottomNavigationIntroProduct.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.itemNhapHang) {
                    Toast.makeText(context, "Sắp làm nhập hàng", Toast.LENGTH_SHORT).show();
                } else if (item.getItemId() == R.id.itemXuatKho) {
                    Toast.makeText(context, "Sắp làm xuất kho", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }
}