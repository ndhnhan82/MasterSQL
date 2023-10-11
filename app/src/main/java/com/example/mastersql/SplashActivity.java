package com.example.mastersql;

import static com.example.mastersql.fragments.Login.user;

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
        } ,2000);
    }

    private void nextActivity() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity( intent );
        }else{
            user.setEmailAddress( currentUser.getEmail());
            Intent intent = new Intent(this, MainActivity.class);
            startActivity( intent );
        }


    }
}