package com.novoda.frankboylan.meetingseating.seats.model;

import com.novoda.frankboylan.meetingseating.Room;
import com.novoda.frankboylan.meetingseating.seats.Seat;

import java.util.List;

public interface SeatModel {
    void retrieveData();

    List<Seat> getAllSeats();

    List<Room> getAllRooms();

    void addSeatToCache(Seat seat);

    void clearSeatCache();

    List<Seat> getCachedList();
}
