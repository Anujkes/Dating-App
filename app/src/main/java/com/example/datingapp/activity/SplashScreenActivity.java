package com.example.datingapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.datingapp.MainActivity;
import com.example.datingapp.R;
import com.example.datingapp.auth.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreenActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        auth=FirebaseAuth.getInstance();
       user=auth.getCurrentUser();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(user==null)
                    startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
               else
                   startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));

                finish();
            }
        },2000);



    }
}