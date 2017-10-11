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

import java.util.Iterator;
import java.util.List;

public class SeatActivity extends AppCompatActivity {
    private static final String TAG = "SeatActivity";
    SqliteDML sqliteDML;
    private Toolbar toolbarSeat;
    private DrawerLayout drawerLayout;
    ListView listViewSeats;
    RelativeLayout rlFilterView;
    LinearLayout llRoomsExpandableContent, llSeatsExpandableContent;
    MenuItem refreshItem, filterItem;
    List<Seat> seatsList;
    boolean initialToggleSeats = false, initialToggleRooms = false;

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


        llRoomsExpandableContent = findViewById(R.id.ll_filter_expandable_rooms);
        //llRoomsExpandableContent.setVisibility(View.GONE);

        llSeatsExpandableContent = findViewById(R.id.ll_filter_expandable_seats);
        //llSeatsExpandableContent.setVisibility(View.GONE);
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
                cycleFilterUI();
                break;
            case R.id.action_refresh:
                JSONParser jsonParser = new JSONParser(this);
                jsonParser.execute();
                updateList();
                break;
            case android.R.id.home:
                if(rlFilterView.getVisibility() == View.VISIBLE) {
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
    /**
     * Refreshes ListViews with SQLite data
     */
    private void updateList() {
        seatsList = getSeatList();
        ArrayAdapter<Seat> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, seatsList);
        listViewSeats.setAdapter(adapter);
    }

    /**
     * Retrieves all Seats in a seatList via SqliteDML
     */
    private List<Seat> getSeatList() {
        sqliteDML = new SqliteDML(this);
        return sqliteDML.getAllSeats();
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
            displayRoomSwitches();
            displaySeatSwitches();
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
    public void handlerExpandableToggle(View view) { // Ignore warning, this is being used (styles.xml)
        String contextTag = view.getTag().toString(); // Retrieves tag for context on what to toggle
        switch(contextTag) {
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

    private void displayRoomSwitches() {
        llRoomsExpandableContent.removeAllViewsInLayout();
        List<Room> roomsList = sqliteDML.getAllRooms();
        for(Room room : roomsList) {
            final Switch newSwitch = new Switch(this);
            if(!initialToggleRooms) {
                newSwitch.setChecked(true);
            }
            newSwitch.setText(room.toString());
            newSwitch.setId(room.getRoomId()); // Could cause issues if roomIds are the same
            newSwitch.setMinimumHeight(getResources().getInteger(R.integer.filter_switch_min_height));
            newSwitch.setSwitchMinWidth(getResources().getInteger(R.integer.filter_switch_min_width));
            llRoomsExpandableContent.addView(newSwitch);
            newSwitch.setOnClickListener(new View.OnClickListener() { // ToDo: Move this to its own method
                @Override
                public void onClick(View v) {
                    if(newSwitch.isChecked()) {
                        // ToDo: (NEW DML) Add specific seats to List (param: newSwitch.getId) (ROOMID)
                        return;
                    }
                    Iterator<Seat> iterator = seatsList.iterator(); // Iterator used to avoid Concurrent Modification Exception
                    while(iterator.hasNext()) {
                        Seat seatCheck = iterator.next();
                        if(seatCheck.getRoomId() == newSwitch.getId()) { // Indentation madness, refine this
                            iterator.remove();
                        }
                    }
                    initialToggleSeats = false;
                    displaySeatSwitches();
                }
            });
        }
        initialToggleRooms = true;
    }

    private void displaySeatSwitches() {
        llSeatsExpandableContent.removeAllViewsInLayout(); // Clears all of the Switches
        int i = 0;
        for(Seat seat : seatsList) {
            Log.d(TAG, seatsList.get(i) + "");
            Switch newSwitch = new Switch(this);
            if(!initialToggleSeats) {
                newSwitch.setChecked(true);
            }
            newSwitch.setText(seat.toString());
            newSwitch.setId(i); // Could cause issues if roomIds are the same
            newSwitch.setMinimumHeight(getResources().getInteger(R.integer.filter_switch_min_height));
            newSwitch.setSwitchMinWidth(getResources().getInteger(R.integer.filter_switch_min_width));
            llSeatsExpandableContent.addView(newSwitch);
            i++;
        }
        initialToggleSeats = true;
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