package com.novoda.frankboylan.meetingseating;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;

public class SeatActivity extends AppCompatActivity {
    private static final String TAG = "SeatActivity";
    DBHelper dbHelper;
    private Seat seat;
    private List<Seat> seatList = new ArrayList<Seat>();
    private Toolbar toolbar;
    ListView listViewSeats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat);

        Toolbar toolbarSeat = (Toolbar)findViewById(R.id.toolbar_seat);
        toolbarSeat.setTitle("Seat List"); // ToDo: Reference strings.xml
        setSupportActionBar(toolbarSeat);

        listViewSeats = (ListView)findViewById(R.id.listview_all_seats);

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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_filter:
                break;
            case R.id.action_refresh:
                JSONParser jsonParser = new JSONParser(this);
                jsonParser.execute();

                updateList();
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
        seatList = dbHelper.getAllSeats();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, seatList);
        listViewSeats.setAdapter(adapter);
    }
}