package com.novoda.frankboylan.meetingseating.seats.model;

import android.os.AsyncTask;

import com.novoda.frankboylan.meetingseating.RoomSeatData;

public class SeatDataRetrievalTask extends AsyncTask<RoomSeatData, Void, Void> {
    private final RoomDatabaseWriter roomDatabaseWriter;

    public SeatDataRetrievalTask(RoomDatabaseWriter roomDatabaseWriter) {
        this.roomDatabaseWriter = roomDatabaseWriter;
    }

    @Override
    protected Void doInBackground(RoomSeatData... roomSeatData) {
        roomDatabaseWriter.add(roomSeatData[0]);
        return null;
    }
}
