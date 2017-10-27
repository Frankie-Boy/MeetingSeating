package com.novoda.frankboylan.meetingseating.seats.model;

import android.util.Log;

import com.novoda.frankboylan.meetingseating.RoomSeatData;
import com.novoda.frankboylan.meetingseating.SQLiteDataManagement.SQLiteDelete;
import com.novoda.frankboylan.meetingseating.SQLiteDataManagement.SQLiteInsert;
import com.novoda.frankboylan.meetingseating.SQLiteDataManagement.SQLiteRead;
import com.novoda.frankboylan.meetingseating.SQLiteDataManagement.SQLiteUpdate;
import com.novoda.frankboylan.meetingseating.heatmap.Room;

public class RoomDatabaseWriter {

    private static final String TAG = "RoomDatabaseWriter";
    private SQLiteDelete sqliteDelete;
    private SQLiteUpdate sqliteUpdate;
    private SQLiteInsert sqliteInsert;
    private SQLiteRead sqliteRead;

    public RoomDatabaseWriter(SQLiteDelete sqliteDelete, SQLiteUpdate sqliteUpdate, SQLiteInsert sqliteInsert, SQLiteRead sqliteRead) {
        this.sqliteDelete = sqliteDelete;
        this.sqliteUpdate = sqliteUpdate;
        this.sqliteInsert = sqliteInsert;
        this.sqliteRead = sqliteRead;
    }

    public void add(RoomSeatData roomSeatData) {
        if (roomSeatData.getRooms().isEmpty()) {
            Log.d(TAG, "No rooms found");
            return;
        }
        sqliteDelete.clearRoomSeatData();
        sqliteDelete.clearSeatCache();
        Log.d(TAG, "ts: " + roomSeatData.getLastUpdateTimestamp());
        sqliteUpdate.updateMetaTimestamp(Long.valueOf(roomSeatData.getLastUpdateTimestamp()));
        for (Room room : roomSeatData.getRooms()) {
            room.updateSeatRoomIds();
            sqliteInsert.addRoom(room);
        }
        sqliteRead.debugLog();

    }
}
