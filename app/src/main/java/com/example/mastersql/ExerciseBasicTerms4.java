package com.example.mastersql;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ExerciseBasicTerms4 extends AppCompatActivity implements View.OnClickListener {

    Button btnCancel, btnNext;
    FloatingActionButton fbtnBack;
    CheckBox ckConstraint, ckAutomatically, ckDuplicatedKey, ckLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_basic_terms4);
        initialize();
    }

    private void initialize() {
        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(this);
        btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(this);
        ckConstraint = findViewById(R.id.ckConstraint);
        ckConstraint.setOnClickListener(this);
        ckAutomatically = findViewById(R.id.ckAutomatically);
        ckAutomatically.setOnClickListener(this);
        ckDuplicatedKey = findViewById(R.id.ckDuplicateKey);
        ckDuplicatedKey.setOnClickListener(this);
        ckLink = findViewById(R.id.ckLink);
        ckLink.setOnClickListener(this);
        fbtnBack = findViewById(R.id.ivBack );
        fbtnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == btnCancel.getId())
        {
            finish();
        }

        if (id == ckConstraint.getId())
        {
            ckConstraint.setBackgroundResource(R.drawable.border_wrong_answer);
            Toast.makeText(this, "Sorry! You have chosen the wrong answer", Toast.LENGTH_SHORT).show();
        }

        if (id == ckLink.getId())
        {
            ckLink.setBackgroundResource(R.drawable.border_right_answer);
            Toast.makeText(this, "Congratulations! You can go to the next exercise", Toast.LENGTH_SHORT).show();
            btnNext.setVisibility(View.VISIBLE);
        }

        if (id == ckAutomatically.getId())
        {
            ckAutomatically.setBackgroundResource(R.drawable.border_wrong_answer);
            Toast.makeText(this, "Sorry! You have chosen the wrong answer", Toast.LENGTH_SHORT).show();
        }

        if (id == ckDuplicatedKey.getId())
        {
            ckDuplicatedKey.setBackgroundResource(R.drawable.border_wrong_answer);
            Toast.makeText(this, "Sorry! You have chosen the wrong answer", Toast.LENGTH_SHORT).show();
        }

        if (id == btnNext.getId())
        {
            Intent intent = new Intent(this, ExerciseBasicViews5.class);
            startActivity(intent);
            finish();
        }

        if (id == fbtnBack.getId())
        {
            finish();
        }
    }
}