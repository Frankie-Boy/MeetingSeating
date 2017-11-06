package com.novoda.frankboylan.meetingseating.rooms.heatmap.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface AdvHeatmapRoomDao {

    @Query("SELECT * FROM AdvHeatmapRoom WHERE roomId LIKE :roomId")
    AdvHeatmapRoom findByRoomId(String roomId);

    @Insert
    void insertAll(List<AdvHeatmapRoom> roomList);

    @Query("DELETE FROM advheatmaproom")
    void deleteAll();
}
