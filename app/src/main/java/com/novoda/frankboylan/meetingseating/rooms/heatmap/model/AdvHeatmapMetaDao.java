package com.novoda.frankboylan.meetingseating.rooms.heatmap.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface AdvHeatmapMetaDao {

    @Query("SELECT lastUpdateTimestamp FROM advheatmapmeta")
    String getLastUpdateTimestamp();

    @Insert
    void insertLastUpdateTimestamp(String lastUpdateTimestamp);

    @Update
    void updateLastUpdateTimestamp(String lastUpdateTimestamp);

    @Query("SELECT rooms FROM advheatmapmeta")
    List<AdvHeatmapRoom> getRoomList();

    @Insert
    void insertRoomList(List<AdvHeatmapRoom> roomList);

    @Update
    void updateRoomList(List<AdvHeatmapRoom> roomList);
}
