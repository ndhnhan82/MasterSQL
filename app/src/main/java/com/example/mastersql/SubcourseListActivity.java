package com.example.mastersql;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import adapter.CourseAdapter;
import model.Courses;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Objects;

public class SubcourseListActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView lvCourses;

    private FloatingActionButton btnAddCourse;

    private String text;




    //For Realtime database
    DatabaseReference courseDatabase;

    //For Firebase Storage
    FirebaseStorage storage;

    StorageReference storageReference, sRef;

    //For receiving results (image) when we click the button browse
    ActivityResultLauncher aResL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcourse_list);



        initialize();

    }

    private void initialize() {

        text = getIntent().getStringExtra("item_text");

        lvCourses = findViewById(R.id.lvCourses);


        ArrayList<String> list = new ArrayList<>();
        CourseAdapter adapter = new CourseAdapter(SubcourseListActivity.this, list);
        lvCourses.setAdapter(adapter);

        lvCourses.setClickable(true);

       btnAddCourse = findViewById(R.id.btnAddCourse);
       btnAddCourse.setOnClickListener(this);


        //Initialization of Objects to Firebase database & Storage

        courseDatabase = FirebaseDatabase.getInstance().getReference().child("Courses").child(text);

        courseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren() ){



                        list.add(snapshot1.getKey());








                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        storage = FirebaseStorage.getInstance();

        storageReference = storage.getReference();

        // Registration of Activity Result Launcher

        runActivityResLauncher();

        lvCourses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent( SubcourseListActivity.this, SubCourseContentActivity.class);

                String subCourseTitle =  adapterView.getItemAtPosition(i).toString();

                intent.putExtra("Course_title", text);

                intent.putExtra("subCourse_title", subCourseTitle);

                startActivity( intent );
            }
        });



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

        if(id == R.id.btnAddCourse)
            view();


    }

    private void view() {

        showAlert("The text value is: " + text);

        Intent intent = new Intent( SubcourseListActivity.this, AddSubcourseActivity.class );


        intent.putExtra("course_title", text);

        startActivity( intent );
        SubcourseListActivity.this.finishAffinity();


    }


    private void showAlert(String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder( SubcourseListActivity.this );

        builder.setTitle( "Notification" )
                .setMessage( message )
                .setPositiveButton( "OK", (dialogInterface, i) -> {
                } )
                .show();

    }
}