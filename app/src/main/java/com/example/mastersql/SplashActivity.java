package com.example.mastersql;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splash );

        Handler handler = new Handler();
        handler.postDelayed( new Runnable() {
            @Override
            public void run() {

                nextActivity();
            }
        } ,1000);
    }

    private void nextActivity() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        Intent intent;
        if (currentUser == null){
            intent = new Intent( this, LoginActivity.class );
        }else{
            intent = new Intent( this, MainActivity.class );
        }
        startActivity( intent );
    }
}