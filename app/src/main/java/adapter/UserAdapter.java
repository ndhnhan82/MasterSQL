package adapter;

import static model.DatabaseManagement.refreshProfilePicture;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.mastersql.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import model.User;

public class UserAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<User> usersList;
    private User user;
    public static final int USER_MANAGEMENT = 1;
    public static final int USER_PROGRESS = 2;
    private int fragment;

    public UserAdapter() {
    }

    public UserAdapter(Context context, ArrayList<User> usersList, int fragment) {
        this.context = context;
        this.usersList = usersList;
        this.fragment = fragment;


    }

    @Override
    public int getCount() {
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
        View oneItem = null;
        ImageView imPhoto, imDelete;
        TextView tvFullName, tvEmail, tvProgress;
        //2- convert the layout one_item.xml to the corresponding java view objects
        //in order to manipulate different widgets -This conversion is called  Layout inflation
        LayoutInflater layoutInf = LayoutInflater.from( context );
        // change xml to java objects
        if (fragment == USER_MANAGEMENT) {
            oneItem = layoutInf.inflate( R.layout.one_user_item, viewGroup, false );

            //3- to reference the widgets using findViewById inside to one_item.xml
            tvFullName = oneItem.findViewById( R.id.tvUserNameItem );
            tvEmail = oneItem.findViewById( R.id.tvEmailAddressItem );
            imPhoto = oneItem.findViewById( R.id.imPhoto );
            imDelete = oneItem.findViewById( R.id.tvProgress );
            user = (User) getItem( position );
            View finalOneItem = oneItem;
            imDelete.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = v.getId();
                    if (id == R.id.tvProgress) {
                        String safeEmail = user.getEmailAddress()
                                .replace( "@", "-" )
                                .replace( ".", "-" );
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder( finalOneItem.getContext() );
                        alertDialog.setTitle( "Deletion" );
                        alertDialog.setMessage( "Do you want to delete this user?" );
                        alertDialog.setPositiveButton( "Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == Dialog.BUTTON_POSITIVE) {
                                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference( "Users" );
                                    userRef.child( safeEmail ).removeValue();
                                    usersList.remove( position );
                                    notifyDataSetChanged();
                                }
                            }
                        } );
                        alertDialog.setNegativeButton( "No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        } );
                        alertDialog.create().show();
                    }
                }
            } );
            //4- to populate the widgets
            refreshProfilePicture( context, user.getEmailAddress(), imPhoto );
            tvFullName.setText( user.getFullName() );
            tvEmail.setText( user.getEmailAddress() );
        } else if (fragment == USER_PROGRESS) {
            oneItem = layoutInf.inflate( R.layout.one_user_item_progress, viewGroup, false );

            //3- to reference the widgets using findViewById inside to one_item.xml
            tvFullName = oneItem.findViewById( R.id.tvUserNameItem );
            tvEmail = oneItem.findViewById( R.id.tvEmailAddressItem );
            imPhoto = oneItem.findViewById( R.id.imPhoto );
            tvProgress = oneItem.findViewById( R.id.tvProgress );

            user = (User) getItem( position );
            refreshProfilePicture( context, user.getEmailAddress(), imPhoto );
            tvFullName.setText( user.getFullName() );
            tvEmail.setText( user.getEmailAddress() );
            tvProgress.setText( "Progress: " + user.getProgress() +" %");
        }
        return oneItem;
    }
}