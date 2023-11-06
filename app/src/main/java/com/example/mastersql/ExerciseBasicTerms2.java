package com.example.mastersql;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ExerciseBasicTerms2 extends AppCompatActivity implements View.OnClickListener {

    Button btnCancel, btnNext;
    FloatingActionButton fbtnBack;
    CheckBox ckUniqueIdent, ckDataType, ckTableName, ckSelectQuery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_basic_terms2);
        initialize();
    }

    private void initialize() {
        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(this);
        btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(this);
        ckUniqueIdent = findViewById(R.id.ckUniqueIdent);
        ckUniqueIdent.setOnClickListener(this);
        ckDataType = findViewById(R.id.ckDataType);
        ckDataType.setOnClickListener(this);
        ckTableName = findViewById(R.id.ckTableName);
        ckTableName.setOnClickListener(this);
        ckSelectQuery = findViewById(R.id.ckSelectQuery);
        ckSelectQuery.setOnClickListener(this);
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

        if (id == ckDataType.getId())
        {
            ckDataType.setBackgroundResource(R.drawable.border_wrong_answer);
            Toast.makeText(this, "Sorry! You have chosen the wrong answer", Toast.LENGTH_SHORT).show();
        }

        if (id == ckUniqueIdent.getId())
        {
            ckUniqueIdent.setBackgroundResource(R.drawable.border_right_answer);
            Toast.makeText(this, "Congratulations! You can go to the next exercise", Toast.LENGTH_SHORT).show();
            btnNext.setVisibility(View.VISIBLE);
        }

        if (id == ckTableName.getId())
        {
            ckTableName.setBackgroundResource(R.drawable.border_wrong_answer);
            Toast.makeText(this, "Sorry! You have chosen the wrong answer", Toast.LENGTH_SHORT).show();
        }

        if (id == ckSelectQuery.getId())
        {
            ckSelectQuery.setBackgroundResource(R.drawable.border_wrong_answer);
            Toast.makeText(this, "Sorry! You have chosen the wrong answer", Toast.LENGTH_SHORT).show();
        }

        if (id == btnNext.getId())
        {
            Intent intent = new Intent(this, ExerciseBasictTerms3.class);
            startActivity(intent);
            finish();
        }

        if (id == fbtnBack.getId())
        {
            finish();
        }
    }
}