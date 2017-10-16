package com.novoda.frankboylan.meetingseating;

interface SeatDisplayer {
    void showToast(String message);

    void addRoomSwitchElement(Room room);

    void addSeatSwitchElement(Seat seat);

    void updateSeatList();
}
