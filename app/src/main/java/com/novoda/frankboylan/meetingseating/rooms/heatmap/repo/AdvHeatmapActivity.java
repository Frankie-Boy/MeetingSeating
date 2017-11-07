package com.novoda.frankboylan.meetingseating.rooms.heatmap.repo;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.novoda.frankboylan.meetingseating.R;
import com.novoda.frankboylan.meetingseating.rooms.heatmap.repo.model.InternalDatabase;

public class AdvHeatmapActivity extends AppCompatActivity {
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
}
