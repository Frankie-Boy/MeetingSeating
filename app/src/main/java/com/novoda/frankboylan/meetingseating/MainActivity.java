package com.novoda.frankboylan.meetingseating;

import android.content.Context;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private String[] mDrawerOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerList = (ListView)findViewById(R.id.side_drawer);

        mDrawerOptions = getResources().getStringArray(R.array.drawer_options);
        mDrawerList.setAdapter(new ArrayAdapter<>(this,
                R.layout.drawer_list_item, mDrawerOptions));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        new JSONParser().execute();

        // ToDo: Check if online, if so, parse JSON and recreate SQLite DB.
        // ToDo: Display the last time the information was updated (use lastUpdateTimestamp)
    }

    private class JSONParser extends AsyncTask<Void, Void, Void> {

        private static final String URL_DATASET = "https://f8v3dmak5d.execute-api.eu-west-1.amazonaws.com/prod/seat-monitor-data";
        public ArrayList<HashMap<String, String>> roomList = new ArrayList<>();

        @Override
        protected Void doInBackground(Void... params) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(URL_DATASET);

            Log.e("TAG_URL ", "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    DBHelper dbHelper = new DBHelper(MainActivity.this);
                    // Getting JSON Array node
                    JSONArray rooms = jsonObj.getJSONArray("rooms");

                    for (int i = 0; i < rooms.length(); i++) {
                        JSONObject room = rooms.getJSONObject(i);
                        Room newRoom = new Room();

                        newRoom.setRoomId(room.getInt("roomId"));
                        newRoom.setRoomLocation(room.getString("location"));
                        newRoom.setRoomUnitName(room.getString("unitName"));
                        newRoom.setRoomName(room.getString("roomName"));

                        JSONArray seats = room.getJSONArray("seats");

                        dbHelper.addRoom(newRoom);

                        for (int j = 0; j < seats.length(); j++) {
                            JSONObject seat = seats.getJSONObject(j);
                            Seat seatObj = new Seat();

                            seatObj.setSeatId(seat.getInt("seatId"));
                            seatObj.setValue(seat.getInt("value"));
                            seatObj.setUnitType(seat.getString("unitType"));
                            seatObj.setRoomId(room.getInt("roomId"));

                            dbHelper.addSeat(seatObj);
                        }
                    }
                    dbHelper.close();
                } catch (final JSONException e) {
                    Log.e("JSONERROR", "Json parsing error: " + e.getMessage());
                }
            } else {
                Log.e("ERRTAG", "Couldn't get json from server.");
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this,"Json Data is downloading",Toast.LENGTH_LONG).show();
            // Showing progress dialog
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if(!roomList.isEmpty()) {
                Log.d("TAGIMP", roomList.toString());
            }
        }

/*
        public String readJsonDataFromUrl(String url) throws IOException {
            String jsonDataString = null;
            InputStream inputStream = null;
            StringBuilder stringBuilder = new StringBuilder();

            try {
                inputStream = new URL(url).openStream();
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(inputStream, "UTF-8")
                );
                while ((jsonDataString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(jsonDataString);
                }
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
            }
            return new String(stringBuilder);
        }*/
    }
}
