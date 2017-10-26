package com.novoda.frankboylan.meetingseating.seats.model;

import android.os.AsyncTask;

import com.novoda.frankboylan.meetingseating.RoomSeatData;
import com.novoda.frankboylan.meetingseating.SQLiteDataManagement.SQLiteRead;
import com.novoda.frankboylan.meetingseating.seats.RoomDatabaseWriter;

import retrofit2.Response;

public class SeatDataRetrievalTask extends AsyncTask<Void, Void, Void> {
    private static final String TAG = "SeatDataRetrievalTask";

    private SeatModel seatModel;
    private SQLiteRead sqliteRead;
    private final RoomDatabaseWriter roomDatabaseWriter;

    public SeatDataRetrievalTask(SeatModel seatModel, SQLiteRead sqliteRead, RoomDatabaseWriter roomDatabaseWriter) {
        this.seatModel = seatModel;
        this.sqliteRead = sqliteRead;
        this.roomDatabaseWriter = roomDatabaseWriter;
    }

    @Override
    protected Void doInBackground(Void... params) {
        Response<RoomSeatData> response = seatModel.retrieveData();
        RoomSeatData roomSeatData = response.body();
        long serverResponseTimestamp = 0L;

        if (response.isSuccessful() && roomSeatData != null) {
            serverResponseTimestamp = Long.valueOf(roomSeatData.getLastUpdateTimestamp());
        }
        long databaseTimestamp = sqliteRead.getMetaTimestamp().getTimestamp();

        if (serverResponseTimestamp > databaseTimestamp) {  // Checking data's Timestamp is newer than stored version.
            roomDatabaseWriter.add(roomSeatData);
        }
        return null;
    }
}
