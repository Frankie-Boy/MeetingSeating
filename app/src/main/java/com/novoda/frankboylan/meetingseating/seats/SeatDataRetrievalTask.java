package com.novoda.frankboylan.meetingseating.seats;

import android.os.AsyncTask;
import android.util.Log;

import com.novoda.frankboylan.meetingseating.Room;
import com.novoda.frankboylan.meetingseating.RoomSeatData;
import com.novoda.frankboylan.meetingseating.SQLiteDataManagement.SQLiteDelete;
import com.novoda.frankboylan.meetingseating.SQLiteDataManagement.SQLiteInsert;
import com.novoda.frankboylan.meetingseating.SQLiteDataManagement.SQLiteRead;
import com.novoda.frankboylan.meetingseating.SQLiteDataManagement.SQLiteUpdate;

import retrofit2.Response;

public class SeatDataRetrievalTask extends AsyncTask<Void, Void, Void> {
    private static final String TAG = "SeatDataRetrievalTask";

    private SQLiteRead sqliteRead;
    private SQLiteDelete sqliteDelete;
    private SQLiteInsert sqliteInsert;
    private SQLiteUpdate sqliteUpdate;

    public SeatDataRetrievalTask(SQLiteRead sqliteRead, SQLiteDelete sqliteDelete, SQLiteInsert sqliteInsert, SQLiteUpdate sqliteUpdate) {
        this.sqliteRead = sqliteRead;
        this.sqliteDelete = sqliteDelete;
        this.sqliteInsert = sqliteInsert;
        this.sqliteUpdate = sqliteUpdate;
    }

    @Override
    protected Void doInBackground(Void... params) {
        SeatModel model = new SeatModelImpl(sqliteRead, sqliteDelete, sqliteInsert);
        Response<RoomSeatData> response = model.retrieveData();
        RoomSeatData roomSeatData = response.body();
        long serverResponseTimestamp = 0L;

        if (response.isSuccessful() && roomSeatData != null) {
            serverResponseTimestamp = Long.valueOf(roomSeatData.getLastUpdateTimestamp());
        }
        long databaseTimestamp = sqliteRead.getMetaTimestamp().getTimestamp();

        if (serverResponseTimestamp > databaseTimestamp) {  // Checking data's Timestamp is newer than stored version.
            if (roomSeatData.getRooms().isEmpty()) {
                Log.d(TAG, "No rooms found");
            } else {
                sqliteDelete.clearRoomSeatData();
                sqliteUpdate.updateMetaTimestamp(Long.valueOf(roomSeatData.getLastUpdateTimestamp()));
                for (Room room : roomSeatData.getRooms()) {
                    sqliteInsert.addRoom(room);
                    for (Seat seat : room.getSeats()) {
                        seat.setRoomId(room.getRoomId());
                        sqliteInsert.addSeat(seat);
                    }
                }
            }
            sqliteRead.debugLog();
        }
        return null;
    }
}
