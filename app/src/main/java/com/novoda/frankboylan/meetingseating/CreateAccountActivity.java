package com.novoda.frankboylan.meetingseating;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CreateAccountActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    EditText newEmail, newPassword, newFirstname, newLastname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();

        newEmail = findViewById(R.id.et_new_email);
        newPassword = findViewById(R.id.et_new_password);
        newFirstname = findViewById(R.id.et_new_firstname);
        newLastname = findViewById(R.id.et_new_lastname);

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();
    }

    public void createAccountSubmit() {
        if (!formsAreValid()) {
            return;
        }
        auth.createUserWithEmailAndPassword(newEmail.getText().toString(), newPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Account Creation was a success (ToDo: maybe pass the email into the LoginActivity)
                        } else {
                            // Account Creation failed!
                            Toast.makeText(CreateAccountActivity.this, "Authentication failure! " + task.getException(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public boolean formsAreValid() {
        String email = newEmail.getText().toString();
        String password = newPassword.getText().toString();
        String firstname = newFirstname.getText().toString();
        String lastname = newLastname.getText().toString();

        if (email.length() < 9) {
            makeToast("Email must be longer than 9 characters!");
            return false;
        }
        if (!email.endsWith("@novoda.com") || !email.endsWith("@novoda.co.uk")) {
            makeToast("Email must be a Novoda email!");
            return false;
        }

        if (password.length() < 5) {
            makeToast("Password must be longer than 5 characters!");
            return false;
        }

        if (firstname.length() < 2) {
            makeToast("Please enter a longer Firstname!");
            return false;
        }
        if (!firstname.matches("[a-zA-Z]+")) {
            makeToast("Ensure there is only letters in your Firstname!");
            return false;
        }

        if (lastname.length() < 2) {
            makeToast("Please enter a longer Surname!");
            return false;
        }
        if (!lastname.matches("[a-zA-Z]+")) {
            makeToast("Ensure there is only letters in your Surname!");
            return false;
        }

        return true;
    }

    private void makeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
