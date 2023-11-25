package com.example.mastersql;

import static model.DatabaseManagement.refreshProfilePicture;

import android.content.Intent;
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

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import fragments.AdminDashboard;
import fragments.Home;
import fragments.Profile;
import fragments.UserDashboard;
import model.User;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_PROFILE = 1;
    private static final int FRAGMENT_ADMIN = 2;
    private static final int FRAGMENT_USER_DASHBOARD = 3;
    private int mCurrentFragment = FRAGMENT_HOME;

    private TextView tvUserName, tvEmailAddress;
    private ImageView imgAvatar;
    private int SELECT_PICTURE = 200;

    private DatabaseReference userRef;
    private String safeEmail;
    private NavigationView navigationView;
    private User currUserLogged;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        initialize();
    }

    private void initialize() {
        if (FirebaseApp.getApps(this).isEmpty()) {
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
        navigationView.getMenu().findItem( R.id.nav_home ).setChecked( true );
        View navHeaderView = navigationView.getHeaderView( 0 );
        tvEmailAddress = navHeaderView.findViewById( R.id.tvEmailAddress );
        userIdentify( new UserCallback() {
            @Override
            public void onUserUpdated(User user) {
                tvEmailAddress.setText( currUserLogged.getEmailAddress() );
                tvUserName = (TextView) navHeaderView.findViewById( R.id.tvUserNameLabel );
                if (currUserLogged.getFullName() != null) {
                    tvUserName.setText( currUserLogged.getFullName() );
                }
                if (currUserLogged.getRole().equals( "Admin" )) {
                    navigationView.getMenu().findItem( R.id.nav_admin ).setVisible( true );
                    replaceFragment( new AdminDashboard() );
                } else{
                    replaceFragment( new Home() );
                    navigationView.getMenu().findItem( R.id.nav_admin ).setVisible( false );
                }
                imgAvatar = (ImageView) navHeaderView.findViewById( R.id.imgUser );
                refreshProfilePicture(getBaseContext(),currUserLogged.getEmailAddress(),imgAvatar);
            }

            @Override
            public void onFailure(Exception e) {

            }
        } );

    }

    private void userIdentify(final UserCallback callback) {
        safeEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail()
                .replace( "@", "-" )
                .replace( ".", "-" );
        userRef = FirebaseDatabase.getInstance().getReference( "Users/" + safeEmail );
        userRef.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currUserLogged = (User)snapshot.getValue( User.class);

                callback.onUserUpdated( currUserLogged );
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onFailure(error.toException());
            }
        } );
    }
    public interface UserCallback {
        void onUserUpdated(User user);

        void onFailure(Exception e);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.nav_home) {
            if (mCurrentFragment != FRAGMENT_HOME) {
                replaceFragment( new Home() );
                mCurrentFragment = FRAGMENT_HOME;
            }
        } else if (id == R.id.nav_users) {
            if (mCurrentFragment != FRAGMENT_USER_DASHBOARD) {
                replaceFragment( new UserDashboard());
                mCurrentFragment = FRAGMENT_USER_DASHBOARD;
            }

        }else if (id == R.id.nav_my_profile) {
            if (mCurrentFragment != FRAGMENT_PROFILE) {
                replaceFragment( new Profile(new User(currUserLogged.getEmailAddress().toString(), "normalUser")) );
                mCurrentFragment = FRAGMENT_PROFILE;
            }
        } else if (id == R.id.nav_admin) {
            if (mCurrentFragment != FRAGMENT_ADMIN) {
                AdminDashboard adminDashboard = new AdminDashboard();
                replaceFragment( adminDashboard );
                mCurrentFragment = FRAGMENT_ADMIN;
            }
        } else if (id == R.id.nav_signout) {
            FirebaseAuth.getInstance().signOut();
            this.finish();
            Intent intent = new Intent( this, LoginActivity.class );
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