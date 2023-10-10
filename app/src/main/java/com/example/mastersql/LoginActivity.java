package com.example.mastersql;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.mastersql.adapter.LoginAdapter;
import com.google.android.material.tabs.TabLayout;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login );
        initialize();
    }

    private void initialize() {
        ViewPager mVpLogin = findViewById(R.id.vpgLogin);
        mVpLogin.setAdapter(new LoginAdapter(getSupportFragmentManager()));
        TabLayout tabLayout = findViewById(R.id.tloLogin);
        tabLayout.setupWithViewPager(mVpLogin);
    }
}