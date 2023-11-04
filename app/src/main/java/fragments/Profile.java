package fragments;

import static android.app.Activity.RESULT_OK;
import static fragments.Login.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.mastersql.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
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
    private ImageView imgProfile;
    private int SELECT_PICTURE = 200;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference curUser;
    private Uri selectedImageUri;
    private ArrayAdapter<CharSequence> adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate( R.layout.fragment_profile, container, false );
        initialize();
        return mRootView;
    }

    private void initialize() {

        String safeEmail = user.getEmailAddress()
                .replace( "@", "-" )
                .replace( ".", "-" );

        tvEmail = (TextView) mRootView.findViewById( R.id.tvEmail );
        edName = (EditText) mRootView.findViewById( R.id.edUserName );
        edAge = (EditText) mRootView.findViewById( R.id.edAge );
        edCountry = (EditText) mRootView.findViewById( R.id.edCountry );
        spLanguage = (Spinner) mRootView.findViewById( R.id.spLanguage );

        iniSpLanguage();

        tvEmail.setText( user.getEmailAddress() );

        btnEditSave = (ToggleButton) mRootView.findViewById( R.id.btnToggleEditSave );
        btnEditSave.setChecked( false );
        btnEditSave.setOnClickListener( this );
        imgProfile = (ImageView) mRootView.findViewById( R.id.ivProfile );
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        firebaseDatabase = FirebaseDatabase.getInstance();
        curUser = firebaseDatabase.getReference( "Users/" + safeEmail );
        setEditDisable();
        refreshProfilePicture();
        fletchData();
        imgProfile.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
            }
        } );
    }



    private void fletchData() {
        curUser.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child( "fullName" ).getValue() != null)
                    edName.setText( snapshot.child( "fullName" ).getValue().toString() );
                edAge.setText( snapshot.child( "age" ).getValue().toString() );
                if (snapshot.child( "country" ).getValue() != null)
                    edCountry.setText( snapshot.child( "country" ).getValue().toString() );
                int selection = (snapshot.child( "languagePrefer" )
                        .getValue().equals( "English" )) ? 0 : 1;
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
// Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
// Apply the adapter to the spinner.
        spLanguage.setAdapter( adapter );
    }

    private void refreshProfilePicture() {
        String safeEmail = user.getEmailAddress();
        safeEmail = safeEmail.replace( "@", "-" );
        safeEmail = safeEmail.replace( ".", "-" );
        String pathString = "Images/" + safeEmail + ".jpg";
        // Create a reference with an initial file path and name
        storageReference.child( pathString ).getDownloadUrl().addOnSuccessListener( new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with( getContext() ).load( uri ).into( imgProfile );

            }
        } ).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                Toast.makeText( getContext(), "Cannot loading image!", Toast.LENGTH_SHORT ).show();
            }
        } );
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
        if (btnEditSave.isChecked()) {
            setEditEnable();
        } else {
            setEditDisable();
            String safeEmail = user.getEmailAddress().
                    replace( "@", "-" ).
                    replace( ".", "-" );

            user.setFullName( edName.getText().toString() );
            user.setAge( Integer.valueOf( edAge.getText().toString() ) );
            user.setCountry( edCountry.getText().toString() );
            if (spLanguage.getSelectedItemId() == 0)
                user.setLanguagePrefer( User.languages.English );
            else
                user.setLanguagePrefer( User.languages.French );

            DatabaseReference usersDatabase = FirebaseDatabase.getInstance().getReference( "Users" );
            usersDatabase.child( safeEmail ).setValue( user );


        }
    }

    private void imageChooser() {
        Intent i = new Intent();
        i.setType( "image/*" );
        i.setAction( Intent.ACTION_GET_CONTENT );

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult( Intent.createChooser( i, "Select Picture" ), SELECT_PICTURE );
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );

        if (resultCode == RESULT_OK && requestCode == SELECT_PICTURE && data != null && data.getData() != null) {

            // Get the url of the image from data
            selectedImageUri = data.getData();
            // update the preview image in the layout
            imgProfile.setImageURI( selectedImageUri );
            uploadPicture();
        }
    }

    private void uploadPicture() {
        final ProgressDialog pd = new ProgressDialog( getContext() );
        pd.setTitle( "Uploading image..." );
        pd.show();

        String safeEmail = user.getEmailAddress();
        safeEmail = safeEmail.replace( "@", "-" );
        safeEmail = safeEmail.replace( ".", "-" );


        StorageReference mountainsRef = storageReference.child( "Images/" + safeEmail + ".jpg" );
        mountainsRef.putFile( selectedImageUri ).addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        Snackbar.make( mRootView.findViewById( android.R.id.content ), "image uploaded!", Snackbar.LENGTH_LONG ).show();
                    }
                } )
                .addOnFailureListener( new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText( getActivity().getApplicationContext(), "Failed to upload!", Toast.LENGTH_SHORT ).show();
                    }
                } ).addOnProgressListener( new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progressPercent = 0.00 + snapshot.getBytesTransferred() / snapshot.getTotalByteCount();
                        pd.setMessage( "Percentage: " + (int) progressPercent + "%" );
                    }
                } );

    }
}