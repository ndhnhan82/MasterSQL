package com.example.mastersql;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ExerciseTools1 extends AppCompatActivity implements View.OnClickListener {
    Button btnCancel, btnNext;
    FloatingActionButton fbtnBack;
    CheckBox ckMicrosoftQl, ckOracle, ckMySQL, ckSQLITE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_tools1);
        intialize();
    }

    private void intialize() {
        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(this);
        btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(this);
        ckMicrosoftQl = findViewById(R.id.ckMcSQL);
        ckMicrosoftQl.setOnClickListener(this);
        ckOracle = findViewById(R.id.ckOracle);
        ckOracle.setOnClickListener(this);
        ckMySQL = findViewById(R.id.ckMySQL);
        ckMySQL.setOnClickListener(this);
        ckSQLITE = findViewById(R.id.ckSQLITE);
        ckSQLITE.setOnClickListener(this);
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

        if (id == ckOracle.getId())
        {
            ckOracle.setBackgroundResource(R.drawable.border_wrong_answer);
            Toast.makeText(this, "Sorry! You have chosen the wrong answer", Toast.LENGTH_SHORT).show();
        }

        if (id == ckMicrosoftQl.getId())
        {
            ckMicrosoftQl.setBackgroundResource(R.drawable.border_right_answer);
            Toast.makeText(this, "Congratulations! You can go to the next exercise", Toast.LENGTH_SHORT).show();
            btnNext.setVisibility(View.VISIBLE);
        }
        if (id == ckSQLITE.getId())
        {
            ckSQLITE.setBackgroundResource(R.drawable.border_wrong_answer);
            Toast.makeText(this, "Sorry! You have chosen the wrong answer", Toast.LENGTH_SHORT).show();
        }

        if (id == ckMySQL.getId())
        {
            ckMySQL.setBackgroundResource(R.drawable.border_wrong_answer);
            Toast.makeText(this, "Sorry! You have chosen the wrong answer", Toast.LENGTH_SHORT).show();
        }

        if (id == btnNext.getId())
        {
            Intent intent = new Intent(this, Tools2Exercise.class);
            startActivity(intent);
            //finish();
        }

        if (id == fbtnBack.getId())
        {
            finish();
        }
    }
}