package com.example.mastersql;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
    private TextView tvQuestion;
    private ImageView imBack;
    private String topic;
    private ListView lvAnswers;
    private int currentQuestionNumber = 1;
    private int totalQuestions = 0;

    //For Realtime database
    DatabaseReference courseDatabase;
    FirebaseStorage storage;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_exercise_list );
        initialize();
        fetchTotalQuestions();
    }

    private void initialize() {

        topic = getIntent().getStringExtra("course_title");
        Log.d( "TESTING", topic );
//        topic = "";
        btnCancel = findViewById( R.id.btnCancel );
        btnCancel.setOnClickListener( this );

        btnNext = findViewById( R.id.btnNext );
        btnNext.setOnClickListener( this );

        imBack = findViewById( R.id.imBack );
        imBack.setOnClickListener( this );

        tvQuestion = findViewById( R.id.tvQuestion );

        lvAnswers = findViewById( R.id.lvAnswers );


        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
    }


    private void loadQuestion(int questionNumber) {
        String questionKey = "Question" + questionNumber;
        courseDatabase = FirebaseDatabase.getInstance().getReference( "Exercises" ).child( topic ).child( questionKey );

        ArrayList<String> list = new ArrayList<>();
        CourseAdapter adapter = new CourseAdapter( ExerciseList.this, list, 2 );
        lvAnswers.setAdapter( adapter );

        courseDatabase.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    list.clear();
                    String questionText = snapshot.child( "Question" ).getValue( String.class );
                    tvQuestion.setText( questionText );

                    DataSnapshot answersSnapshot = snapshot.child( "Answers" );
                    for (DataSnapshot answerSnapshot : answersSnapshot.getChildren()) {
                        String answer = answerSnapshot.getValue( String.class );
                        list.add( answer );
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
        } );
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnCancel)
            cancel();
        else if (id == R.id.btnNext)
            next();
        else if (id == R.id.imBack)
            back();
    }

    private void back() {
        finish();
    }

    private void next() {
        if (currentQuestionNumber < totalQuestions) {
            currentQuestionNumber++; // Move to the next question
            loadQuestion( currentQuestionNumber ); // Load the next question
        } else {
            // Handle the end of questions
            Intent intent = new Intent( ExerciseList.this, FinishExerciseActivity.class );
            startActivity( intent );
        }
    }

    private void cancel() {
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
        Log.d( "TESTING", topic );

        DatabaseReference subjectRef = FirebaseDatabase.getInstance().getReference( "Exercises" ).child( topic );
        Log.d( "TESTING", "3" );

        subjectRef.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Log.d( "TESTING", "4" );

                    // Count the number of child nodes under the subject
                    totalQuestions = (int) snapshot.getChildrenCount();
                    loadQuestion( currentQuestionNumber ); // Load the first question
                    Log.d( "TESTING", "5" );

                } else {
                    Log.d( "TESTING", "6" );

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        } );
    }

}
