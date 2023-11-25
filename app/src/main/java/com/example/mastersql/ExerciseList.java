package com.example.mastersql;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import adapter.CourseAdapter;

public class ExerciseList extends AppCompatActivity implements View.OnClickListener {
    private Button btnCancel, btnNext;
    private CheckBox ckAnswer1,ckAnswer2,ckAnswer3,ckAnswer4;
    private TextView tvAnswer1,tvAnswer2,tvAnswer3,tvAnswer4, tvQuestion;
    private FloatingActionButton fabBack;
    private String text, questionIntent;
    public static Activity fa;

    private ListView lvAnswers;

    private int currentQuestionNumber = 1;
    private int totalQuestions = 0;

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
        fa = this;

        text = getIntent().getStringExtra("course_title");

        fetchTotalQuestions();

        initialize();
    }

    private void initialize() {


        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(this);

        btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(this);

        fabBack = findViewById(R.id.ivBack);
        fabBack.setOnClickListener(this);

        tvQuestion = findViewById(R.id.tvQuestion);

        lvAnswers = findViewById(R.id.lvAnswers);


        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
    }


    private void loadQuestion(int questionNumber) {
        String questionKey = "Question" + questionNumber;
        courseDatabase = FirebaseDatabase.getInstance().getReference("Exercises").child(text).child(questionKey);

        ArrayList<String> list = new ArrayList<>();
        CourseAdapter adapter = new CourseAdapter(ExerciseList.this, list, 2);
        lvAnswers.setAdapter(adapter);

        courseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    list.clear();
                    String questionText = snapshot.child("Question").getValue(String.class);
                    tvQuestion.setText(questionText);

                    DataSnapshot answersSnapshot = snapshot.child("Answers");
                    for (DataSnapshot answerSnapshot : answersSnapshot.getChildren()) {
                        String answer = answerSnapshot.getValue(String.class);
                        list.add(answer);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    // Handle the scenario where the question doesn't exist
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error
            }
        });
    }




    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnCancel)
            cancel();
        else if (id == R.id.btnNext)
            next();
        else if (id == R.id.ivBack)
            back();
    }

    private void back() {
        finish();
    }

    private void next() {
        if (currentQuestionNumber < totalQuestions) {
            currentQuestionNumber++; // Move to the next question
            loadQuestion(currentQuestionNumber); // Load the next question
        } else {
            // Handle the end of questions
            Intent intent = new Intent( ExerciseList.this, FinishExerciseActivity.class );
            startActivity( intent );
        }
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



    private void fetchTotalQuestions() {
        DatabaseReference subjectRef = FirebaseDatabase.getInstance().getReference("Exercises").child(text);

        subjectRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Count the number of child nodes under the subject
                    totalQuestions = (int) snapshot.getChildrenCount();
                    loadQuestion(currentQuestionNumber); // Load the first question
                } else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
