package com.novoda.frankboylan.meetingseating;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

class SeatDataRetrievalTask extends AsyncTask<Void, Void, Void> {
    private static final String TAG = "SeatDataRetrievalTask";

    private Context mContext;

    SeatDataRetrievalTask(Context context) {
        mContext = context;
    }

    @Override
    protected Void doInBackground(Void... params) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AwsSeatMonitorService.BASE)
                .addConverterFactory(MoshiConverterFactory.create())
                .client(new OkHttpClient())
                .build();
        AwsSeatMonitorService awsSeatMonitorService = retrofit.create(AwsSeatMonitorService.class);
        RoomSeatData roomSeatData;
        long serverResponseTimestamp = 0L;
        try {
            Response<RoomSeatData> response = awsSeatMonitorService.seatMonitorData().execute();
            roomSeatData = response.body();
            if (response.isSuccessful() && roomSeatData != null) {
                serverResponseTimestamp = Long.valueOf(roomSeatData.getLastUpdateTimestamp());
            }
        } catch (IOException e) {
            throw new IllegalStateException(e); // response throws an IOException when devices wifi is offline.
        }
        SQLiteDataManagement sqliteDataManagement = new SQLiteDataManagement(mContext);
        long databaseTimestamp = sqliteDataManagement.getMetaTimestamp().getTimestamp();
        if (serverResponseTimestamp < databaseTimestamp) {
            return null;
        }
        SQLiteDataDefinition sqliteDataDefinition = new SQLiteDataDefinition(mContext);
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
