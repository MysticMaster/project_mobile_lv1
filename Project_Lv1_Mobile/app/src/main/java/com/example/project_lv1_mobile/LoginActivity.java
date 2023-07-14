package com.example.project_lv1_mobile;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project_lv1_mobile.dao.MemberDAO;
import com.example.project_lv1_mobile.model.Member;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LoginActivity extends AppCompatActivity {

    private Context context = LoginActivity.this;
    private MemberDAO memberDAO;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private final String TABLE_NAME = "MEMBER";

    private List<Member> memberList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        memberDAO = new MemberDAO(firestore,context);
//
//        String id = UUID.randomUUID().toString();
//        String name = "Hoa Mộc Lan";
//        String user = "Member02";
//        String pass = "member123";
//        String sex = "Nữ";
//        String email = "lanhm123456@fpt.edu.vn";
//        int rank = 1;
//        int status = 0;
//
//        Member member = new Member(id,name,user,pass,sex,email,rank,status);
//        memberDAO.addMember(member);


        firestore.collection(TABLE_NAME).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.e(TAG, "Read Fail", error);
                    return;
                }
                if (value != null) {
                    memberList.clear();
                    for (DocumentSnapshot snapshot : value.getDocuments()) {
                        Member member = snapshot.toObject(Member.class);
                        memberList.add(member);
                    }
                }
            }
        });

        EditText edtUsernameLogin = findViewById(R.id.edtUsernameLogin);
        EditText edtPasswordLogin = findViewById(R.id.edtPasswordLogin);
        Button btnLoginSubmit = findViewById(R.id.btnLoginSubmit);

        btnLoginSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtUsernameLogin.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Trống username", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (edtPasswordLogin.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Trống password", Toast.LENGTH_SHORT).show();
                    return;
                }

                String user = edtUsernameLogin.getText().toString();
                String pass = edtPasswordLogin.getText().toString();

                boolean check = false;
                for (int i = 0; i < memberList.size(); i++) {
                    if (user.equals(memberList.get(i).getUserName())) {
                        check = true;
                        if (user.equals(memberList.get(i).getUserName()) && pass.equals(memberList.get(i).getPassWord())) {

                            Intent intent = new Intent(LoginActivity.this, NavigationActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("fullName", memberList.get(i).getFullName());
                            bundle.putString("userName", memberList.get(i).getUserName());
                            bundle.putString("email", memberList.get(i).getEmail());
                            bundle.putString("gender", memberList.get(i).getGender());
                            bundle.putInt("rank", memberList.get(i).getRank());

                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(context, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }
                if (check == false){
                    Toast.makeText(context, "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}