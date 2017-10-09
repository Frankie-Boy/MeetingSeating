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

public class RoomActivity extends AppCompatActivity {
    private static final String TAG = "RoomActivity";
    private ListView listViewRooms;
    private DBHelper dbHelper;
    private List<Room> roomList = new ArrayList<Room>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        Toolbar toolbarRoom = (Toolbar)findViewById(R.id.toolbar_room);
        toolbarRoom.setTitle("Room List"); // ToDo: Reference strings.xml
        setSupportActionBar(toolbarRoom);

        listViewRooms = (ListView)findViewById(R.id.listview_all_rooms);

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
        roomList = dbHelper.getAllRooms();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, roomList);
        listViewRooms.setAdapter(adapter);
    }
}