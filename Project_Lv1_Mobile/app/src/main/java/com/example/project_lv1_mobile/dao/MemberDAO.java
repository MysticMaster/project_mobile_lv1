package com.example.project_lv1_mobile.dao;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.project_lv1_mobile.model.Member;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;

public class MemberDAO {
    private final FirebaseFirestore databaseMember;
    private final Context context;
    private final String TABLE_NAME = "MEMBER";


    public MemberDAO(FirebaseFirestore databaseMember, Context context) {
        this.databaseMember = databaseMember;
        this.context = context;
    }

    public void addMember(Member member) {
        HashMap<String, Object> mapMember = member.objectMember();
        databaseMember.collection(TABLE_NAME).document(member.getIdMember())
                .set(mapMember).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Add Successful", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Add Fail", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void updateMember(Member member) {
        databaseMember.collection(TABLE_NAME).document(member.getIdMember()).update(member.objectMember())
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(context, "Update Successful", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(e -> {
                    Log.e("Error", "Fail" + e);
                });
    }
}
