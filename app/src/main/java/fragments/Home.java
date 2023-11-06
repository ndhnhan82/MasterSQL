package fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mastersql.AddCourse;
import com.example.mastersql.R;
import com.example.mastersql.SubcourseListActivity;
import adapter.CourseAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters


    private ListView lvCourses;

    //private Button btnAddCourse2;

    private FloatingActionButton btnAddCourse;

    private View mRootView, subCourseView;
    private String mParam1;
    private String mParam2;

    //For Realtime database
    DatabaseReference courseDatabase;

    //For Firebase Storage
    FirebaseStorage storage;

    StorageReference storageReference, sRef;

    //For receiving results (image) when we click the button browse
    ActivityResultLauncher aResL;





    public Home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home.
     */
    // TODO: Rename and change types and number of parameters
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
        Bundle args = new Bundle();
        args.putString( ARG_PARAM1, param1 );
        args.putString( ARG_PARAM2, param2 );
        fragment.setArguments( args );
        return fragment;


    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        if (getArguments() != null) {
            mParam1 = getArguments().getString( ARG_PARAM1 );
            mParam2 = getArguments().getString( ARG_PARAM2 );
        }
    }

    //private FragmentHomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        mRootView = inflater.inflate( R.layout.fragment_home, container, false );

        //binding = ActivityMainBinding.inflate(getLayoutInflater());
        //setContentView(binding.getRoot());




        initialize();

        return mRootView;
    }

    private void initialize() {

        lvCourses = (ListView) mRootView.findViewById(R.id.lvCourses);

        //ArrayList<String> list = new ArrayList<>();
        //ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.course_list, list);
        //lvCourses.setAdapter(adapter);

        ArrayList<String> list = new ArrayList<>();
        CourseAdapter adapter = new CourseAdapter(getActivity(), list);
        lvCourses.setAdapter(adapter);

        lvCourses.setClickable(true);


        //btnAddCourse = (Button) mRootView.findViewById(R.id.btnAddCourse);
        //btnAddCourse.setOnClickListener(this);

        btnAddCourse = (FloatingActionButton) mRootView.findViewById(R.id.btnAddCourse);
        btnAddCourse.setOnClickListener(this);



        //Initialization of Objects to Firebase database & Storage

        courseDatabase = FirebaseDatabase.getInstance().getReference().child("Courses");

        courseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren() ){
                    //Courses courses = snapshot1.getValue(Courses.class);
                    //if (courses != null) {
                      //  String txt = String.valueOf(courses.getId());
                        //list.add(txt);
                    //}

                    list.add(snapshot1.getKey());

                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //subDatabase = FirebaseDatabase.getInstance().getReference("SubCourse");

        storage = FirebaseStorage.getInstance();

        storageReference = storage.getReference();

        // Registration of Activity Result Launcher

        runActivityResLauncher();

        lvCourses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent( getContext(), SubcourseListActivity.class);


                String text =  adapterView.getItemAtPosition(i).toString();;


                intent.putExtra("item_text", text);

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

    private void view()
    {
        Intent intent = new Intent( getContext(), AddCourse.class );
        startActivity( intent );
        getActivity().finishAffinity();
    }



}