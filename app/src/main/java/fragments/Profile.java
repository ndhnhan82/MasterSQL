package fragments;

import static android.app.Activity.RESULT_OK;
import static model.DatabaseManagement.refreshProfilePicture;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mastersql.MainActivity;
import com.example.mastersql.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import model.User;

public class Profile extends Fragment implements View.OnClickListener {
    private View mRootView;
    private TextView tvEmail;
    private EditText edName, edAge, edCountry;
    private Spinner spLanguage;
    private ToggleButton btnEditSave;
    private Button btnReturn;
    private ImageView imgProfile;
    private int SELECT_PICTURE = 200;
    private StorageReference storageReference, mountainsRef;
    private DatabaseReference userRef;
    private Uri selectedImageUri;
    private ArrayAdapter<CharSequence> adapter;

    private User currUser;

    public Profile() {
    }

    ;

    public Profile(User currUser) {
        this.currUser = currUser;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate( R.layout.fragment_profile, container, false );
        initialize();
        return mRootView;
    }

    private void initialize() {


        String safeEmail = currUser.getEmailAddress()
                .replace( "@", "-" )
                .replace( ".", "-" );
        tvEmail = (TextView) mRootView.findViewById( R.id.tvEmail );
        edName = (EditText) mRootView.findViewById( R.id.edUserName );
        edAge = (EditText) mRootView.findViewById( R.id.edAge );
        edCountry = (EditText) mRootView.findViewById( R.id.edCountry );
        spLanguage = (Spinner) mRootView.findViewById( R.id.spLanguage );

        iniSpLanguage();

        tvEmail.setText( currUser.getEmailAddress() );

        btnEditSave = (ToggleButton) mRootView.findViewById( R.id.btnToggleEditSave );
        btnReturn = (Button) mRootView.findViewById( R.id.btnReturn );
        btnReturn.setOnClickListener( this );
        btnEditSave.setChecked( false );
        btnEditSave.setOnClickListener( this );
        imgProfile = (ImageView) mRootView.findViewById( R.id.ivProfile );
        storageReference = FirebaseStorage.getInstance().getReference();
        userRef = FirebaseDatabase.getInstance().getReference( "Users/" + safeEmail );
        setEditDisable();
        refreshProfilePicture( getContext(), safeEmail, imgProfile );
        fletchData();
        imgProfile.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
            }
        } );
    }

    private void fletchData() {
        userRef.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currUser = (User) snapshot.getValue( User.class );
                edName.setText( currUser.getFullName() );
                edAge.setText( String.valueOf( currUser.getAge() ) );
                edCountry.setText( currUser.getCountry() );
                int selection = (currUser.getLanguagePrefer().equals( "English" )) ? 0 : 1;
                spLanguage.setSelection( selection );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        } );
    }

    private void iniSpLanguage() {
        adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.languages_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spLanguage.setAdapter( adapter );
    }


    private void setEditEnable() {
        edAge.setEnabled( true );
        edName.setEnabled( true );
        edCountry.setEnabled( true );
        spLanguage.setEnabled( true );
    }

    private void setEditDisable() {
        edAge.setEnabled( false );
        edName.setEnabled( false );
        edCountry.setEnabled( false );
        spLanguage.setEnabled( false );
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnReturn)
            gotoMainActivity();
        else if (btnEditSave.isChecked()) {
            setEditEnable();
        } else if (!btnEditSave.isChecked()) {
            setEditDisable();
            String safeEmail = currUser.getEmailAddress().
                    replace( "@", "-" ).
                    replace( ".", "-" );

            currUser.setFullName( edName.getText().toString() );
            currUser.setAge( Integer.valueOf( edAge.getText().toString() ) );
            currUser.setCountry( edCountry.getText().toString() );
            if (spLanguage.getSelectedItemId() == 0)
                currUser.setLanguagePrefer( "English" );
            else
                currUser.setLanguagePrefer( "French" );

            DatabaseReference usersDatabase = FirebaseDatabase.getInstance().getReference( "Users" );
            usersDatabase.child( safeEmail ).setValue( currUser );

        }
    }

    private void gotoMainActivity() {
        getActivity().finishAffinity();
        Intent intent = new Intent( getContext(), MainActivity.class );
        startActivity( intent );
    }

    private void imageChooser() {
        Intent i = new Intent();
        i.setType( "image/*" );
        i.setAction( Intent.ACTION_GET_CONTENT );
        startActivityForResult( Intent.createChooser( i, "Select Picture" ), SELECT_PICTURE );
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );

        if (resultCode == RESULT_OK && requestCode == SELECT_PICTURE && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            imgProfile.setImageURI( selectedImageUri );
            uploadPicture();
        }
    }

    private void uploadPicture() {

        ProgressDialog pd = null;
        try {
            // Existing code for image upload

            Log.d( "UPLOAD", "Starting image upload" );
            pd = new ProgressDialog( getContext() );
            pd.setTitle( "Uploading image..." );
            pd.show();

            String safeEmail = currUser.getEmailAddress();
            safeEmail = safeEmail.replace( "@", "-" );
            safeEmail = safeEmail.replace( ".", "-" );

            StorageReference parentRef = storageReference.child( "Images" );
            StorageReference childRef = parentRef.child( safeEmail + ".jpg" );

            ProgressDialog finalPd = pd;
            ProgressDialog finalPd1 = pd;
            ProgressDialog finalPd2 = pd;
            childRef.putFile( selectedImageUri )
                    .addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            finalPd.dismiss();
//                            Snackbar.make( mRootView.findViewById( android.R.id.content ), "Image uploaded!", Snackbar.LENGTH_LONG ).show();
                            Log.d( "UPLOAD", "Image uploaded successfully" );
                        }
                    } )
                    .addOnFailureListener( new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            finalPd1.dismiss();
                            Toast.makeText( getActivity().getApplicationContext(), "Failed to upload!", Toast.LENGTH_SHORT ).show();
                            Log.e( "UPLOAD", "Failed to upload image", e );
                        }
                    } )
                    .addOnProgressListener( new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            double progressPercent = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                            finalPd2.setMessage( "Percentage: " + (int) progressPercent + "%" );
                            Log.d( "UPLOAD", "Upload progress: " + (int) progressPercent + "%" );
                        }
                    } );

        } catch (Exception e) {
            pd.dismiss();
            Log.e( "UPLOAD", "Exception during image upload", e );
            e.printStackTrace();
        }


    }
}