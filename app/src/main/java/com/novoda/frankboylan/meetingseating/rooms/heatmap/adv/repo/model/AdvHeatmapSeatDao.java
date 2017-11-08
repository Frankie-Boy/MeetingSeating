package com.novoda.frankboylan.meetingseating.rooms.heatmap.adv.repo.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface AdvHeatmapSeatDao {

    @Insert
    void insertSeat(AdvHeatmapSeat seat);

    @Query("DELETE FROM seat_data")
    void deleteAllSeats();

    @Query("SELECT * FROM seat_data")
    List<AdvHeatmapSeat> getAllSeats();

    @Query("SELECT * FROM seat_data WHERE room_id LIKE :roomId")
    List<AdvHeatmapSeat> getAllSeatsWithMatchingRoomId(String roomId);
}
