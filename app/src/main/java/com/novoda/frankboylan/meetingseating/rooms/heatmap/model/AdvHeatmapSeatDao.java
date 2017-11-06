package com.novoda.frankboylan.meetingseating.rooms.heatmap.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface AdvHeatmapSeatDao {

    @Query("SELECT * FROM AdvHeatmapSeat")
    List<AdvHeatmapSeat> getAllSeats();

    @Query("SELECT * FROM AdvHeatmapSeat WHERE roomId LIKE :roomId")
    List<AdvHeatmapSeat> getAllSeatsWithMatchingRoomId(String roomId);

    @Insert
    void insertAll(List<AdvHeatmapSeat> seatList);

}
