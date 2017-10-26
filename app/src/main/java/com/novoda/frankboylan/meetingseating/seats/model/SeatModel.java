package com.novoda.frankboylan.meetingseating.seats.model;

import com.novoda.frankboylan.meetingseating.Room;
import com.novoda.frankboylan.meetingseating.RoomSeatData;
import com.novoda.frankboylan.meetingseating.seats.Seat;

import java.util.List;

import retrofit2.Response;

public interface SeatModel {
    Response<RoomSeatData> retrieveData();

    List<Seat> getAllSeats();

    List<Room> getAllRooms();

    void addSeatToCache(Seat seat);

    void clearSeatCache();

    List<Seat> getCachedList();
}
