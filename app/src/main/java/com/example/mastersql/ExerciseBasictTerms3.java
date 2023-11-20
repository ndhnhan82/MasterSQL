package com.example.mastersql;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ExerciseBasictTerms3 extends AppCompatActivity implements View.OnClickListener {

    Button btnCancel, btnNext;
    FloatingActionButton fbtnBack;
    CheckBox ckSelectedRows, ckInclude, ckEverything, ckNothing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_basict_terms3);
        intialize();
    }

    private void intialize() {
        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(this);
        btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(this);
        ckSelectedRows = findViewById(R.id.ckSelectedRows);
        ckSelectedRows.setOnClickListener(this);
        ckInclude = findViewById(R.id.ckInclude);
        ckInclude.setOnClickListener(this);
        ckEverything = findViewById(R.id.ckEverything);
        ckEverything.setOnClickListener(this);
        ckNothing = findViewById(R.id.ckNothing);
        ckNothing.setOnClickListener(this);
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

        if (id == ckSelectedRows.getId())
        {
            ckSelectedRows.setBackgroundResource(R.drawable.border_wrong_answer);
            Toast.makeText(this, "Sorry! You have chosen the wrong answer", Toast.LENGTH_SHORT).show();
        }

        if (id == ckEverything.getId())
        {
            ckEverything.setBackgroundResource(R.drawable.border_right_answer);
            Toast.makeText(this, "Congratulations! You can go to the next exercise", Toast.LENGTH_SHORT).show();
            btnNext.setVisibility(View.VISIBLE);
        }

        if (id == ckInclude.getId())
        {
            ckInclude.setBackgroundResource(R.drawable.border_wrong_answer);
            Toast.makeText(this, "Sorry! You have chosen the wrong answer", Toast.LENGTH_SHORT).show();
        }

        if (id == ckNothing.getId())
        {
            ckNothing.setBackgroundResource(R.drawable.border_wrong_answer);
            Toast.makeText(this, "Sorry! You have chosen the wrong answer", Toast.LENGTH_SHORT).show();
        }
        if (id == btnNext.getId())
        {
            Intent intent = new Intent(this, ExerciseBasicTerms4.class);
            startActivity(intent);
            finish();
        }
        if (id == fbtnBack.getId())
        {
            finish();
        }
    }
}