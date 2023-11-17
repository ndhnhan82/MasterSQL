package adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mastersql.R;

import java.util.ArrayList;

import fragments.UserManagement;
import model.User;

public class UserAdapter extends ArrayAdapter<User> {
    UserManagement context;
    int idLayout;
    ArrayList<User> arrayListUser;

    public UserAdapter(@NonNull UserManagement context, int idLayout, ArrayList<User> arrayListUser) {
        super( context.getContext(), idLayout, arrayListUser );
        this.context = context;
        this.idLayout = idLayout;
        this.arrayListUser = arrayListUser;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        convertView = layoutInflater.inflate( idLayout, parent );
        User user = arrayListUser.get( position );
        TextView tvUserName = (TextView) convertView.findViewById( R.id.tvName);
        tvUserName.setText( user.getFullName() );
        return convertView;
    }
}
