package com.example.mastersql.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.mastersql.LoginActivity;
import com.example.mastersql.MainActivity;
import com.example.mastersql.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends Fragment implements View.OnClickListener {
    private TextInputEditText tieEmailAddress, tiePassword;
    TextView tvForgetPassword;
    private Button btnLogin;
    private View mRootView;
    private AlertDialog.Builder builder;
    private FirebaseAuth mAuth;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate( R.layout.fragment_login, container, false );
        initialize();
        return mRootView;
    }

    private void initialize() {
        tieEmailAddress = (TextInputEditText) mRootView.findViewById( R.id.tieEmailAddress );
        tiePassword = (TextInputEditText) mRootView.findViewById( R.id.tiePassword );
        btnLogin = (Button) mRootView.findViewById( R.id.btnLogin );
        tvForgetPassword = (TextView) mRootView.findViewById( R.id.tvForgetPass );
        btnLogin.setOnClickListener( this );
        tvForgetPassword.setOnClickListener( this );
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if (currentUser != null) {
//            gotoMainActivity();
//        }
//    }

    @Override
    public void onClick(View view) {
// Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        String strEmail = tieEmailAddress.getText().toString().trim();
        String strPassword = tiePassword.getText().toString();
        int id = view.getId();
        if (id == R.id.tvForgetPass) {
            showAlert( "An email will be sent to you shortly! " );
            mAuth.sendPasswordResetEmail( strEmail );

        } else if (id == R.id.btnLogin) {

            mAuth.signInWithEmailAndPassword(strEmail, strPassword)
                    .addOnCompleteListener( getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                gotoMainActivity();

                              } else {
                                showAlert( "Your email or password is not correct. PLease try again or register a new user!" );
                               }
                        }
                    });
        }
    }

    private void gotoMainActivity() {
        Intent intent = new Intent( getContext(), MainActivity.class );
        startActivity( intent );
        getActivity().finishAffinity();
    }

    private void showAlert(String message) {

        builder = new AlertDialog.Builder( getContext() );

        builder.setTitle( "Notification" )
                .setMessage( message )
                .setCancelable( true )
                .setPositiveButton( "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent( getActivity(), LoginActivity.class );
                        startActivity( intent );
                    }
                } )
                .show();

    }

}
