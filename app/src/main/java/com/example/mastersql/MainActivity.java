package com.example.mastersql;

import static fragments.Login.user;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import fragments.Home;
import fragments.Profile;
import model.User;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_PROFILE = 1;
    private int mCurrentFragment = FRAGMENT_HOME;

    private TextView tvUserName, tvEmailAddress;
    private ImageView imgAvatar;
    private int SELECT_PICTURE = 200;

    private StorageReference storageReference;
    private FirebaseStorage firebaseStorage;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference curUser;
    private String safeEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        initialize();
    }

    private void initialize() {

        drawerLayout = (DrawerLayout) findViewById( R.id.drawer_layout );
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle( this, drawerLayout, toolbar,
                R.string.nav_drawer_open, R.string.nav_drawer_close );
        drawerLayout.addDrawerListener( toggle );
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById( R.id.navigation_view );
        navigationView.setNavigationItemSelectedListener( this );
        replaceFragment( new Home() );
        navigationView.getMenu().findItem( R.id.nav_home ).setChecked( true );
        View navHeaderView = navigationView.getHeaderView( 0 );
        tvEmailAddress = navHeaderView.findViewById( R.id.tvEmailAddress );
        tvEmailAddress.setText( user.getEmailAddress() );
        tvUserName = (TextView) navHeaderView.findViewById( R.id.tvUserNameLabel );
        if (user.getFullName() != null)
            tvUserName.setText( user.getFullName() );
        imgAvatar = (ImageView) navHeaderView.findViewById( R.id.imgUser );
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        firebaseDatabase = FirebaseDatabase.getInstance();

        safeEmail = user.getEmailAddress()
                .replace( "@", "-" )
                .replace( ".", "-" );
        curUser = firebaseDatabase.getReference( "Users/"+safeEmail );
        refreshProfilePicture();

    }

    private void refreshProfilePicture() {

        String pathString = "Images/" + safeEmail + ".jpg";

        // Create a reference with an initial file path and name
        storageReference.child( pathString ).getDownloadUrl().addOnSuccessListener( new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Glide.with(getBaseContext()).load( uri ).into( imgAvatar );
            }
        } );

        curUser.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tvUserName.setText( snapshot.child( "fullName" ).getValue().toString() );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        } );
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.nav_home) {
            if (mCurrentFragment != FRAGMENT_HOME) {
                replaceFragment( new Home() );
                mCurrentFragment = FRAGMENT_HOME;
            }
        } else if (id == R.id.nav_my_profile) {
            if (mCurrentFragment != FRAGMENT_PROFILE) {
                replaceFragment( new Profile() );
                mCurrentFragment = FRAGMENT_PROFILE;
                drawerLayout.closeDrawers();
            }

        } else if (id == R.id.nav_signout) {
            FirebaseAuth.getInstance().signOut();
            user = new User( "test@gmail.com" );
            this.finish();
            Intent intent = new Intent( this, SplashActivity.class );
            startActivity( intent );
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen( GravityCompat.START ))
            drawerLayout.closeDrawer( GravityCompat.START );
        else
            super.onBackPressed();
    }


    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace( R.id.content_frame, fragment );
        transaction.commit();
    }


}