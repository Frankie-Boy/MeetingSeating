package com.novoda.frankboylan.meetingseating.heatmap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.novoda.frankboylan.meetingseating.R;

public class HeatmapRoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heatmap_room);

        Toolbar toolbarHeatmap = findViewById(R.id.toolbar_heatmap);
        toolbarHeatmap.setTitle(R.string.toolbar_heatmap_title);

        setSupportActionBar(toolbarHeatmap);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.heatmap_toolbar_content, menu);
        MenuItem heatmapItem = menu.findItem(R.id.action_heatmap);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_heatmap:
                // ToDo: Toggle Heatmap Colours & update listView
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
