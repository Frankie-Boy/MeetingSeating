package com.novoda.frankboylan.meetingseating.rooms.heatmap.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

@Dao
public interface AdvHeatmapMetaDao {

    @Query("SELECT latest_timestamp FROM metadata")
    String getLatestTimestamp();

    @Update
    void updateMetaData(AdvHeatmapMeta metadata);

    @Query("SELECT heat_unit FROM metadata")
    String getHeatUnit();
}
