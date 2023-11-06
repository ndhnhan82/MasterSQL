package com.example.mastersql;

import static fragments.Login.loggedInUser;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import fragments.Admin;
import fragments.Home;
import fragments.Profile;
import model.User;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ChildEventListener {
    private DrawerLayout drawerLayout;
    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_PROFILE = 1;
    private static final int FRAGMENT_ADMIN = 2;
    private int mCurrentFragment = FRAGMENT_HOME;

    private TextView tvUserName, tvEmailAddress;
    private ImageView imgAvatar;
    private int SELECT_PICTURE = 200;

    private StorageReference storageReference;
    private FirebaseStorage firebaseStorage;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference curUser, userRef;
    private String safeEmail;
    private NavigationView navigationView;

    private ArrayList<String> arrListUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        initialize();
    }

    private void initialize() {
        arrListUsers = new ArrayList<>();

        drawerLayout = (DrawerLayout) findViewById( R.id.drawer_layout );
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle( this, drawerLayout, toolbar,
                R.string.nav_drawer_open, R.string.nav_drawer_close );
        drawerLayout.addDrawerListener( toggle );
        toggle.syncState();
        navigationView = (NavigationView) findViewById( R.id.navigation_view );
        navigationView.setNavigationItemSelectedListener( this );
        replaceFragment( new Home() );
        navigationView.getMenu().findItem( R.id.nav_home ).setChecked( true );
        View navHeaderView = navigationView.getHeaderView( 0 );
        tvEmailAddress = navHeaderView.findViewById( R.id.tvEmailAddress );
        tvEmailAddress.setText( loggedInUser.getEmailAddress() );
        tvUserName = (TextView) navHeaderView.findViewById( R.id.tvUserNameLabel );
        if (loggedInUser.getFullName() != null)
            tvUserName.setText( loggedInUser.getFullName() );
        imgAvatar = (ImageView) navHeaderView.findViewById( R.id.imgUser );
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        firebaseDatabase = FirebaseDatabase.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference( "Users" );
        userRef.addChildEventListener( this );
        userRoleCheck();
        refreshProfilePicture();

    }

    private void userRoleCheck() {
        safeEmail = loggedInUser.getEmailAddress()
                .replace( "@", "-" )
                .replace( ".", "-" );
        curUser = firebaseDatabase.getReference( "Users/" + safeEmail );
        curUser.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child( "role" ).getValue().equals( "Admin" )) {
                    navigationView.getMenu().findItem( R.id.nav_users ).setVisible( true );

                } else
                    navigationView.getMenu().findItem( R.id.nav_users ).setVisible( false );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        } );
    }

    private void refreshProfilePicture() {

        String pathString = "Images/" + safeEmail + ".jpg";

        // Create a reference with an initial file path and name
        storageReference.child( pathString ).getDownloadUrl().addOnSuccessListener( new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Glide.with( getBaseContext() ).load( uri ).into( imgAvatar );
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.nav_home) {
            if (mCurrentFragment != FRAGMENT_HOME) {
                replaceFragment( new Home() );
                mCurrentFragment = FRAGMENT_HOME;

            }
        } else if (id == R.id.nav_my_profile) {
            if (mCurrentFragment != FRAGMENT_PROFILE) {
                replaceFragment( new Profile(new User(loggedInUser.getEmailAddress().toString(), User.roles.NormalUser)) );
                mCurrentFragment = FRAGMENT_PROFILE;
            }
        } else if (id == R.id.nav_users) {
            if (mCurrentFragment != FRAGMENT_ADMIN) {
                Bundle bundle = new Bundle();
                bundle.putStringArrayList( "USER_LIST", arrListUsers );
                Admin admin = new Admin();
                admin.setArguments( bundle );
                replaceFragment( admin );
                mCurrentFragment = FRAGMENT_ADMIN;
            }

        } else if (id == R.id.nav_signout) {
            FirebaseAuth.getInstance().signOut();
            loggedInUser = new User( "test@gmail.com" );
            this.finish();
            Intent intent = new Intent( this, SplashActivity.class );
            startActivity( intent );
        }
        drawerLayout.closeDrawers();


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


    @Override
    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
        String temp = "";
        if (snapshot.child( "fullName" ).getValue() != "") {
            temp = snapshot.child( "fullName" ).getValue().toString();
        } else
            temp = "NO NAME";

        temp += " - " + snapshot.child( "emailAddress" ).getValue().toString();
        arrListUsers.add( temp );
    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
}