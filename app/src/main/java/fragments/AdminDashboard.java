package fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mastersql.R;

public class AdminDashboard extends Fragment implements View.OnClickListener {

    private View mRootView;
    private ImageView imUsers, imQuizzes, imCourses, imProgress, imArchive;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate( R.layout.fragment_dashboard, container, false );
        initialize();
        return mRootView;
    }

    private void initialize() {
        imUsers = (ImageView) mRootView.findViewById( R.id.imUsers );
        imQuizzes = (ImageView) mRootView.findViewById( R.id.imQuizs );
        imCourses = (ImageView) mRootView.findViewById( R.id.imCourses );
        imProgress = (ImageView) mRootView.findViewById( R.id.imProgress );
        imArchive = (ImageView) mRootView.findViewById( R.id.imArchive );
        imUsers.setOnClickListener( this );
        imQuizzes.setOnClickListener( this );
        imCourses.setOnClickListener( this );
        imProgress.setOnClickListener( this );
        imArchive.setOnClickListener( this );

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.imUsers) {
            launchUserManagementFragment();
        }
    }

    private void launchUserManagementFragment() {
        AdminManagement adminManagementFragment = new AdminManagement();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace( R.id.content_frame, adminManagementFragment )
                .addToBackStack( null )
                .commit();
    }

}