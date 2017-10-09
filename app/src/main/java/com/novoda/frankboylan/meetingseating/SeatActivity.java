package com.novoda.frankboylan.meetingseating;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class SeatActivity extends AppCompatActivity {
    private static final String TAG = "SeatActivity";
    DBHelper dbHelper;
    private Seat seat;
    private Toolbar toolbarSeat;
    ListView listViewSeats;
    ConstraintLayout clFilterView;
    MenuItem refreshItem, filterItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat);

        toolbarSeat = findViewById(R.id.toolbar_seat);
        toolbarSeat.setTitle("Seat List"); // ToDo: Reference strings.xml
        setSupportActionBar(toolbarSeat);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        clFilterView = findViewById(R.id.cl_filter);
        clFilterView.setVisibility(View.GONE);
        listViewSeats = findViewById(R.id.listview_all_seats);
        updateList();
    }

    @Override
    public void onStart() {
        super.onStart();
        updateList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.seat_toolbar_content, menu);
        refreshItem = menu.findItem(R.id.action_refresh);
        filterItem = menu.findItem(R.id.action_filter);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_filter:
                refreshItem.setVisible(false);
                filterItem.setVisible(false);
                clFilterView.setVisibility(View.VISIBLE);
                toolbarSeat.setTitle("Fitler Menu"); // ToDo: reference strings.xml
                break;
            case R.id.action_refresh:
                JSONParser jsonParser = new JSONParser(this);
                jsonParser.execute();
                updateList();
                break;
            case android.R.id.home:
                refreshItem.setVisible(true);
                filterItem.setVisible(true);
                clFilterView.setVisibility(View.GONE);
                toolbarSeat.setTitle("Seats List"); // ToDo: reference string.xml
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
    /**
     * Refreshes ListViews with SQLite data
     */
    private void updateList() {
        dbHelper = new DBHelper(this);
        List<Seat> seatList = dbHelper.getAllSeats();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, seatList);
        listViewSeats.setAdapter(adapter);
    }
}