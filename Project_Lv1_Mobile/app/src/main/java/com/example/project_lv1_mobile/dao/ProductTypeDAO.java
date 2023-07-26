package com.example.project_lv1_mobile.dao;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.project_lv1_mobile.model.Member;
import com.example.project_lv1_mobile.model.ProductType;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class ProductTypeDAO {

    private final FirebaseFirestore firestore;
    private final Context context;
    private final String TABLE_NAME = "TYPE";

    public ProductTypeDAO(FirebaseFirestore firestore, Context context) {
        this.firestore = firestore;
        this.context = context;
    }

    public void addProductType(ProductType type) {
        HashMap<String, Object> mapType = type.objectType();
        firestore.collection(TABLE_NAME).document(type.getIdType())
                .set(mapType).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Add Successful", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e -> {
                    Log.e("Error", "Fail" + e);
                });
    }

    public void updateType(ProductType type) {
        firestore.collection(TABLE_NAME).document(type.getIdType()).update(type.objectType())
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(context, "Update Successful", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(e -> {
                    Log.e("Error", "Fail" + e);
                });
    }
}
