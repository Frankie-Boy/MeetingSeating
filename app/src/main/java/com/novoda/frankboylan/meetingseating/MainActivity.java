package com.novoda.frankboylan.meetingseating;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private String[] mDrawerOptions;

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

        // ToDo: Check if online, if so, parse JSON and recreate SQLite DB.
        // ToDo: Display the last time the information was updated (use lastUpdateTimestamp)
    }
}
