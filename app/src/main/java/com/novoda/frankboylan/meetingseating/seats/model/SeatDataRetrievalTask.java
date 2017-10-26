package com.novoda.frankboylan.meetingseating.seats.model;

import android.os.AsyncTask;

import com.novoda.frankboylan.meetingseating.RoomSeatData;

import java.util.Arrays;
import java.util.List;

public class SeatDataRetrievalTask extends AsyncTask<RoomSeatData, Void, Void> {
    private final RoomDatabaseWriter roomDatabaseWriter;

    public SeatDataRetrievalTask(RoomDatabaseWriter roomDatabaseWriter) {
        this.roomDatabaseWriter = roomDatabaseWriter;
    }

    @Override
    protected Void doInBackground(RoomSeatData... roomSeatData) {
        List<RoomSeatData> roomSeatData1 = Arrays.asList(roomSeatData);
        roomDatabaseWriter.add(roomSeatData1.get(0));
        return null;
    }
}
