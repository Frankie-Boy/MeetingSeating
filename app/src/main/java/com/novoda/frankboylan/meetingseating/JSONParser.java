package com.novoda.frankboylan.meetingseating;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


class JSONParser extends AsyncTask<Void, Void, Void> {
    private static final String TAG = "JSONParser";
    private static final String BASE = "https://f8v3dmak5d.execute-api.eu-west-1.amazonaws.com";
    private static final String ENV = "/prod";
    private static final String SEAT_MONITOR = "/seat-monitor-data";
    private static final String ENDPOINT_SEAT_MONITOR = BASE + ENV + SEAT_MONITOR; // Data end-point URL

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
        OkHttpClient client = new OkHttpClient();

        // OkHttp Request start
        Request request = new Request.Builder()
                .url(ENDPOINT_SEAT_MONITOR)
                .build();
        String jsonString = null;
        try {
            Response response = client.newCall(request).execute();
            Log.d(TAG, "Response from url: " + response);
            ResponseBody responseBody = response.body();
            if (response.isSuccessful() && responseBody != null) {
                jsonString = responseBody.string();
            }
        } catch (IOException e) {
            throw new IllegalStateException("Fubar", e);
        }

        if (jsonString == null) {
            throw new IllegalStateException("Couldn't get JSON from server.");
        }
        try {
            DBHelper dbHelper = new DBHelper(mContext);
            dbHelper.clearData(); // Clear existing data

            JSONObject jsonObj = new JSONObject(jsonString);
            Long newUpdateTimestamp = jsonObj.getLong("lastUpdateTimestamp");
            if(dbHelper.getMetaTimestamp().getTimestamp() < newUpdateTimestamp) { // If the data is newer than the last update
                dbHelper.setMetaTimestamp(newUpdateTimestamp); // Update the timestamp

                JSONArray rooms = jsonObj.getJSONArray("rooms");

                for (int i = 0; i < rooms.length(); i++) { // Creates Room for each Room in JSON data
                    JSONObject room = rooms.getJSONObject(i);
                    Room newRoom = new Room();

                    newRoom.setRoomId(room.getInt("roomId"));
                    newRoom.setRoomLocation(room.getString("location"));
                    if(room.has("unitName")) {
                        newRoom.setRoomUnitName(room.getString("unitName"));
                    }
                    newRoom.setRoomName(room.getString("roomName"));

                    JSONArray seats = room.getJSONArray("seats");

                    dbHelper.addRoom(newRoom);

                    for (int j = 0; j < seats.length(); j++) { // Creates Seat for each Seat in JSON data
                        JSONObject seat = seats.getJSONObject(j);
                        Seat seatObj = new Seat();

                        seatObj.setSeatId(seat.getInt("seatId"));
                        seatObj.setValue(seat.getInt("value"));
                        seatObj.setUnitType(seat.getString("unitType"));
                        seatObj.setRoomId(room.getInt("roomId"));

                        dbHelper.addSeat(seatObj);
                    }
                }
                debugLog(dbHelper);

            }
            dbHelper.close();
        } catch (final JSONException e) {
            Log.e(TAG, "JSON parsing error: " + e.getMessage());
        }
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
