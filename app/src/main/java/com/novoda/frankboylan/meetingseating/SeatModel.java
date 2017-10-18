package com.novoda.frankboylan.meetingseating;

import java.util.List;

import retrofit2.Response;

interface SeatModel {
    Response<RoomSeatData> retrieveData();

    void execSeatDataRetrievalTask();

    List<Seat> getAllSeats();

    List<Room> getAllRooms();

    void addSeatToCache(Seat seat);

    void clearSeatCache();

    List<Seat> getCachedList();
}
