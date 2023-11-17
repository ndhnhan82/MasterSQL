package fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.mastersql.R;

public class AdminDashboard extends Fragment implements View.OnClickListener {


    private View mRootView, subCourseView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
    }

    //private FragmentHomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate( R.layout.fragment_dashboard, container, false );
        return mRootView;
    }

    @Override
    public void onClick(View v) {

    }
}