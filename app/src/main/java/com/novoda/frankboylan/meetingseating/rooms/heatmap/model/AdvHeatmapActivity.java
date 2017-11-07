package com.novoda.frankboylan.meetingseating.rooms.heatmap.model;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.novoda.frankboylan.meetingseating.R;

public class AdvHeatmapActivity extends AppCompatActivity {
    private static final String DATABASE_NAME = "AdvDatabase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adv_heatmap);

        final InternalDatabase database = Room.databaseBuilder(
                getApplicationContext(),
                InternalDatabase.class,
                DATABASE_NAME
        ).build();

    }
}
