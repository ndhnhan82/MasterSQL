package adapter;

import static model.UserManagement.refreshProfilePicture;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mastersql.R;

import java.util.ArrayList;

import model.User;

public class UserAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<User> usersList;
    private User user;
    public UserAdapter() {

    }
    public UserAdapter(Context context, ArrayList<User> usersList) {
        this.context = context;
        this.usersList = usersList;

    }
    @Override
    public int getCount() {
        // the number of items in your list view
        return usersList.size();
    }

    @Override
    public Object getItem(int i) {
        return usersList.get( i );
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        //1- perform the required declaration
        View oneItem;
        ImageView imPhoto, imDelete;
        TextView tvFullName, tvEmail;
        //2- convert the layout one_item.xml to the corresponding java view objects
        //in order to manipulate different widgets -This conversion is called  Layout inflation
        LayoutInflater layoutInf = LayoutInflater.from( context );
        // change xml to java objects
        oneItem = layoutInf.inflate( R.layout.one_user_item, viewGroup, false );

        //3- to reference the widgets using findViewById inside to one_item.xml
        tvFullName = oneItem.findViewById( R.id.tvUserNameItem );
        tvEmail = oneItem.findViewById( R.id.tvEmailAddressItem );
        imPhoto = oneItem.findViewById( R.id.imPhoto );
        imDelete = oneItem.findViewById( R.id.imDelete );

        //4- to populate the widgets
        user = (User) getItem( position );
        refreshProfilePicture( context,user,imPhoto);
        tvFullName.setText( user.getFullName() );
        tvEmail.setText( user.getEmailAddress() );

//        String photoName = user.getPhoto();

        imDelete.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                user = (User) getItem( position );
            }
        } );
        //5- to return the data
        return oneItem;
    }
}