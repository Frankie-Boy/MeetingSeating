package com.novoda.frankboylan.meetingseating.seats;

import com.novoda.frankboylan.meetingseating.heatmap.Room;

import java.util.List;

interface SeatDisplayer {
    void showToast(String message);

    void addRoomSwitchElement(Room room);

    void addSeatSwitchElement(Seat seat);

    void updateSeatList(List<Seat> seatList);

    void updateSwitchList(List<Seat> cachedSeatList);
}
