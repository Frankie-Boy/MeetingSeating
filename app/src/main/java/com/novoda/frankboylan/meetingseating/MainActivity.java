package com.novoda.frankboylan.meetingseating;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView mDrawerList = findViewById(R.id.side_drawer);

        String[] mDrawerOptions = getResources().getStringArray(R.array.drawer_options);
        mDrawerList.setAdapter(new ArrayAdapter<>(this,
                R.layout.drawer_list_item, mDrawerOptions));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        updateUI();
    }

    /**
     * Fetches Json data, refreshes ListViews with SQLite data
     */
    private void updateUI() {
        // Fetches Json RoomSeatData and updates SQLite DB.
        JSONParser jsonParser = new JSONParser(this);
        jsonParser.execute();

        DBHelper dbHelper = new DBHelper(this);

        // ToDo: On first time run, the date is 1970 - because setText is called before a timestamp has been read.
        Timestamp metaTimestamp = dbHelper.getMetaTimestamp();
        Date lastUpdatedDate = new Date(metaTimestamp.getTimestamp() * 1000);
        Log.d(TAG, "Last Updated: " + lastUpdatedDate);
    }
}
