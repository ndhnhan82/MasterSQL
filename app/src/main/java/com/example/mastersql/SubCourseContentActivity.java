package com.example.mastersql;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class SubCourseContentActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView imBack;
    private EditText editText;
    private TextView headerTitle;
    private String text, courseTitle;
    DatabaseReference subCourseHeaderDatabase, subCourseTextDatabase;
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_course_content);

        initialize();

    }

    private void initialize() {
        text = getIntent().getStringExtra("subCourse_title");
        courseTitle = getIntent().getStringExtra("Course_title");
        editText = findViewById(R.id.editTextContentText);
        headerTitle = findViewById(R.id.tvSubCourseHeader);
        imBack = findViewById(R.id.imBack );
        imBack.setOnClickListener(this);
        //Initialization of Objects to Firebase database & Storage

        subCourseHeaderDatabase = FirebaseDatabase.getInstance().getReference().child("Courses").child(courseTitle).child(text).child("content").child("header").child("0");

        subCourseHeaderDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                // Check if the dataSnapshot has data
                if (snapshot.exists()) {
                    // Assuming the data is of type String
                    String header = snapshot.getValue(String.class);
                    headerTitle.setText(header); // Set the text from the database
                } else {
                    headerTitle.setText("No content available"); // Handle case where there is no data
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                headerTitle.setText("Error loading content");
            }
        });

        subCourseTextDatabase = FirebaseDatabase.getInstance().getReference().child("Courses").child(courseTitle).child(text).child("content").child("text").child("0");

        subCourseTextDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                // Check if the dataSnapshot has data
                if (snapshot.exists()) {
                    // Assuming the data is of type String
                    String subText = snapshot.getValue(String.class);
                    editText.setText(subText); // Set the text from the database
                } else {
                    editText.setText("No content available"); // Handle case where there is no data
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                editText.setText("Error loading content");
            }
        });
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
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