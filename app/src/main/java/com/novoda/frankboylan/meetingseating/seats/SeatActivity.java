package com.novoda.frankboylan.meetingseating.seats;

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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.novoda.frankboylan.meetingseating.ConnectionStatus;
import com.novoda.frankboylan.meetingseating.DrawerItemClickListener;
import com.novoda.frankboylan.meetingseating.R;
import com.novoda.frankboylan.meetingseating.Room;
import com.novoda.frankboylan.meetingseating.SQLiteDataManagement.SQLiteDelete;
import com.novoda.frankboylan.meetingseating.SQLiteDataManagement.SQLiteInsert;
import com.novoda.frankboylan.meetingseating.SQLiteDataManagement.SQLiteRead;
import com.novoda.frankboylan.meetingseating.SQLiteDataManagement.SQLiteUpdate;
import com.novoda.frankboylan.meetingseating.seats.model.SeatDataRetrievalTask;
import com.novoda.frankboylan.meetingseating.seats.model.SeatModel;
import com.novoda.frankboylan.meetingseating.seats.model.SeatModelImpl;

import java.util.List;

public class SeatActivity extends AppCompatActivity implements SeatDisplayer {
    private static final String TAG = "SeatActivity";

    private Toolbar toolbarSeat;
    private DrawerLayout drawerLayout;
    ListView listViewSeats;
    RelativeLayout rlFilterView;
    LinearLayout llRoomsExpandableContent;
    LinearLayout llSeatsExpandableContent;
    MenuItem refreshItem, filterItem, resetItem;
    TextView tvEmptyList;
    private SeatPresenterImpl seatPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat);

        final FirebaseAuth auth = FirebaseAuth.getInstance();

        ListView drawerList = findViewById(R.id.side_drawer);

        drawerLayout = findViewById(R.id.drawer_layout);

        final TextView tvLoggedUser = findViewById(R.id.tv_drawer_greeting);


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

        SQLiteDelete sqliteDelete = new SQLiteDelete(this);
        SQLiteUpdate sqliteUpdate = new SQLiteUpdate(this);
        SQLiteRead sqliteRead = new SQLiteRead(this);
        SQLiteInsert sqliteInsert = new SQLiteInsert(this);
        RoomDatabaseWriter roomDatabaseWriter = new RoomDatabaseWriter(sqliteDelete, sqliteUpdate, sqliteInsert, sqliteRead);

        SeatModel seatModel = new SeatModelImpl(sqliteRead, sqliteDelete, sqliteInsert);

        if (ConnectionStatus.hasActiveInternetConnection()) {
            DatabaseReference firebaseDb = FirebaseDatabase.getInstance().getReference();
            firebaseDb.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String firstname = dataSnapshot.child("users").child(auth.getUid()).child("firstname").getValue().toString();
                    tvLoggedUser.setText("Welcome, " + firstname + "!");
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            SeatDataRetrievalTask task = new SeatDataRetrievalTask(seatModel, sqliteRead, roomDatabaseWriter);
            showToast("Fetching Data...");
            task.execute();
        }

        seatPresenter = new SeatPresenterImpl(this, seatModel);
        seatPresenter.bind(this);

        seatPresenter.updateAllLists();
        seatPresenter.setLinearLayouts(llRoomsExpandableContent, llSeatsExpandableContent);
        seatPresenter.fillFilterView();

        List<Seat> seatList = seatPresenter.getSeatList();
        tvEmptyList = findViewById(R.id.tv_list_text);

        if (seatList.isEmpty()) {
            tvEmptyList.setVisibility(View.VISIBLE);
        } else {
            tvEmptyList.setVisibility(View.GONE);
        }

        ArrayAdapter<Seat> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, seatList);
        listViewSeats.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.seat_toolbar_content, menu);
        refreshItem = menu.findItem(R.id.action_refresh);
        filterItem = menu.findItem(R.id.action_filter);
        resetItem = menu.findItem(R.id.action_reset);
        resetItem.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter:
                cycleFilterUI();
                break;
            case R.id.action_refresh:
                seatPresenter.onRefresh();
                break;
            case android.R.id.home:
                if (rlFilterView.getVisibility() == View.VISIBLE) {
                    cycleFilterUI();
                    break;
                }
                drawerLayout.openDrawer(Gravity.START);
                break;
            case R.id.action_reset:
                seatPresenter.resetAllSwitch();
                break;
        }
        return true;
    }

    @Override
    public void updateSeatList(List<Seat> seatList) {
        if (seatList.isEmpty()) {
            tvEmptyList.setVisibility(View.VISIBLE);
        } else {
            tvEmptyList.setVisibility(View.GONE);
        }
        ArrayAdapter<Seat> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, seatList);
        listViewSeats.setAdapter(adapter);
    }

    @Override
    public void updateSwitchList(List<Seat> cachedSeatList) {
        for (Seat seat : cachedSeatList) {
            Switch v = llSeatsExpandableContent.findViewWithTag(seat);
            if (v != null) {
                v.setChecked(true);
            }
        }
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
            resetItem.setVisible(true);
            rlFilterView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_on_left));
            rlFilterView.setVisibility(View.VISIBLE); // Displaying View
            toolbarSeat.setTitle(R.string.toolbar_seat_filter_title);
            toolbarSeat.setNavigationIcon(R.drawable.ic_action_arrow);
            seatPresenter.onFilterPressed();
            return;
        }
        // Else filter view is visible, so hide it!
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        rlFilterView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_off_right));
        rlFilterView.setVisibility(View.GONE); // Hiding View
        listViewSeats.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_on_right));
        listViewSeats.setVisibility(View.VISIBLE);
        if (tvEmptyList.getVisibility() == View.VISIBLE) {
            tvEmptyList.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_on_right));
        }
        toolbarSeat.setTitle(R.string.toolbar_seat_title);
        toolbarSeat.setNavigationIcon(R.drawable.ic_action_burger);
        refreshItem.setVisible(true); // Adding Toolbar items
        filterItem.setVisible(true);
        resetItem.setVisible(false);
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
    public void addRoomSwitchElement(Room room) {
        Switch newSwitch = new Switch(this);
        newSwitch.setChecked(true);
        newSwitch.setText(room.toString());
        newSwitch.setTag("Room");
        newSwitch.setId(room.getRoomId()); // Could cause issues if roomIds are the same, alternative is to pass Room object in Tag
        newSwitch.setMinimumHeight(getResources().getInteger(R.integer.filter_switch_min_height));
        newSwitch.setSwitchMinWidth(getResources().getInteger(R.integer.filter_switch_min_width));
        llRoomsExpandableContent.addView(newSwitch);
        newSwitch.setOnClickListener(new SwitchItemOnClickListener(newSwitch, seatPresenter));
    }

    public void addSeatSwitchElement(Seat seat) {
        Switch newSwitch = new Switch(this);
        newSwitch.setChecked(true);
        newSwitch.setText(seat.toString());
        newSwitch.setTag(seat); // Sets metadata in Switch element to seat object
        newSwitch.setId(0);
        newSwitch.setMinimumHeight(100);
        newSwitch.setSwitchMinWidth(150);
        llSeatsExpandableContent.addView(newSwitch);
        newSwitch.setOnClickListener(new SwitchItemOnClickListener(newSwitch, seatPresenter));
    }

    /**
     * Removes data from original seatList, copies all data from seatListFiltered, then updates the adapter
     */
    public void handlerApplyFilter(View v) {
        seatPresenter.onApplyFilter();
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

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        seatPresenter.unbind();
        super.onDestroy();
    }
}
