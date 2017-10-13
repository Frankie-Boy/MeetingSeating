package com.novoda.frankboylan.meetingseating;

import android.content.Context;

import java.util.Iterator;
import java.util.List;

import static com.novoda.frankboylan.meetingseating.SeatActivity.seatListFiltered;

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
        seatListFiltered.addAll(addSeatsList);
        SeatActivity.updateSeatList();
    }

    void removeSeatsWithMatchingId(int roomId) {
        Iterator<Seat> i = seatListFiltered.iterator();
        while (i.hasNext()) {
            Seat seat = i.next();
            if (seat.getRoomId().equals(roomId)) {
                i.remove();
            }
        }
        SeatActivity.updateSeatList();
    }
}
