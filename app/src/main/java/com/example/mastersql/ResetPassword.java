package com.example.mastersql;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

public class ResetPassword extends AppCompatActivity implements View.OnClickListener {

    EditText edPassword, edConfirmPassword;

    Button btnResetPassword, btnGoToLogin;

    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        initialize();
    }

    private void initialize() {

        edPassword = findViewById(R.id.edTxtResetPassword);
        edConfirmPassword = findViewById(R.id.edTxtConfirmResetPassword);

        btnResetPassword = findViewById(R.id.btnResetPassword);
        btnGoToLogin = findViewById(R.id.btnBackToLogin);

        //Make a link between the buttons and the click
        //Enable user click for the push buttons

        btnResetPassword.setOnClickListener(this);
        btnGoToLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        if(id == R.id.btnResetPassword)
        {
            showAlert();
        }
        else if(id == R.id.btnBackToLogin)
        {
            goToLogin();
        }
    }

    private void goToLogin() {

        Intent intent = new Intent(this, LoginActivity.class);

        startActivity(intent);
    }

    private void showAlert() {

        builder = new AlertDialog.Builder(this);

        builder.setTitle("Notification")
                .setMessage("Your password has been successfully updated")
                .setCancelable(true)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        goToLogin();
                    }
                })
                .show();


    }
}