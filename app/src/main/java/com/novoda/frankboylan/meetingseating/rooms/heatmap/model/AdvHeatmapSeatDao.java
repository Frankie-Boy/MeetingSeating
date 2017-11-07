package com.novoda.frankboylan.meetingseating.rooms.heatmap.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface AdvHeatmapSeatDao {

    @Insert
    void insertAllSeats(AdvHeatmapSeat... seats);

    @Query("DELETE FROM seats")
    void deleteAllSeats();

    @Query("SELECT * FROM seats")
    List<AdvHeatmapSeat> getAllSeats();

    @Query("SELECT * FROM seats WHERE roomId LIKE :roomId")
    List<AdvHeatmapSeat> getAllSeatsWithMatchingRoomId(String roomId);
}
