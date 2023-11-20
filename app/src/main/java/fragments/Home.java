package fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mastersql.AddCourse;
import com.example.mastersql.R;
import com.example.mastersql.SubcourseListActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import adapter.CourseAdapter;
public class Home extends Fragment implements View.OnClickListener {
    private ListView lvCourses;

    private ImageView btnAddCourse;
    private View mRootView, subCourseView;

    private DatabaseReference courseDatabase, userRole;
    private FirebaseStorage storage;
    private StorageReference storageReference, sRef;
    private ActivityResultLauncher aResL;
    private FirebaseUser currUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate( R.layout.fragment_home, container, false );
        initialize();
        return mRootView;
    }

    private void initialize() {

        lvCourses = (ListView) mRootView.findViewById( R.id.lvCourses );

        ArrayList<String> list = new ArrayList<>();
        CourseAdapter adapter = new CourseAdapter( getActivity(), list );
        lvCourses.setAdapter( adapter );

        lvCourses.setClickable( true );

        btnAddCourse = (ImageView) mRootView.findViewById( R.id.btnAddCourse );
        btnAddCourse.setOnClickListener( this );

        //Initialization of Objects to Firebase database & Storage

        currUser = FirebaseAuth.getInstance().getCurrentUser();
        String safeEmail = currUser.getEmail()
                .replace( "@", "-" )
                .replace( ".", "-" );

        userRole = FirebaseDatabase.getInstance().getReference()
                .child( "Users" )
                .child( safeEmail )
                .child( "role" );
        userRole.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d( "USER_ROLE", snapshot.getValue().toString() );
                if (snapshot.getValue().toString().equals( "Admin" )) {
                    btnAddCourse.setVisibility( View.VISIBLE );
                    lvCourses.setOnItemLongClickListener( new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                            return false;
                        }
                    } );
                } else
                    btnAddCourse.setVisibility( View.INVISIBLE );
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        } );
        courseDatabase = FirebaseDatabase.getInstance().getReference().child( "Courses" );

        courseDatabase.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                    list.add( snapshot1.getKey() );

                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        } );

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        runActivityResLauncher();
        lvCourses.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent( getContext(), SubcourseListActivity.class );
                String text = adapterView.getItemAtPosition( i ).toString();
                intent.putExtra( "item_text", text );
                startActivity( intent );
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
    }

    private void view() {
        Intent intent = new Intent( getContext(), AddCourse.class );
        startActivity( intent );
        //getActivity().finishAffinity();
    }
}