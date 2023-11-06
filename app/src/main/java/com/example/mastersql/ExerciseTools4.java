package com.example.mastersql;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ExerciseTools4 extends AppCompatActivity implements View.OnClickListener {

    Button btnCancel, btnDone;
    FloatingActionButton fbtnBack;
    CheckBox ckMicrosoftQl, ckOracle, ckMySQL, ckSQLITE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_tools4);
        intialize();
    }

    private void intialize() {
        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(this);
        btnDone = findViewById(R.id.btnDone);
        btnDone.setOnClickListener(this);
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

        if (id == btnCancel.getId() || id == btnCancel.getId())
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
            ckMicrosoftQl.setBackgroundResource(R.drawable.border_wrong_answer);
            Toast.makeText(this, "Sorry! You have chosen the wrong answer", Toast.LENGTH_SHORT).show();
        }

        if (id == ckSQLITE.getId())
        {
            ckSQLITE.setBackgroundResource(R.drawable.border_right_answer);
            Toast.makeText(this, "Congratulations! You can go to the next exercise", Toast.LENGTH_SHORT).show();
            btnDone.setVisibility(View.VISIBLE);
        }

        if (id == ckMySQL.getId())
        {
            ckMySQL.setBackgroundResource(R.drawable.border_wrong_answer);
            Toast.makeText(this, "Sorry! You have chosen the wrong answer", Toast.LENGTH_SHORT).show();
        }

        if (id == fbtnBack.getId())
        {
            finish();
        }
    }
}