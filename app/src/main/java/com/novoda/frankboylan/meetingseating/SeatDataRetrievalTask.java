package com.novoda.frankboylan.meetingseating;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import retrofit2.Response;

class SeatDataRetrievalTask extends AsyncTask<Void, Void, Void> {
    private static final String TAG = "SeatDataRetrievalTask";
    private SeatModel model;

    private SQLiteDataManagement sqliteDataManagement;
    private SQLiteDataDefinition sqliteDataDefinition;

    SeatDataRetrievalTask(SQLiteDataManagement sqliteDataManagement, SQLiteDataDefinition sqliteDataDefinition) {
        this.sqliteDataManagement = sqliteDataManagement;
        this.sqliteDataDefinition = sqliteDataDefinition;
    }

    @Override
    protected Void doInBackground(Void... params) {
        model = new SeatModelImpl();
        RoomSeatData roomSeatData;
        long serverResponseTimestamp = 0L;

        Response<RoomSeatData> response = model.retrieveData();
        roomSeatData = response.body();
        if (response.isSuccessful() && roomSeatData != null) {
            serverResponseTimestamp = Long.valueOf(roomSeatData.getLastUpdateTimestamp());
        }
        long databaseTimestamp = sqliteDataManagement.getMetaTimestamp().getTimestamp();
        if (serverResponseTimestamp < databaseTimestamp) {
            return null;
        }
        if (roomSeatData.getRooms().isEmpty()) {
            Log.d(TAG, "No rooms found");
        } else {
            sqliteDataDefinition.clearData();
            sqliteDataManagement.setMetaTimestamp(serverResponseTimestamp);
            for (Room room : roomSeatData.getRooms()) {
                sqliteDataManagement.addRoom(room);
                for (Seat seat : room.getSeats()) {
                    seat.setRoomId(room.getRoomId());
                    sqliteDataManagement.addSeat(seat);
                }
            }
        }
        debugLog(sqliteDataManagement);
        sqliteDataDefinition.close();
        return null;
    }

    private void debugLog(SQLiteDataManagement sqliteDataManagement) {
        // Print DB
        List<Seat> seatList = sqliteDataManagement.getAllSeats();
        for (int i = 0; i < seatList.size(); i++) {
            Log.d("TABLE_SEAT", seatList.get(i).toString());
        }
        List<Room> roomList = sqliteDataManagement.getAllRooms();
        for (int j = 0; j < roomList.size(); j++) {
            Log.d("TABLE_ROOM", roomList.get(j).toString());
        }
    }

}
