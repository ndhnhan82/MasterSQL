package com.example.mastersql;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;

import model.Content;

public class AddCourse extends AppCompatActivity implements View.OnClickListener {

    private EditText editTxtCourseTitle, editTxtSubCourseTitle, editTxtCourseID, editTxtSubCourseID, edTxtContentHeader, edTxtContentText;

    private Button btnAdd;

    private ImageView ivBack;

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

        ivBack = findViewById(R.id.ivBack );
        ivBack.setOnClickListener(this);

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

        if(id == R.id.ivBack)
            finish();


    }



    @SuppressLint("NotConstructor")
    private void AddCourse() {

        ArrayList<String> Header = new ArrayList<String>();
        Header.add(edTxtContentHeader.getText().toString());

        ArrayList<String> Text = new ArrayList<String>();
        Text.add(edTxtContentText.getText().toString());

       // int subCourseID = Integer.parseInt(editTxtSubCourseID.getText().toString());
        //int courseID = Integer.parseInt(editTxtCourseID.getText().toString());

        Content content = new Content(1, Header, Text);

        // Assuming editTxtSubCourseKey is an EditText where the user inputs the key name for subCourse
        String subCourseKey = editTxtSubCourseTitle.getText().toString();

        // Create a HashMap or use a POJO if your SubCourse class is structured to handle this
        HashMap<String, Object> subCourseMap = new HashMap<>();
        subCourseMap.put("title", editTxtSubCourseTitle.getText().toString());
        subCourseMap.put("content", content); // Make sure your Content class is structured to convert to a Map

        // Create a HashMap or use a POJO for Courses
        HashMap<String, Object> courseMap = new HashMap<>();
        courseMap.put(subCourseKey, subCourseMap); // Use the user-defined key here

        // Set the value using the course title as the key
        courseDatabase.child(editTxtCourseTitle.getText().toString()).setValue(courseMap);



    }


}




