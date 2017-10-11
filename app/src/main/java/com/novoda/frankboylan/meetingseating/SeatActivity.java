package com.novoda.frankboylan.meetingseating;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.List;

public class SeatActivity extends AppCompatActivity {
    private static final String TAG = "SeatActivity";
    DBHelper dbHelper;
    private Seat seat;
    private Toolbar toolbarSeat;
    private DrawerLayout drawerLayout;
    ListView listViewSeats;
    RelativeLayout rlFilterView;
    MenuItem refreshItem, filterItem;
    boolean filterViewVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat);

        ListView drawerList = findViewById(R.id.side_drawer);
        drawerLayout = findViewById(R.id.drawer_layout);

        String[] mDrawerOptions = getResources().getStringArray(R.array.drawer_options);
        drawerList.setAdapter(new ArrayAdapter<>(this,
                R.layout.drawer_list_item, mDrawerOptions));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        toolbarSeat = findViewById(R.id.toolbar_seat);
        toolbarSeat.setTitle(R.string.toolbar_seat_title);
        toolbarSeat.setNavigationIcon(R.drawable.ic_action_burger);

        setSupportActionBar(toolbarSeat);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listViewSeats = findViewById(R.id.listview_all_seats);
        updateList();

        rlFilterView = findViewById(R.id.rl_filter);
        rlFilterView.setVisibility(View.GONE); // Hiding View

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
                filterViewVisible = true;
                cycleUI();
                break;
            case R.id.action_refresh:
                JSONParser jsonParser = new JSONParser(this);
                jsonParser.execute();
                updateList();
                break;
            case android.R.id.home:
                if(filterViewVisible) {
                    filterViewVisible = false;
                    cycleUI();
                    break;
                }
                drawerLayout.openDrawer(Gravity.LEFT);
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

    /**
     * Updates UI - View, Toolbar, Icons, Titles, & Nav Drawer
     */
    private void cycleUI() {
        if (filterViewVisible) { // Display Filter View
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            listViewSeats.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_off_left));
            listViewSeats.setVisibility(View.GONE);
            refreshItem.setVisible(false); // Removing Toolbar items
            filterItem.setVisible(false);
            rlFilterView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_on_left));
            rlFilterView.setVisibility(View.VISIBLE); // Displaying View
            toolbarSeat.setTitle(R.string.toolbar_seat_filter_title);
            toolbarSeat.setNavigationIcon(R.drawable.ic_action_arrow);
            return;
        }
        // Hide Filter View
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        rlFilterView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_off_right));
        rlFilterView.setVisibility(View.GONE); // Hiding View
        listViewSeats.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_on_right));
        listViewSeats.setVisibility(View.VISIBLE);
        toolbarSeat.setTitle(R.string.toolbar_seat_title);
        toolbarSeat.setNavigationIcon(R.drawable.ic_action_burger);
        refreshItem.setVisible(true); // Adding Toolbar items
        filterItem.setVisible(true);
    }
}