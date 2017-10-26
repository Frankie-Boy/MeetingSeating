package com.novoda.frankboylan.meetingseating.seats;

import com.novoda.frankboylan.meetingseating.Room;
import com.novoda.frankboylan.meetingseating.RoomSeatData;

import java.util.List;

import retrofit2.Response;

interface SeatModel {
    Response<RoomSeatData> retrieveData();

    List<Seat> getAllSeats();

    List<Room> getAllRooms();

    void addSeatToCache(Seat seat);

    void clearSeatCache();

    List<Seat> getCachedList();
}
