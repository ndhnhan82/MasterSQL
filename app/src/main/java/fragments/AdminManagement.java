package fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mastersql.R;

import java.util.ArrayList;

import adapter.UserAdapter;
import model.User;
import model.UserManagement;

public class AdminManagement extends Fragment {

    private ListView lvUsers;
    private View view;
    private String selectedFromList;
    private Fragment fragment;

    private UserAdapter userAdapter;

    public AdminManagement() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate( R.layout.fragment_admin_management, container, false );

        lvUsers = (ListView) view.findViewById( R.id.lvUser );

        UserManagement.getUserList( new UserManagement.UserListCallback() {
            @Override
            public void onUserListUpdated(ArrayList<User> userList) {

                userAdapter = new UserAdapter( view.getContext(), userList );
                lvUsers.setAdapter( userAdapter );
                lvUsers.setOnItemClickListener( new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        User selectedUser = (User) lvUsers.getItemAtPosition(position);
                        launchProfileActivity(selectedUser);
                    }
                } );
            }

            @Override
            public void onFailure(Exception e) {

            }
        } );


        // Inflate the layout for this fragment
        return view;
    }

    private void launchProfileActivity(User selectedUser) {
        fragment = new Profile( new User( selectedUser.getEmailAddress(), "Admin" ) );
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace( R.id.content_frame, fragment ).addToBackStack( null ).commit();
    }
}