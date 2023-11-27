package com.example.mastersql;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SubCourseContentActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView imBack;
    private EditText edContent;
    private TextView tvHeader;
    private String subTitle, courseTitle;
    DatabaseReference subCourseHeaderDatabase, subCourseTextDatabase,userProgressRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_course_content);

        initialize();

    }

    private void initialize() {
        subTitle = getIntent().getStringExtra("subCourse_title");
        courseTitle = getIntent().getStringExtra("Course_title");
        edContent = findViewById(R.id.editTextContentText);
        tvHeader = findViewById(R.id.tvSubCourseHeader);
        imBack = findViewById(R.id.imBack );
        imBack.setOnClickListener(this);
        //Initialization of Objects to Firebase database & Storage

        setHeader();
        setProgressValue();
        setContent();

    }

    private void setContent() {
        subCourseTextDatabase = FirebaseDatabase.getInstance().getReference().child("Courses")
                .child(courseTitle).child( subTitle ).child("content")
                .child("text").child("0");
        subCourseTextDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                // Check if the dataSnapshot has data
                if (snapshot.exists()) {
                    // Assuming the data is of type String
                    String subText = snapshot.getValue(String.class);
                    edContent.setText(subText); // Set the text from the database
                } else {
                    edContent.setText("No content available"); // Handle case where there is no data
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                edContent.setText("Error loading content");
            }
        });
    }

    private void setProgressValue() {
        String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail()
                .replace("@", "-").replace(".", "-");
        userProgressRef = FirebaseDatabase.getInstance().getReference()
                .child("Users").child(userEmail).child("PROGRESS")
                .child( courseTitle ).child( subTitle );

        userProgressRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Check if the dataSnapshot has data
                if (snapshot.exists()) {
                    if (!snapshot.getValue().equals(true))
                    {
                        userProgressRef.setValue(true);

                    }
                }
                else {
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

               Log.e("Firebase", "Error updating value: " + error.getMessage());

            }
        });
    }

    private void setHeader() {
        subCourseHeaderDatabase = FirebaseDatabase.getInstance().getReference().child("Courses").child(courseTitle).child( subTitle ).child("content").child("header").child("0");
        subCourseHeaderDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                // Check if the dataSnapshot has data
                if (snapshot.exists()) {
                    // Assuming the data is of type String
                    String header = snapshot.getValue(String.class);
                    tvHeader.setText(header); // Set the text from the database
                } else {
                    tvHeader.setText("No content available"); // Handle case where there is no data
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                tvHeader.setText("Error loading content");
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == imBack.getId())
        {
            finish();
        }
    }
}