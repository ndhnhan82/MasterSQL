package com.example.mastersql;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mastersql.fragments.Login;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_PROFILE = 1;
    private int mCurrentFragment = FRAGMENT_HOME;
    private FirebaseAuth mAuth;


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
        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle( this, drawerLayout, toolbar,
                R.string.nav_drawer_open, R.string.nav_drawer_close );
        drawerLayout.addDrawerListener( toogle );
        toogle.syncState();
        NavigationView navigationView = (NavigationView) findViewById( R.id.navigation_view );
        navigationView.setNavigationItemSelectedListener( this );
        replaceFragment( new Login() );
        navigationView.getMenu().findItem( R.id.nav_home ).setChecked( true );
    }

    @Override
    public void onClick(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent( this, LoginActivity.class );
        startActivity( intent );
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.nav_home) {

            if (mCurrentFragment != FRAGMENT_HOME) {
                replaceFragment( new Fragment() );
                mCurrentFragment = FRAGMENT_HOME;
            }

        } else if (id == R.id.nav_signout) {
            FirebaseAuth.getInstance().signOut();
            this.finish();
            Intent intent = new Intent(this, SplashActivity.class);
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