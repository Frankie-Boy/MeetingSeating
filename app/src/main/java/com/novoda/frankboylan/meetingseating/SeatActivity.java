package com.novoda.frankboylan.meetingseating;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import static android.R.attr.id;

public class SeatActivity extends AppCompatActivity {
    private Seat seat;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat);

        Toolbar toolbarSeat = (Toolbar)findViewById(R.id.toolbar_seat);
        setSupportActionBar(toolbarSeat);

        // ToDo: read data from SQLite (Cursor) internal db to List (ListAdapter).
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.seat_toolbar_content, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_filter:
                // Show the filter view
                break;
            case R.id.action_refresh:
                // Run SQLite query to fetch result (based on selected filters)
                break;
            default:
                // How did users get here? :thinking:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}