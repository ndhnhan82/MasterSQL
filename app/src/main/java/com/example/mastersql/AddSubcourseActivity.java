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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;

public class AddSubcourseActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTxtSubCourseTitle, editTxtSubCourseID, edTxtContentHeader, edTxtContentText;

    private Button btnAdd;

    private String text;

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
        setContentView(R.layout.activity_add_subcourse);


        initialize();

    }

    private void initialize() {

        text = getIntent().getStringExtra("course_title");

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);


        editTxtSubCourseTitle = findViewById(R.id.edTxtSubCourseTitle);
        editTxtSubCourseID = findViewById(R.id.edTxtSubCourseID);
        edTxtContentHeader = findViewById(R.id.edTxtContentHeader);
        edTxtContentText = findViewById(R.id.editTextContentText);


        //Initialization of Objects to Firebase database & Storage

        courseDatabase = FirebaseDatabase.getInstance().getReference("Courses");

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
            AddSubCourse();

    }

    private void AddSubCourse() {

        ArrayList<String> Header = new ArrayList<String>();
        Header.add(edTxtContentHeader.getText().toString());

        ArrayList<String> Text = new ArrayList<String>();
        Text.add(edTxtContentText.getText().toString());

        int subCourseID = Integer.parseInt(editTxtSubCourseID.getText().toString());


        Content content = new Content(2, Header, Text);

        // Assuming editTxtSubCourseKey is an EditText where the user inputs the key name for subCourse
        String subCourseKey = editTxtSubCourseTitle.getText().toString();

        // Create a HashMap or use a POJO if your SubCourse class is structured to handle this
        HashMap<String, Object> subCourseMap = new HashMap<>();
        subCourseMap.put("id", subCourseID);
        subCourseMap.put("title", editTxtSubCourseTitle.getText().toString());
        subCourseMap.put("content", content); // Make sure your Content class is structured to convert to a Map



        // Set the value using the course title as the key
        courseDatabase.child(text).child(subCourseKey).setValue(subCourseMap);


    }
}