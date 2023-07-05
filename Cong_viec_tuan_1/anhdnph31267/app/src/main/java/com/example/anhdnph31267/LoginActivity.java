package com.example.anhdnph31267;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText edtUsernameLogin = findViewById(R.id.edtUsernameLogin);
        EditText edtPasswordLogin = findViewById(R.id.edtPasswordLogin);
        Button btnLoginSubmit = findViewById(R.id.btnLoginSubmit);

        btnLoginSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtUsernameLogin.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Trống username", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (edtPasswordLogin.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Trống password", Toast.LENGTH_SHORT).show();
                    return;
                }

                startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                finish();
            }
        });
    }
}