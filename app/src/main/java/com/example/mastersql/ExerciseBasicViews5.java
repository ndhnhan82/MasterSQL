package com.example.mastersql;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ExerciseBasicViews5 extends AppCompatActivity implements View.OnClickListener {

    Button btnCancel, btnDone;
    FloatingActionButton fbtnBack;
    CheckBox ck0, ck3, ck2, ck1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_basic_views5);
        initialize();
    }

    private void initialize() {
        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(this);
        btnDone = findViewById(R.id.btnDone);
        btnDone.setOnClickListener(this);
        ck0 = findViewById(R.id.ckZero);
        ck0.setOnClickListener(this);
        ck3 = findViewById(R.id.ckThree);
        ck3.setOnClickListener(this);
        ck2 = findViewById(R.id.ckTwo);
        ck2.setOnClickListener(this);
        ck1 = findViewById(R.id.ckOne);
        ck1.setOnClickListener(this);
        fbtnBack = findViewById(R.id.ivBack );
        fbtnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == btnCancel.getId() || id == btnDone.getId())
        {
            finish();
        }

        if (id == ck0.getId())
        {
            ck0.setBackgroundResource(R.drawable.border_wrong_answer);
            Toast.makeText(this, "Sorry! You have chosen the wrong answer", Toast.LENGTH_SHORT).show();
        }

        if (id == ck1.getId())
        {
            ck1.setBackgroundResource(R.drawable.border_right_answer);
            Toast.makeText(this, "Congratulations! You can go to the next exercise", Toast.LENGTH_SHORT).show();
            btnDone.setVisibility(View.VISIBLE);
        }

        if (id == ck2.getId())
        {
            ck2.setBackgroundResource(R.drawable.border_wrong_answer);
            Toast.makeText(this, "Sorry! You have chosen the wrong answer", Toast.LENGTH_SHORT).show();
        }

        if (id == ck3.getId())
        {
            ck3.setBackgroundResource(R.drawable.border_wrong_answer);
            Toast.makeText(this, "Sorry! You have chosen the wrong answer", Toast.LENGTH_SHORT).show();
        }

        if (id == fbtnBack.getId())
        {
            finish();
        }
    }
}