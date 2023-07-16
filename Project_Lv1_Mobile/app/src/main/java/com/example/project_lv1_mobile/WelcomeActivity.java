package com.example.project_lv1_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {
    LinearLayout llWelcomeActivity,wellcomeapp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ImageView imgBn = findViewById(R.id.img_bn);
        wellcomeapp = findViewById(R.id.wellcomeapp);
        llWelcomeActivity = findViewById(R.id.llWelcomeActivity);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animation_welcome);
        llWelcomeActivity.startAnimation(animation);

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Handler handler = new Handler();
                imgBn.setVisibility(View.GONE);
                wellcomeapp.setVisibility(View.VISIBLE);
                wellcomeapp.startAnimation(animation);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                        finish();
                    }
                }, 3000);
            }
        }, 3000);

    }
}