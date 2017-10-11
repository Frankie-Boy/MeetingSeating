package com.novoda.frankboylan.meetingseating;

import android.content.Context;

import java.util.List;

class SeatListController {
    private SqliteDML sqliteDML;

    SeatListController(Context context){
        sqliteDML = new SqliteDML(context);
    }
    List<Seat> getAllSeats() {
        return sqliteDML.getAllSeats();
    }
}
