package com.novoda.frankboylan.meetingseating;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SeatActivity extends AppCompatActivity {
    private static final String TAG = "SeatActivity";
    public static List<Seat> seatListFiltered;
    public static List<Room> roomList;
    private Toolbar toolbarSeat;
    private DrawerLayout drawerLayout;
    static ArrayAdapter<Seat> adapter;
    ListView listViewSeats;
    RelativeLayout rlFilterView;
    LinearLayout llRoomsExpandableContent;
    static LinearLayout llSeatsExpandableContent;
    MenuItem refreshItem, filterItem;
    List<Seat> seatList, seatListFull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat);

        ListView drawerList = findViewById(R.id.side_drawer);
        drawerLayout = findViewById(R.id.drawer_layout);

        String[] mDrawerOptions = getResources().getStringArray(R.array.drawer_options);
        drawerList.setAdapter(new ArrayAdapter<>(this,
                                                 R.layout.drawer_list_item, mDrawerOptions
        ));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        toolbarSeat = findViewById(R.id.toolbar_seat);
        toolbarSeat.setTitle(R.string.toolbar_seat_title);
        toolbarSeat.setNavigationIcon(R.drawable.ic_action_burger);

        setSupportActionBar(toolbarSeat);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listViewSeats = findViewById(R.id.listview_all_seats);

        rlFilterView = findViewById(R.id.rl_filter);
        rlFilterView.setVisibility(View.GONE); // Hiding View

        llRoomsExpandableContent = findViewById(R.id.ll_filter_expandable_rooms);
        llRoomsExpandableContent.setVisibility(View.GONE);

        llSeatsExpandableContent = findViewById(R.id.ll_filter_expandable_seats);
        llSeatsExpandableContent.setVisibility(View.GONE);

        SeatListController seatListController = new SeatListController(this);
        seatList = new ArrayList<>();
        seatList.addAll(seatListController.getAllSeats());
        seatListFull = new ArrayList<>();
        seatListFull.addAll(seatList);  // Making a full copy of seatList to use for displaying Filters

        seatListFiltered = new ArrayList();

        roomList = new ArrayList<>();
        roomList.addAll(seatListController.getAllRooms());
        Log.d(TAG, "INITIAL CREATE");

        setListAdapter();
        fillFilterView();
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
        switch (item.getItemId()) {
            case R.id.action_filter:
                cycleFilterUI();
                break;
            case R.id.action_refresh:
                SeatDataRetrievalTask task = new SeatDataRetrievalTask(new SQLiteDataManagement(this), new SQLiteDataDefinition(this));
                task.execute();
                SeatListController seatListController = new SeatListController(this);
                seatList = seatListController.getAllSeats();
                Toast.makeText(this, "Fetching data", Toast.LENGTH_LONG).show();
                setListAdapter();
                seatListFull.clear();   // Re-makes the seatListFull from seatList's new data
                seatListFull.addAll(seatList);
                updateSeatList();
                break;
            case android.R.id.home:
                if (rlFilterView.getVisibility() == View.VISIBLE) {
                    cycleFilterUI();
                    break;
                }
                drawerLayout.openDrawer(Gravity.START);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void setListAdapter() {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, seatList);
        listViewSeats.setAdapter(adapter);
    }

    public static void updateSeatList() {
        adapter.notifyDataSetChanged();
    }

    /**
     * Updates UI - View, Toolbar, Icons, Titles, & Nav Drawer
     */
    private void cycleFilterUI() {
        if (rlFilterView.getVisibility() == View.GONE) { // If filter view isn't visible, display it
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            listViewSeats.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_off_left));
            listViewSeats.setVisibility(View.GONE);
            refreshItem.setVisible(false); // Removing Toolbar items
            filterItem.setVisible(false);
            rlFilterView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_on_left));
            rlFilterView.setVisibility(View.VISIBLE); // Displaying View
            toolbarSeat.setTitle(R.string.toolbar_seat_filter_title);
            toolbarSeat.setNavigationIcon(R.drawable.ic_action_arrow);
            seatListFiltered.addAll(seatList);
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

    /**
     * Toggles expand on TextViews content depending on tag set in activity_seat.xml
     */
    public void handlerExpandableToggle(View view) {
        String contextTag = view.getTag().toString(); // Retrieves tag for context on what to toggle
        switch (contextTag) {
            case "rooms": // Toggles the first TextViews content
                cycleRoomsExpandableUI();
                break;
            case "seats":
                cycleSeatsExpandableUI();
                break;
            default:
                break;
        }
    }

    /**
     * Initial create of filter Switch elements
     */
    private void fillFilterView() { // ToDo: refine this
        for (Room room : roomList) {
            Switch newSwitch = new Switch(this);
            newSwitch.setChecked(true);
            newSwitch.setText(room.toString());
            newSwitch.setTag("Room");
            newSwitch.setId(room.getRoomId()); // Could cause issues if roomIds are the same, alternative is to pass Room object in Tag
            newSwitch.setMinimumHeight(getResources().getInteger(R.integer.filter_switch_min_height));
            newSwitch.setSwitchMinWidth(getResources().getInteger(R.integer.filter_switch_min_width));
            llRoomsExpandableContent.addView(newSwitch);
            newSwitch.setOnClickListener(new SwitchItemOnClickListener(newSwitch, this));
        }
        int i = 0;
        for (Seat seat : seatListFull) {
            Switch newSwitch = new Switch(this);
            newSwitch.setChecked(true);
            newSwitch.setText(seat.toString());
            newSwitch.setTag(seat); // Sets metadata in Switch element
            newSwitch.setId(i); // ToDo: Check switch.getId is used
            newSwitch.setMinimumHeight(100);
            newSwitch.setSwitchMinWidth(150);
            llSeatsExpandableContent.addView(newSwitch);
            newSwitch.setOnClickListener(new SwitchItemOnClickListener(newSwitch, this));
            i++;
        }
    }

    /**
     * Syncs Switch status with seatList
     */
    public static void updateSwitchUI() {
        for (int i = 0; i < llSeatsExpandableContent.getChildCount(); i++) {
            Boolean found = false;
            Switch button = (Switch) llSeatsExpandableContent.getChildAt(i);
            Seat seatTag = (Seat) button.getTag();

            for (Seat seat : seatListFiltered) {
                if (Objects.equals(seat.getRoomId(), seatTag.getRoomId())) {
                    button.setChecked(true);
                    found = true;
                    break;
                }
            }
            if (!found) {
                button.setChecked(false);
            }
        }
    }

    /**
     * Removes data from original seatList, copies all data from seatListFiltered, then updates the adapter
     */
    public void handlerApplyFilter(View v) {
        seatList.clear();
        seatList.addAll(seatListFiltered);
        seatListFiltered.clear();
        updateSeatList();
        cycleFilterUI();
    }

    /**
     * Toggles specifically the LinearLayout Expandable Rooms View
     */
    private void cycleRoomsExpandableUI() {
        if (llRoomsExpandableContent.getVisibility() == View.VISIBLE) { // If the View is already displayed, hide it
            llRoomsExpandableContent.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_up_vanish));
            llRoomsExpandableContent.postDelayed(new Runnable() {
                @Override
                public void run() {
                    llRoomsExpandableContent.setVisibility(View.GONE);
                }
            }, getResources().getInteger(R.integer.slide_animation_duration_quick));
            return;
        }   // Otherwise load contents then show it
        llRoomsExpandableContent.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_down_appear));
        llRoomsExpandableContent.setVisibility(View.VISIBLE);
    }

    /**
     * Toggles specifically the LinearLayout Expandable Seats View
     */
    private void cycleSeatsExpandableUI() {
        if (llSeatsExpandableContent.getVisibility() == View.VISIBLE) { // If the View is already displayed, hide it
            llSeatsExpandableContent.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_up_vanish));
            llSeatsExpandableContent.postDelayed(new Runnable() {
                @Override
                public void run() {
                    llSeatsExpandableContent.setVisibility(View.GONE);
                }
            }, getResources().getInteger(R.integer.slide_animation_duration_quick));
            return;
        } // Otherwise load contents then show it
        llSeatsExpandableContent.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_down_appear));
        llSeatsExpandableContent.setVisibility(View.VISIBLE);
    }
}
