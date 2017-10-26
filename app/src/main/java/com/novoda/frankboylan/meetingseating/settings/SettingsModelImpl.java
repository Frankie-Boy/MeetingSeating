package com.novoda.frankboylan.meetingseating.settings;

import android.content.res.AssetManager;
import android.util.Log;

import com.novoda.frankboylan.meetingseating.Room;
import com.novoda.frankboylan.meetingseating.RoomSeatData;
import com.novoda.frankboylan.meetingseating.SQLiteDataManagement.SQLiteDelete;
import com.novoda.frankboylan.meetingseating.SQLiteDataManagement.SQLiteInsert;
import com.novoda.frankboylan.meetingseating.SQLiteDataManagement.SQLiteRead;
import com.novoda.frankboylan.meetingseating.SQLiteDataManagement.SQLiteUpdate;
import com.novoda.frankboylan.meetingseating.Seat;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.io.InputStream;

import okio.Buffer;

class SettingsModelImpl implements SettingsModel {
    private static final String TAG = "SettingsModelImpl";
    private SQLiteDelete sqliteDelete;
    private SQLiteInsert sqliteInsert;
    private SQLiteUpdate sqliteUpdate;
    private SQLiteRead sqliteRead;
    private AssetManager assetManager;

    SettingsModelImpl(SQLiteDelete sqliteDelete, SQLiteInsert sqliteInsert, SQLiteUpdate sqliteUpdate, SQLiteRead sqliteRead, AssetManager assetManager) {
        this.sqliteDelete = sqliteDelete;
        this.sqliteInsert = sqliteInsert;
        this.sqliteUpdate = sqliteUpdate;
        this.sqliteRead = sqliteRead;
        this.assetManager = assetManager;
    }

    @Override
    public void replaceWithDataset(int i) {
        switch (i) {
            case 0:
                loadJSONFromFile("europe.txt");
                break;
            case 1:
                loadJSONFromFile("continents.txt");
                break;
            default:
                Log.d(TAG, "That dataset doesn't exist!");
                break;
        }
    }

    private void loadJSONFromFile(String directory) {
        try {
            InputStream input = assetManager.open(directory);

            Moshi build = new Moshi.Builder().build();
            JsonAdapter<RoomSeatData> adapter = build.adapter(RoomSeatData.class);
            Buffer buffer = new Buffer();
            buffer.readFrom(input);
            RoomSeatData roomSeatData = adapter.fromJson(buffer);

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
        } catch (IOException e) {
            throw new IllegalStateException("Expected a file at " + directory + " but got nothing! FUBAR");
        }
    }
}
