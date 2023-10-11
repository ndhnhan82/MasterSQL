package com.example.mastersql.fragments;

import static com.example.mastersql.fragments.Login.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mastersql.R;

public class Profile extends Fragment implements View.OnClickListener {
    private View mRootView;
    TextView tvEmail;
    EditText edName, edAge, edCountry;
    Spinner spLanguage;
    ToggleButton btnEditSave;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate( R.layout.fragment_profile, container, false );
        initialize();
        return mRootView;
    }

    private void initialize() {

        tvEmail = (TextView) mRootView.findViewById(R.id.tvEmail);
        edName = (EditText) mRootView.findViewById(R.id.edUserName );
        edAge = (EditText) mRootView.findViewById(R.id.edAge);
        edCountry = (EditText) mRootView.findViewById(R.id.edCountry);
        spLanguage = (Spinner) mRootView.findViewById(R.id.spLanguage);
        tvEmail.setText( user.getEmailAddress());
        btnEditSave = (ToggleButton) mRootView.findViewById(R.id.btnToggleEditSave);
        btnEditSave.setChecked( false );
        btnEditSave.setOnClickListener( this );
    }


    @Override
    public void onClick(View view) {
        if (!btnEditSave.isChecked()){
            edAge.setEnabled( false );
            edName.setEnabled( false );
            edCountry.setEnabled( false );
            spLanguage.setEnabled( false );
        }else{
            edAge.setEnabled( true );
            edName.setEnabled( true );
            edCountry.setEnabled( true );
            spLanguage.setEnabled( true );
        }
    }
}