package com.example.mastersql;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class SubCourseContentActivity extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton btnAddCourse, fbtnBack;

    private EditText editText;

    private TextView headerTitle;

    private String text, courseTitle;


    //For Realtime database
    DatabaseReference subCourseHeaderDatabase, subCourseTextDatabase, subCourseProgressDatabase;

    //For Firebase Storage
    FirebaseStorage storage;

    StorageReference storageReference, sRef;

    //For receiving results (image) when we click the button browse
    ActivityResultLauncher aResL;
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

        btnAddCourse = findViewById(R.id.btnAddCourse);

        headerTitle = findViewById(R.id.tvSubCourseHeader);

        fbtnBack = findViewById(R.id.ivBack );
        fbtnBack.setOnClickListener(this);



        //Initialization of Objects to Firebase database & Storage

        subCourseHeaderDatabase = FirebaseDatabase.getInstance().getReference().child("Courses").child(courseTitle).child(text).child("content").child("header").child("0");

        String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail().replace("@", "-").replace(".", "-");

        subCourseProgressDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userEmail).child("PROGRESS").child(courseTitle).child(text);

        showAlert(subCourseProgressDatabase.getKey().toString());

        subCourseProgressDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                // Check if the dataSnapshot has data
                if (snapshot.exists()) {
                    if (!snapshot.getValue().equals(true))
                    {
                        subCourseProgressDatabase.setValue(true);
                    }
                    else {

                    }
                } else {
                        showAlert("No subcourse exists");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

               showAlert("Action Cancelled");

            }
        });


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

        if (id == fbtnBack.getId())
        {
            finish();
        }


    }
    private void showAlert(String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder( SubCourseContentActivity.this );

        builder.setTitle( "Notification" )
                .setMessage( message )
                .setPositiveButton( "OK", (dialogInterface, i) -> {
                } )
                .show();

    }
}