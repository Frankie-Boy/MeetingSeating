package com.novoda.frankboylan.meetingseating;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;


class JSONParser extends AsyncTask<Void, Void, Void> {
    private static final String TAG = "JSONParser";

    private Context mContext;

    JSONParser(Context context) {
        mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Toast.makeText(mContext, "Fetching data", Toast.LENGTH_LONG).show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AwsSeatMonitorService.BASE)
                .addConverterFactory(MoshiConverterFactory.create())
                .client(new OkHttpClient())
                .build();
        AwsSeatMonitorService awsSeatMonitorService = retrofit.create(AwsSeatMonitorService.class);
        RoomSeatData roomSeatData = new RoomSeatData();
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
        DBHelper dbHelper = new DBHelper(mContext);
        long databaseTimestamp = dbHelper.getMetaTimestamp().getTimestamp();
        if (serverResponseTimestamp < databaseTimestamp) {
            return null;
        }
        if (roomSeatData.getRooms().isEmpty()) {
            Log.d(TAG, "No rooms found");
        } else {
            dbHelper.clearData();
            dbHelper.setMetaTimestamp(serverResponseTimestamp);
            for (Room room : roomSeatData.getRooms()) {
                dbHelper.addRoom(room);
                for (Seat seat : room.getSeats()) {
                    seat.setRoomId(room.getRoomId());
                    dbHelper.addSeat(seat);
                }
            }
        }
        debugLog(dbHelper);
        dbHelper.close();
        return null;
    }

    private void debugLog(DBHelper dbHelper) {
        // Print DB
        List<Seat> seatList = dbHelper.getAllSeats();
        for (int i = 0; i < seatList.size(); i++) {
            Log.d("SEAT_DB", seatList.get(i).toString());
        }
        List<Room> roomList = dbHelper.getAllRooms();
        for (int j = 0; j < roomList.size(); j++) {
            Log.d("ROOM_DB", roomList.get(j).toString());
        }
    }

}
