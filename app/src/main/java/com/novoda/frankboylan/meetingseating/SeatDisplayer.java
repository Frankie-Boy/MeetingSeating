package com.novoda.frankboylan.meetingseating;

import java.util.List;

interface SeatDisplayer {
    void showToast(String message);

    void addRoomSwitchElement(Room room);

    void addSeatSwitchElement(Seat seat);

    void updateSeatList(List<Seat> seatList);
}
