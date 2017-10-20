package com.novoda.frankboylan.meetingseating;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private FirebaseAuth auth;
    TextView tvEmail, tvPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, StatisticsActivity.class));
            finish();
        }

        tvEmail = findViewById(R.id.tv_login_email);
        tvPassword = findViewById(R.id.tv_login_password);

    }

    /**
     * Button Handler method - Login
     */
    public void handlerLogin(View v) {
        String email = tvEmail.getText().toString();
        String pass = tvPassword.getText().toString();

        if (email.isEmpty() || pass.isEmpty()) {
            makeToast("Missing fields!");
            return;
        }

        auth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(LoginActivity.this, StatisticsActivity.class));
                            finish();
                        }
                        makeToast("Username & Password Combo not recognised!");
                    }
                });

    }

    private void makeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Button Handler method - Create Account
     */
    public void handlerCreateAccount(View v) {
        // ToDo: clear any EditText data.
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }

    /**
     * Button Handler method - Offline Mode
     */
    public void handlerOfflineLogin(View v) {
        Intent intent = new Intent(this, StatisticsActivity.class);
        startActivity(intent);
        finish();
    }

}
