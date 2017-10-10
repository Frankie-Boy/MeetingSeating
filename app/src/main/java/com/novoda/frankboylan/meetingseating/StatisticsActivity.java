package com.novoda.frankboylan.meetingseating;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class StatisticsActivity extends AppCompatActivity {
    private static final String TAG = "StatisticsActivity";
    private Toolbar toolbarStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        ListView mDrawerList = findViewById(R.id.side_drawer);

        String[] mDrawerOptions = getResources().getStringArray(R.array.drawer_options);
        mDrawerList.setAdapter(new ArrayAdapter<>(this,
                R.layout.drawer_list_item, mDrawerOptions));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        toolbarStats = findViewById(R.id.toolbar_stats);
        toolbarStats.setTitle(R.string.toolbar_statistics_title);
        setSupportActionBar(toolbarStats);

        updateUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.stats_toolbar_content, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_refresh:
                updateUI();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    /**
     * Fetches Json data, refreshes ListViews with SQLite data
     */
    private void updateUI() {
        // Fetches Json RoomSeatData then Updates the SQLite DB
        JSONParser jsonParser = new JSONParser(this);
        jsonParser.execute();
    }
}
