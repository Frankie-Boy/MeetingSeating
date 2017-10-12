package com.novoda.frankboylan.meetingseating;

import android.content.Context;
import android.util.Log;

import java.util.Iterator;
import java.util.List;

import static com.novoda.frankboylan.meetingseating.SeatActivity.seatList;

class SeatListController {
    private static final String TAG = "SeatListController";
    private SQLiteDataManagement sqliteDataManagement;

    SeatListController(Context context) {
        sqliteDataManagement = new SQLiteDataManagement(context);
    }

    List<Seat> getAllSeats() {
        return sqliteDataManagement.getAllSeats();
    }

    List<Room> getAllRooms() {
        return sqliteDataManagement.getAllRooms();
    }

    void addSeatsWithMatchingId(int roomId) {
        List<Seat> addSeatsList = sqliteDataManagement.getSeatsWithRoomId(roomId);
        seatList.addAll(addSeatsList);
        SeatActivity.updateLists();
        Log.d(TAG, "ADDED" + seatList.toString());
    }

    void removeSeatsWithMatchingId(int roomId) {
        Iterator<Seat> i = seatList.iterator();
        while (i.hasNext()) {
            Seat seat = i.next();
            Log.d(TAG, "comparing: " + seat.toString());
            if (seat.getRoomId() == roomId) {
                i.remove();
                Log.d(TAG, "removed: " + seat.toString());
            }
        }
        SeatActivity.updateLists();
        Log.d(TAG, "AFTER REMOVED" + seatList.toString());
    }
}
