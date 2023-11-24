package com.example.mastersql;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FinishExerciseActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnHome, btnReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_exercise);
        initialize();
    }

    private void initialize() {
        btnHome = findViewById(R.id.btnHome);
        btnHome.setOnClickListener(this);
        btnReview = findViewById(R.id.btnReview);
        btnReview.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btnHome)
        {
            Intent intent = new Intent( this, MainActivity.class );
            startActivity( intent );
        }

        if (id == R.id.btnReview)
        {
            ExerciseList.fa.finish();
            finish();
        }
    }
}