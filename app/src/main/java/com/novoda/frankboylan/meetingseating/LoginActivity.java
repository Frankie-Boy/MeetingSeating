package com.novoda.frankboylan.meetingseating;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    /**
     * Button Handler method - Login
     */
    public void handlerLogin(View v) {
        // Authenticate with Firebase -> Feedback to user
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
