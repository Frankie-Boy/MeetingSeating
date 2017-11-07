package com.novoda.frankboylan.meetingseating.rooms.heatmap.adv.repo;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.novoda.frankboylan.meetingseating.R;
import com.novoda.frankboylan.meetingseating.rooms.heatmap.adv.repo.model.AdvHeatmapRoom;
import com.novoda.frankboylan.meetingseating.rooms.heatmap.adv.repo.model.AdvHeatmapSeat;
import com.novoda.frankboylan.meetingseating.rooms.heatmap.adv.repo.model.InternalDatabase;

import java.util.List;

public class AdvHeatmapActivity extends AppCompatActivity implements AdvHeatmapDisplayer {
    private static final String DATABASE_NAME = "AdvDatabase";
    ListView roomList, seatList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adv_heatmap);

        roomList = findViewById(R.id.lv_room_list);
        seatList = findViewById(R.id.lv_seat_list);

        final InternalDatabase database = Room.databaseBuilder(
                getApplicationContext(),
                InternalDatabase.class,
                DATABASE_NAME
        ).build();
    }

    @Override
    public void updateRoomList(List<AdvHeatmapRoom> roomList) {
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, roomList);
        this.roomList.setAdapter(adapter);
    }

    @Override
    public void updateSeatList(List<AdvHeatmapSeat> seatList) {
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, seatList);
        this.seatList.setAdapter(adapter);
    }
}
