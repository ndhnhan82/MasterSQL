package com.example.mastersql;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import adapter.CourseAdapter;

public class SubcourseListActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ListView lvCourses;
    private int currentProgress;

    private ImageView imAddCourse, imBack;

    private String text;

    private Button btnTakeAQuiz;

    private ProgressBar pbSubcourses;
    private TextView tvProgress;
    private Handler handler = new Handler();


    //For Realtime database
    DatabaseReference courseDatabase;

    //For Firebase Storage
    FirebaseStorage storage;

    StorageReference storageReference, sRef;

    //For receiving results (image) when we click the button browse
    ActivityResultLauncher aResL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_subcourse_list );
        initialize();
    }

    private void initialize() {
        userIdentity();
        tvProgress = findViewById( R.id.tvProgress );
        pbSubcourses = findViewById( R.id.pbSubCourses );

        text = getIntent().getStringExtra( "item_text" );

        lvCourses = findViewById( R.id.lvCourses );

        ArrayList<String> list = new ArrayList<>();
        CourseAdapter adapter = new CourseAdapter( SubcourseListActivity.this, list, 1 );

        lvCourses.setAdapter( adapter );


        lvCourses.setClickable( true );

        imAddCourse = findViewById( R.id.btnAddCourse );
        imAddCourse.setOnClickListener( this );

        imBack = findViewById( R.id.imBack );
        imBack.setOnClickListener( this );

        btnTakeAQuiz = findViewById( R.id.btnTakeQuiz );
        btnTakeAQuiz.setOnClickListener( this );


        //Initialization of Objects to Firebase database & Storage

        courseDatabase = FirebaseDatabase.getInstance().getReference().child( "Courses" ).child( text );


        courseDatabase.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    list.add( snapshot1.getKey() );
                }
                adapter.notifyDataSetChanged();

                int adapterCount = adapter.getCount();
                currentProgress = pbSubcourses.getProgress();
                pbSubcourses.setMax( adapterCount );
                tvProgress.setText( 0 + "/" + pbSubcourses.getMax() );

                if (currentProgress == pbSubcourses.getMax()) {
                    btnTakeAQuiz.setVisibility( View.VISIBLE );
                } else {
                    btnTakeAQuiz.setVisibility( View.GONE );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        } );


        storage = FirebaseStorage.getInstance();

        storageReference = storage.getReference();

        // Registration of Activity Result Launcher

        runActivityResLauncher();


        btnTakeAQuiz.setVisibility( View.GONE );

        lvCourses.setOnItemClickListener( this );

    }

    private void userIdentity() {
        String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail()
                .replace( "@", "-" )
                .replace( ".", "-" );

        FirebaseDatabase.getInstance().getReference( "Users" ).child( userEmail ).child( "role" ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue().toString().equals( "Admin" )) {
                    btnTakeAQuiz.setVisibility( View.INVISIBLE );
                    pbSubcourses.setVisibility( View.INVISIBLE );
                    tvProgress.setVisibility( View.INVISIBLE );
                    imAddCourse.setVisibility( View.VISIBLE );

                } else {
                    imAddCourse.setVisibility( View.INVISIBLE );
                    btnTakeAQuiz.setVisibility( View.VISIBLE );
                    pbSubcourses.setVisibility( View.VISIBLE );
                    tvProgress.setVisibility( View.VISIBLE );

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        } );
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

        if (id == R.id.btnAddCourse)
            view();

        if (id == R.id.imBack)
            finish();

        if (id == R.id.btnTakeQuiz)
            takeQuiz();

    }

    private void takeQuiz() {
        Intent intent = new Intent( this, ExerciseList.class );
        intent.putExtra( "course_title", text );
        startActivity( intent );
    }

    private void view() {

        //showAlert("The text value is: " + text);

        Intent intent = new Intent( SubcourseListActivity.this, AddSubCourseActivity.class );


        intent.putExtra( "course_title", text );

        startActivity( intent );
        //SubcourseListActivity.this.finishAffinity();


    }

    private void showAlert(String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder( SubcourseListActivity.this );

        builder.setTitle( "Notification" )
                .setMessage( message )
                .setPositiveButton( "OK", (dialogInterface, i) -> {
                } )
                .show();

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        if (currentProgress < pbSubcourses.getMax()) {
            currentProgress += 1;
            pbSubcourses.setProgress( currentProgress );
            tvProgress.setText( currentProgress + "/" + pbSubcourses.getMax() );
        }
        if (currentProgress == pbSubcourses.getMax()) {
            btnTakeAQuiz.setVisibility( View.VISIBLE );
        }


        String subCourseTitle = adapterView.getItemAtPosition( position ).toString();

        Intent intent = new Intent( this, SubCourseContentActivity.class );
        intent.putExtra( "Course_title", text );

        intent.putExtra( "subCourse_title", subCourseTitle );

        startActivity( intent );
    }
}

