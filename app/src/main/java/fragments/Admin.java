package fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mastersql.R;

import java.util.ArrayList;

import model.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Admin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Admin extends Fragment implements AdapterView.OnItemClickListener {

    private ListView lvUser;
    private ArrayAdapter userAdapter;
    private View view;
    private String selectedFromList;
    private Fragment fragment;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Admin() {
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
    public static Admin newInstance(String param1, String param2) {
        Admin fragment = new Admin();
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate( R.layout.fragment_admin, container, false );

        lvUser = (ListView) view.findViewById( R.id.lvUser );
        ArrayList<String> userList = getArguments().getStringArrayList( "USER_LIST" );
        userAdapter = new ArrayAdapter( view.getContext(), R.layout.layout_item_user, userList );
        lvUser.setAdapter( userAdapter );
        lvUser.setOnItemClickListener( this );

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        selectedFromList = (String) (lvUser.getItemAtPosition( i ));
        selectedFromList = selectedFromList.split( "-" )[1].trim();


        fragment = new Profile( new User( selectedFromList, User.roles.Admin ) );
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace( R.id.content_frame, fragment ).addToBackStack( null ).commit();
    }


}