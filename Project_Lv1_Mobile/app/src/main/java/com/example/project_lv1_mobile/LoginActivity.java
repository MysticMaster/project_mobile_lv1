package com.example.project_lv1_mobile;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_lv1_mobile.dao.MemberDAO;
import com.example.project_lv1_mobile.model.Member;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LoginActivity extends AppCompatActivity {

    private Context context;
    private FirebaseFirestore firestore;
    private FirebaseAuth auth;
    private MemberDAO memberDAO;

    private final String TABLE_NAME = "MEMBER";
    private List<Member> memberList;

    private TextInputLayout txtILayoutEmailLogin, txtILayoutPassLogin;
    private TextInputEditText txtIEdtEmailLogin, txtIEdtPassLogin;
    private Button btnLoginSubmit;
    private TextView txtForgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = LoginActivity.this;
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        memberDAO = new MemberDAO(firestore, context);
        memberList = new ArrayList<>();

        txtILayoutEmailLogin = findViewById(R.id.txtILayoutEmailLogin);
        txtILayoutPassLogin = findViewById(R.id.txtILayoutPassLogin);

        txtIEdtEmailLogin = findViewById(R.id.txtIEdtEmailLogin);
        txtIEdtPassLogin = findViewById(R.id.txtIEdtPassLogin);

        ProgressBar progressBarLogin = findViewById(R.id.progressBarLogin);
        btnLoginSubmit = findViewById(R.id.btnLoginSubmit);
        txtForgot = findViewById(R.id.txtForgot);


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


        txtIEdtEmailLogin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    txtILayoutEmailLogin.setHelperText(null);
                    txtIEdtPassLogin.setEnabled(true);
                }
                return false;
            }
        });

        txtIEdtPassLogin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    txtILayoutPassLogin.setHelperText(null);

                    if (txtIEdtEmailLogin.getText().toString().trim().isEmpty()) {

                        txtIEdtPassLogin.setEnabled(false);
                        txtILayoutEmailLogin.setHelperText("Vui lòng nhập địa chỉ email");

                    } else if (!Patterns.EMAIL_ADDRESS.matcher(txtIEdtEmailLogin.getText().toString().trim()).matches()) {

                        txtIEdtPassLogin.setEnabled(false);
                        txtILayoutEmailLogin.setHelperText("Email không hợp lệ");
                    }
                }
                return false;
            }
        });


        btnLoginSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean checkEmail = true;
                Boolean checkPass = true;
                if (txtIEdtPassLogin.getText().toString().trim().isEmpty()) {

                    txtIEdtPassLogin.setEnabled(true);
                    txtILayoutPassLogin.setHelperText("Vui lòng nhập mật khẩu");
                    checkPass = false;
                }

                if (txtIEdtEmailLogin.getText().toString().trim().isEmpty()) {

                    txtILayoutEmailLogin.setHelperText("Vui lòng nhập địa chỉ email");
                    checkEmail = false;

                } else if (!Patterns.EMAIL_ADDRESS.matcher(txtIEdtEmailLogin.getText().toString().trim()).matches()) {

                    txtILayoutEmailLogin.setHelperText("Email không hợp lệ");
                    checkEmail = false;
                }

                if (checkEmail == false || checkPass == false) {
                    return;
                }

                String emailLogin = txtIEdtEmailLogin.getText().toString().trim();
                String passLogin = txtIEdtPassLogin.getText().toString().trim();

                progressBarLogin.setVisibility(View.VISIBLE);
                btnLoginSubmit.setVisibility(View.INVISIBLE);

                auth.fetchSignInMethodsForEmail(emailLogin).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        if (task.isSuccessful()) {
                            SignInMethodQueryResult result = task.getResult();
                            List<String> signInEmail = result.getSignInMethods();

                            if (signInEmail != null && signInEmail.size() > 0) {

                                auth.signInWithEmailAndPassword(emailLogin, passLogin)
                                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {
                                                    Member getMember = null;

                                                    FirebaseUser getCurrentUser = auth.getCurrentUser();
                                                    String userId = getCurrentUser.getUid();

                                                    for (int i = 0; i < memberList.size(); i++) {
                                                        if (memberList.get(i).getIdAccount().equals(userId)) {
                                                            getMember = memberList.get(i);
                                                            break;
                                                        }
                                                    }

                                                    Intent intent = new Intent(LoginActivity.this, NavigationActivity.class);
                                                    Bundle bundle = new Bundle();
                                                    bundle.putString("idMember", getMember.getIdMember());
                                                    intent.putExtras(bundle);

                                                    if (getMember.getStatus() == 1) {
                                                        Toast.makeText(context, "Tài khoản của bạn đã bị vô hiệu hóa", Toast.LENGTH_SHORT).show();
                                                        progressBarLogin.setVisibility(View.INVISIBLE);
                                                        btnLoginSubmit.setVisibility(View.VISIBLE);
                                                    } else {
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                } else {
                                                    progressBarLogin.setVisibility(View.INVISIBLE);
                                                    btnLoginSubmit.setVisibility(View.VISIBLE);
                                                    Toast.makeText(context, "Thông tin tài khoản mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            } else {
                                progressBarLogin.setVisibility(View.INVISIBLE);
                                btnLoginSubmit.setVisibility(View.VISIBLE);
                                Toast.makeText(context, "Tài khoản không tồn tại, vui lòng đăng ký", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });

        txtForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogForgot();
            }
        });

    }

    public void openDialogForgot() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.item_forgot_password, null);
        builder.setView(view);
        Dialog dialogForgot = builder.create();
        dialogForgot.show();

        TextInputLayout txtILayoutEmailForgot = view.findViewById(R.id.txtILayoutEmailForgot);
        TextInputEditText txtIEdtEmailForgot = view.findViewById(R.id.txtIEdtEmailForgot);
        ProgressBar progressBarForgot = view.findViewById(R.id.progressBarForgot);
        Button btnForgotSub = view.findViewById(R.id.btnForgotSub);
        TextView txtCancelForgot = view.findViewById(R.id.txtCancelForgot);


        txtIEdtEmailForgot.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    txtILayoutEmailForgot.setHelperText(null);
                }
                return false;
            }
        });

        txtCancelForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogForgot.dismiss();
            }
        });

        btnForgotSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean checkEmailForgot = true;

                if (txtIEdtEmailForgot.getText().toString().trim().isEmpty()) {

                    txtILayoutEmailForgot.setHelperText("Vui lòng nhập địa chỉ email");
                    checkEmailForgot = false;

                } else if (!Patterns.EMAIL_ADDRESS.matcher(txtIEdtEmailForgot.getText().toString().trim()).matches()) {

                    txtILayoutEmailForgot.setHelperText("Email không hợp lệ");
                    checkEmailForgot = false;
                }

                if (checkEmailForgot == false) {
                    return;
                }

                String emailForgot = txtIEdtEmailForgot.getText().toString().trim();

                progressBarForgot.setVisibility(View.VISIBLE);
                btnForgotSub.setVisibility(View.INVISIBLE);

                auth.fetchSignInMethodsForEmail(emailForgot).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        if (task.isSuccessful()) {
                            SignInMethodQueryResult result = task.getResult();
                            List<String> forgotEmail = result.getSignInMethods();

                            if (forgotEmail != null && forgotEmail.size() > 0) {

                                auth.sendPasswordResetEmail(emailForgot).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(context, "Hệ thống đã gửi một liên kết đến email của bạn" +
                                                "vui lòng xác thực để lấy lại mật khẩu ", Toast.LENGTH_SHORT).show();
                                        dialogForgot.dismiss();
                                    }
                                }).addOnFailureListener(e -> {
                                    Toast.makeText(context, "Đã xảy ra lỗi " + e, Toast.LENGTH_SHORT).show();
                                    progressBarForgot.setVisibility(View.INVISIBLE);
                                    btnForgotSub.setVisibility(View.VISIBLE);
                                });
                            } else {
                                progressBarForgot.setVisibility(View.INVISIBLE);
                                btnForgotSub.setVisibility(View.VISIBLE);
                                Toast.makeText(context, "Email chưa được đăng ký", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                });
            }
        });
    }


}