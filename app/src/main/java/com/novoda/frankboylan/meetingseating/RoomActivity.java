package com.novoda.frankboylan.meetingseating;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class RoomActivity extends AppCompatActivity {
    private static final String TAG = "RoomActivity";
    private ListView listViewRooms;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        ListView drawerList = findViewById(R.id.side_drawer);
        drawerLayout = findViewById(R.id.drawer_layout);

        String[] mDrawerOptions = getResources().getStringArray(R.array.drawer_options);
        drawerList.setAdapter(new ArrayAdapter<>(this,
                R.layout.drawer_list_item, mDrawerOptions));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        Toolbar toolbarRoom = findViewById(R.id.toolbar_room);
        toolbarRoom.setTitle(R.string.toolbar_room_title);
        toolbarRoom.setNavigationIcon(R.drawable.ic_action_burger);
        setSupportActionBar(toolbarRoom);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listViewRooms = findViewById(R.id.listview_all_rooms);
        updateList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.room_toolbar_content, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_heat:
                // ToDo: Display heatmap layout
                break;
            case R.id.action_refresh:
                JSONParser jsonParser = new JSONParser(this);
                jsonParser.execute();
                updateList();
                break;
            case android.R.id.home:
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
        SqliteDML sqliteDML = new SqliteDML(this);
        List<Room> roomList = sqliteDML.getAllRooms();
        ArrayAdapter<Room> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, roomList);
        listViewRooms.setAdapter(adapter);
    }
}