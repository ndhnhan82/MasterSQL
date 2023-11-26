package fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mastersql.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import adapter.ProgressAdapter;
import model.CategoryProgress;

public class UserDashboard extends Fragment {

    private View mRootView;
    private ListView lvCourseProgress;

    private int currentProgress;

    private ProgressBar pbCoursesDashboard;



    //For Realtime database
    DatabaseReference courseDatabase;

    //For Firebase Storage
    FirebaseStorage storage;

    StorageReference storageReference, sRef;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate( R.layout.fragment_user_dashboard, container, false );
        initialize();
        return mRootView;
    }

    private void initialize() {


        String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail().replace("@", "-")
                .replace(".", "-");


        lvCourseProgress = mRootView.findViewById(R.id.lvCourseProgress);
        ArrayList<CategoryProgress> list = new ArrayList<>();
        ProgressAdapter adapter = new ProgressAdapter(getContext(), list);
        lvCourseProgress.setAdapter(adapter);



        courseDatabase = FirebaseDatabase.getInstance().getReference()
                .child("Users").child(userEmail).child("PROGRESS");



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

        AlertDialog.Builder builder = new AlertDialog.Builder( getContext() );

        builder.setTitle( "Notification" )
                .setMessage( message )
                .setPositiveButton( "OK", (dialogInterface, i) -> {
                } )
                .show();

    }



    private void launchFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace( R.id.content_frame, fragment )
                .addToBackStack( null )
                .commit();
    }

}