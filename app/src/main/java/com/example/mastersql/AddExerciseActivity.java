package com.example.mastersql;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import fragments.AdminDashboard;
import model.Content;

public class AddExerciseActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edSubcourseName, edQuestion, edAnswer1, edAnswer2, edAnswer3, edCorrectAnswer;
    private Button btnAdd;
    private ImageView ivBack;
    private int totalQuestions;
    DatabaseReference exerciseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);
        initialize();
    }

    private void initialize() {

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        ivBack = findViewById(R.id.ivBack );
        ivBack.setOnClickListener(this);

        edSubcourseName = findViewById(R.id.edSubcourseName);
        edQuestion = findViewById(R.id.edQuestion);
        edAnswer1 = findViewById(R.id.edAnswer1);
        edAnswer2 = findViewById(R.id.edAnswer2);
        edAnswer3 = findViewById(R.id.edAnswer3);
        edCorrectAnswer = findViewById(R.id.edCorrectAnswer);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnAdd)
        {
            fetchTotalQuestions();
            if (totalQuestions > 0)
            {
                totalQuestions += 1;
                String questionKey = "Question" + totalQuestions;
                exerciseDatabase = FirebaseDatabase.getInstance().getReference("Exercises").child(edSubcourseName.getText().toString()).child(questionKey);
                String QuestionNumber = exerciseDatabase.getKey();
                ArrayList<String> answers = new ArrayList<String>();
                answers.add(edAnswer1.getText().toString());
                answers.add(edAnswer2.getText().toString());
                answers.add(edAnswer3.getText().toString());
                answers.add(edCorrectAnswer.getText().toString());

                HashMap<String, Object> subCourseMap = new HashMap<>();
                subCourseMap.put("Question", edQuestion.getText().toString());
                subCourseMap.put("Answers", answers);
                exerciseDatabase.setValue(subCourseMap);

                Toast.makeText(this, "Question has been added", Toast.LENGTH_SHORT).show();
                edSubcourseName.setText(null);
                edAnswer1.setText(null);
                edAnswer2.setText(null);
                edAnswer3.setText(null);
                edCorrectAnswer.setText(null);
                edQuestion.setText(null);
            }
            else {
                fetchTotalQuestions();
                Toast.makeText(this, "Try Again",Toast.LENGTH_SHORT);
            }
        }
    }
    private void showAlert(String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder( getApplicationContext() );

        builder.setTitle( "Notification" )
                .setMessage( message )
                .setPositiveButton( "OK", (dialogInterface, i) -> {
                } )
                .show();

    }

    private void fetchTotalQuestions() {
        exerciseDatabase = FirebaseDatabase.getInstance().getReference("Exercises").child(edSubcourseName.getText().toString());

        exerciseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Count the number of child nodes under the subject
                    totalQuestions = (int) snapshot.getChildrenCount();
                } else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}