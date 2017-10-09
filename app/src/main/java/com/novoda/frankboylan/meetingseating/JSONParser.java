package com.novoda.frankboylan.meetingseating;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class JSONParser extends AsyncTask<Void, Void, Void> {
    private static final String TAG = "JSONParser";
    private static final String URL_DATASET = "https://f8v3dmak5d.execute-api.eu-west-1.amazonaws.com/prod/seat-monitor-data"; // Data end-point URL
    public ArrayList<HashMap<String, String>> roomList = new ArrayList<>();
    private Context mContext;

    public JSONParser(Context context) {
        mContext = context;
    }

    @Override
    protected Void doInBackground(Void... params) {
        HttpHandler sh = new HttpHandler();

        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(URL_DATASET);

        Log.e(TAG, "Response from url: " + jsonStr);

        if (jsonStr != null) {
            try {
                DBHelper dbHelper = new DBHelper(mContext);
                dbHelper.clearData();   // Clear existing data

                JSONObject jsonObj = new JSONObject(jsonStr);
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
                dbHelper.close();
            } catch (final JSONException e) {
                Log.e(TAG, "JSON parsing error: " + e.getMessage());
            }
        } else {
            Log.e(TAG, "Couldn't get JSON from server.");
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Toast.makeText(mContext, "Fetching data", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        if (!roomList.isEmpty()) {
            Log.d(TAG, roomList.toString());
        }
    }
}
