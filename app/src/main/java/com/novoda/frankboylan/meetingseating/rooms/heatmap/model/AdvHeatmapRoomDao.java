package com.novoda.frankboylan.meetingseating.rooms.heatmap.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface AdvHeatmapRoomDao {

    @Insert
    void insertAllRooms(AdvHeatmapRoom... rooms);

    @Query("DELETE FROM rooms")
    void deleteAll();

    @Query("SELECT * FROM rooms WHERE roomId LIKE :roomId")
    List<AdvHeatmapRoom> findByRoomId(String roomId);

    @Query("SELECT * FROM rooms")
    List<AdvHeatmapRoom> getAllRooms();
}
