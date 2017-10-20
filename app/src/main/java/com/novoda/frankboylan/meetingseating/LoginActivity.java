package com.novoda.frankboylan.meetingseating;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
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

        tvEmail = findViewById(R.id.tv_login_email);
        tvPassword = findViewById(R.id.tv_login_password);
        if (getIntent().getExtras() != null) {
            tvEmail.setText(getIntent().getExtras().getString("email"));
        }

        auth = FirebaseAuth.getInstance();
        auth.signOut();

        checkNetworkState();
    }

    private void checkNetworkState() {
        if (isNetworkAvailable()) {
            // ToDo: Enable buttons

            Snackbar.make(findViewById(R.id.cl_login_activity), "Connected", Snackbar.LENGTH_SHORT).show();
        } else {
            // ToDo: Disable Buttons

            Snackbar.make(findViewById(R.id.cl_login_activity), "No Internet Connection", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Offline Mode", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            handlerOfflineLogin(v);
                        }
                    })
                    .show();

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (isNetworkAvailable()) {
                        checkNetworkState();
                    } else {
                        handler.postDelayed(this, 5000);
                    }
                }
            }, 5000);

        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
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
