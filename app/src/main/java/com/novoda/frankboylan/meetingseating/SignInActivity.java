package com.novoda.frankboylan.meetingseating;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class SignInActivity extends AppCompatActivity {
    private static final String TAG = "SignInActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
    }

    /**
     * Button Handler method - Offline Mode
     */
    public void handlerOfflineLogin(View view) {
        Intent intent = new Intent(this, StatisticsActivity.class);
        startActivity(intent);
        finish();
    }

}
