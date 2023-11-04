package com.example.mastersql;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mastersql.model.Content;
import com.example.mastersql.model.Courses;
import com.example.mastersql.model.SubCourse;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class AddCourse extends AppCompatActivity implements View.OnClickListener {

    private EditText editTxtCourseTitle, editTxtSubCourseTitle, editTxtCourseID, editTxtSubCourseID, edTxtContentHeader, edTxtContentText;

    private Button btnAdd;

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
        setContentView(R.layout.activity_add_course);

        initialize();
    }

    private void initialize() {

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        editTxtCourseTitle = findViewById(R.id.edTxtCourseTitle);
        editTxtCourseID = findViewById(R.id.edTxtCourseID);
        editTxtSubCourseTitle = findViewById(R.id.edTxtSubCourseTitle);
        editTxtSubCourseID = findViewById(R.id.edTxtSubCourseID);
        edTxtContentHeader = findViewById(R.id.edTxtContentHeader);
        edTxtContentText = findViewById(R.id.editTextContentText);


        //Initialization of Objects to Firebase database & Storage

        courseDatabase = FirebaseDatabase.getInstance().getReference("Courses");

        subDatabase = FirebaseDatabase.getInstance().getReference("SubCourse");

        storage = FirebaseStorage.getInstance();

        storageReference = storage.getReference();

        // Registration of Activity Result Launcher

        runActivityResLauncher();


    }

    private void runActivityResLauncher() {

        aResL = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),

                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {

                    }
                }
        );

    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        if(id == R.id.btnAdd)
            AddCourse();

    }

    private void AddCourse() {

        ArrayList<String> Header = new ArrayList<String>();
        Header.add(edTxtContentHeader.getText().toString());

        ArrayList<String> Text = new ArrayList<String>();
        Text.add(edTxtContentText.getText().toString());

        Content content = new Content(1, Header, Text);

        SubCourse sbCourse = new SubCourse(editTxtSubCourseID.getId(), editTxtSubCourseTitle.getText().toString(), content);

        Courses c = new Courses( editTxtCourseID.getId(), sbCourse);

        courseDatabase.child(editTxtCourseTitle.getText().toString()).setValue(c);



    }
}