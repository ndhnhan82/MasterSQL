package com.example.mastersql;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import adapter.CourseAdapter;
import adapter.ProgressAdapter;
import model.CategoryProgress;

public class UserDashboardActivity extends AppCompatActivity {

    private ListView lvCourseProgress;

    private int currentProgress;

    private ProgressBar pbCoursesDashboard;


    //For Realtime database
    DatabaseReference courseDatabase;

    //For Firebase Storage
    FirebaseStorage storage;

    StorageReference storageReference, sRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        initialize();
    }

    private void initialize() {
        lvCourseProgress = findViewById(R.id.lvCourseProgress);
        ArrayList<CategoryProgress> list = new ArrayList<>();
        ProgressAdapter adapter = new ProgressAdapter(this, list, 3);
        lvCourseProgress.setAdapter(adapter);

        DatabaseReference courseDatabase = FirebaseDatabase.getInstance().getReference()
                .child("Users").child("c-gmail-com").child("PROGRESS");

        courseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot categorySnapshot : snapshot.getChildren()) {
                    String categoryName = categorySnapshot.getKey();
                    int completedCount = 0;
                    int totalCount = (int) categorySnapshot.getChildrenCount();
                    for (DataSnapshot subcourseSnapshot : categorySnapshot.getChildren()) {
                        if (subcourseSnapshot.getValue(Boolean.class)) {
                            completedCount++;
                        }
                    }
                    list.add(new CategoryProgress(categoryName, completedCount, totalCount));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database read error
            }
        });
    }





    private void showAlert(String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder( UserDashboardActivity.this );

        builder.setTitle( "Notification" )
                .setMessage( message )
                .setPositiveButton( "OK", (dialogInterface, i) -> {
                } )
                .show();

    }


}