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

public class UserManagement extends Fragment implements AdapterView.OnItemClickListener {

    private ListView lvUser;
    private ArrayAdapter userAdapter;
    private View view;
    private String selectedFromList;
    private Fragment fragment;

    public UserManagement() {
    }

    public static UserManagement newInstance(String param1, String param2) {
        UserManagement fragment = new UserManagement();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate( R.layout.fragment_user, container, false );

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