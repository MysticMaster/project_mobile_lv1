package com.example.project_lv1_mobile.dao;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.project_lv1_mobile.model.Product;
import com.example.project_lv1_mobile.model.ProductType;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class ProductDAO {

    private final FirebaseFirestore firestore;
    private final Context context;
    private final String TABLE_NAME = "PRODUCT";

    public ProductDAO(FirebaseFirestore firestore, Context context) {
        this.firestore = firestore;
        this.context = context;
    }

    public void addProduct(Product product) {
        HashMap<String, Object> mapType = product.objectType();
        firestore.collection(TABLE_NAME).document(product.getIdProduct())
                .set(mapType).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Add Successful", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e -> {
                    Log.e("Error", "Fail" + e);
                });
    }

    public void updateProduct(Product product) {
        firestore.collection(TABLE_NAME).document(product.getIdProduct()).update(product.objectType())
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(context, "Update Successful", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(e -> {
                    Log.e("Error", "Fail" + e);
                });
    }

}
