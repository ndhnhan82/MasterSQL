package com.example.mastersql;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.internal.CheckableGroup;

import java.util.Set;

public class ExerciseBasicTerms1 extends AppCompatActivity implements View.OnClickListener {
    Button btnCancel, btnNext, btTRIAL;
    FloatingActionButton fbtnBack;
    CheckBox ckSecure, ckStructured, ckSimple, ckSequential;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_basic_terms1);
        initialize();
    }

    public void initialize()
    {
        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(this);
        btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(this);
        ckSecure = findViewById(R.id.ckSecureQL);
        ckSecure.setOnClickListener(this);
        ckStructured = findViewById(R.id.ckStructured);
        ckStructured.setOnClickListener(this);
        ckSimple = findViewById(R.id.ckSimple);
        ckSimple.setOnClickListener(this);
        ckSequential = findViewById(R.id.ckSequential);
        ckSequential.setOnClickListener(this);
        fbtnBack = findViewById(R.id.fbtnBack);
        fbtnBack.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == btnCancel.getId())
        {
            finish();
        }

        if (id == ckSecure.getId())
        {
            ckSecure.setBackgroundResource(R.drawable.border_wrong_answer);
            Toast.makeText(this, "Sorry! You have chosen the wrong answer", Toast.LENGTH_SHORT).show();
        }

        if (id == ckStructured.getId())
        {
            ckStructured.setBackgroundResource(R.drawable.border_right_answer);
            Toast.makeText(this, "Congratulations! You can go to the next exercise", Toast.LENGTH_SHORT).show();
            btnNext.setVisibility(View.VISIBLE);
        }

        if (id == ckSimple.getId())
        {
            ckSimple.setBackgroundResource(R.drawable.border_wrong_answer);
            Toast.makeText(this, "Sorry! You have chosen the wrong answer", Toast.LENGTH_SHORT).show();
        }

        if (id == ckSequential.getId())
        {
            ckSequential.setBackgroundResource(R.drawable.border_wrong_answer);
            Toast.makeText(this, "Sorry! You have chosen the wrong answer", Toast.LENGTH_SHORT).show();
        }

        if (id == btnNext.getId())
        {
            Intent intent = new Intent(this, ExerciseBasicTerms2.class);
            startActivity(intent);
            finish();
        }

        if (id == fbtnBack.getId())
        {
            finish();
        }
    }

}