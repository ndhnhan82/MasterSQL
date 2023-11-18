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
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import fragments.AdminDashboard;
import fragments.Home;
import fragments.Profile;
import model.User;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
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
    private DatabaseReference curUser;
    private String safeEmail;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        initialize();
    }

    private void initialize() {
        if (FirebaseApp.getApps(this).isEmpty()) {
            // Firebase is not initialized, so initialize it
            FirebaseApp.initializeApp(this);
        }

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
                replaceFragment( new Profile(new User(loggedInUser.getEmailAddress().toString(), "normalUser")) );
                mCurrentFragment = FRAGMENT_PROFILE;
            }
        } else if (id == R.id.nav_users) {
            if (mCurrentFragment != FRAGMENT_ADMIN) {
                Bundle bundle = new Bundle();
                AdminDashboard adminDashboard = new AdminDashboard();
                adminDashboard.setArguments( bundle );
                replaceFragment( adminDashboard );
                mCurrentFragment = FRAGMENT_ADMIN;
            }
        } else if (id == R.id.nav_signout) {
            FirebaseAuth.getInstance().signOut();
            loggedInUser = new User( "user@gmail.com" );
            this.finish();
            Intent intent = new Intent( this, SplashActivity.class );
            startActivity( intent );
        }else if (id == R.id.nav_quit){
            this.finish();
            getApplication().notifyAll();
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
}