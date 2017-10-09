package com.novoda.frankboylan.meetingseating;

import android.content.Context;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private String[] mDrawerOptions;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerList = (ListView)findViewById(R.id.side_drawer);

        mDrawerOptions = getResources().getStringArray(R.array.drawer_options);
        mDrawerList.setAdapter(new ArrayAdapter<>(this,
                R.layout.drawer_list_item, mDrawerOptions));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        updateUI();
    }
    /**
     * Fetches Json data, refreshes ListViews with SQLite data
     */
    private void updateUI() {
        // Fetches Json Data and updates SQLite DB.
        JSONParser jsonParser = new JSONParser(this);
        jsonParser.execute();

        dbHelper = new DBHelper(this);

        TextView tvUpdatedTime = (TextView)findViewById(R.id.tv_last_timestamp);
        com.novoda.frankboylan.meetingseating.Timestamp metaTimestamp = dbHelper.getMetaTimestamp();
        Date lastUpdatedDate = new Date(metaTimestamp.getTimestamp() * 1000);
        tvUpdatedTime.setText("Last Updated: " + lastUpdatedDate);
    }
}
