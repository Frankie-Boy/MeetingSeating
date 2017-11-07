package com.novoda.frankboylan.meetingseating.rooms.heatmap.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {AdvHeatmapMeta.class}, version = 1)
public abstract class InternalDatabase extends RoomDatabase {
    public abstract AdvHeatmapMetaDao metaDao();
}
