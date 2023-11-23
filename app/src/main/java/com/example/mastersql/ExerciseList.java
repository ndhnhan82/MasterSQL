package com.example.mastersql;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ExerciseList extends AppCompatActivity implements View.OnClickListener {
    private Button btnCancel, btnNext;
    private CheckBox ckAnswer1,ckAnswer2,ckAnswer3,ckAnswer4;
    private TextView tvAnswer1,tvAnswer2,tvAnswer3,tvAnswer4, tvQuestion;
    private FloatingActionButton fabBack;
    private String text, questionIntent;

    //For Realtime database
    DatabaseReference courseDatabase, subDatabase;

    //For Firebase Storage
    FirebaseStorage storage;

    StorageReference storageReference, sRef;

    //For receiving results (image) when we click the button browse
    ActivityResultLauncher aResL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);
        initialize();
    }

    private void initialize() {
        text = getIntent().getStringExtra("course_title");
        questionIntent = getIntent().getStringExtra("question");
        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(this);
        btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(this);
        ckAnswer1 = findViewById(R.id.ckAnswer1);
        ckAnswer1.setOnClickListener(this);
        ckAnswer2 = findViewById(R.id.ckAnswer2);
        ckAnswer2.setOnClickListener(this);
        ckAnswer3 = findViewById(R.id.ckAnswer3);
        ckAnswer3.setOnClickListener(this);
        ckAnswer4 = findViewById(R.id.ckAnswer4);
        ckAnswer4.setOnClickListener(this);
        fabBack = findViewById(R.id.ivBack );
        fabBack.setOnClickListener(this);
        tvQuestion = findViewById(R.id.tvQuestion);
        tvAnswer1 = findViewById(R.id.tvAnswer1);
        tvAnswer2 = findViewById(R.id.tvAnswer2);
        tvAnswer3 = findViewById(R.id.tvAnswer3);
        tvAnswer4 = findViewById(R.id.tvAnswer4);
        //Initialization of Objects to Firebase database & Storage

        courseDatabase = FirebaseDatabase.getInstance().getReference("Exercises").child(text).child(questionIntent);

        courseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                showAlert(courseDatabase.toString());
                String questionText = snapshot.child("Question").getValue(String.class);
                tvQuestion.setText(questionText);
                String answerText1 = snapshot.child("Answers").child(String.valueOf(0)).getValue(String.class);
                tvAnswer1.setText(answerText1);
                String answerText2 = snapshot.child("Answers").child(String.valueOf(1)).getValue(String.class);
                tvAnswer2.setText(answerText2);
                String answerText3 = snapshot.child("Answers").child(String.valueOf(2)).getValue(String.class);
                tvAnswer3.setText(answerText3);
                String answerText4 = snapshot.child("Answers").child(String.valueOf(3)).getValue(String.class);
                tvAnswer4.setText(answerText4);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        storage = FirebaseStorage.getInstance();

        storageReference = storage.getReference();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnCancel)
            cancel();
        if (id == R.id.btnNext)
            next();
        if (id == R.id.ivBack)
            back();
    }

    private void back() {
        finish();
    }

    private void next() {
        Intent intent = new Intent(this, ExerciseList.class);
        intent.putExtra("question", "Question2");
        startActivity(intent);
    }

    private void cancel()
    {
        finish();
    }

    private void showAlert(String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder( ExerciseList.this );

        builder.setTitle( "Notification" )
                .setMessage( message )
                .setPositiveButton( "OK", (dialogInterface, i) -> {
                } )
                .show();

    }
}
