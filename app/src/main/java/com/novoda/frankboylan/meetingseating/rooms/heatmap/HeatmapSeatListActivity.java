package com.novoda.frankboylan.meetingseating.rooms.heatmap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.novoda.frankboylan.meetingseating.R;

public class HeatmapSeatListActivity extends AppCompatActivity implements HeatmapSeatListDisplayer {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heatmap_room);

        Toolbar toolbarHeatmap = findViewById(R.id.toolbar_heatmap);
        toolbarHeatmap.setTitle(R.string.toolbar_heatmap_title);

        setSupportActionBar(toolbarHeatmap);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView seatList = findViewById(R.id.lv_seat_heatmap);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.heatmap_toolbar_content, menu);
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

    @Override
    public void makeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
