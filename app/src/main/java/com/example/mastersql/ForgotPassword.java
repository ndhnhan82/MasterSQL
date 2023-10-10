package com.example.mastersql;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener {

    EditText edEmail;
    Button btnSubmit, btnBackLogin;

    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initialize();
    }

    private void initialize()
    {
        edEmail = findViewById(R.id.editTxtEmail);
        btnSubmit = findViewById(R.id.btnSubmitEmail);
        btnBackLogin = findViewById(R.id.btnBackToLogin);

        //Make a link between the buttons and the click
        //Enable user click for the push buttons

        btnSubmit.setOnClickListener(this);
        btnBackLogin.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        int id = view.getId();

        if(id == R.id.btnSubmitEmail)
        {
            showAlert();
        }
        else if(id == R.id.btnBackToLogin)
        {
            goToLogin();
        }
    }

    private void showAlert() {

        builder = new AlertDialog.Builder(this);

        builder.setTitle("Notification")
                .setMessage("An email has been sent")
                .setCancelable(true)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        goToLogin();
                    }
                })
                .show();

    }

    private void goToLogin() {

        Intent intent = new Intent(this, LoginActivity.class);

        startActivity(intent);
    }
}