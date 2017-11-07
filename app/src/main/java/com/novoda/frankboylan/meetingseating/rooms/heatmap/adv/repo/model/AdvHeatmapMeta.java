package com.novoda.frankboylan.meetingseating.rooms.heatmap.adv.repo.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "metadata")
public class AdvHeatmapMeta {

    @PrimaryKey
    @ColumnInfo(name = "latest_timestamp")
    @NonNull
    private String lastUpdateTimestamp = "0L";

    @ColumnInfo(name = "heat_unit")
    private String heatUnit = "%";

    String getLastUpdateTimestamp() {
        return lastUpdateTimestamp;
    }

    void setLastUpdateTimestamp(String lastUpdateTimestamp) {
        this.lastUpdateTimestamp = lastUpdateTimestamp;
    }

    String getHeatUnit() {
        return heatUnit;
    }

    void setHeatUnit(String heatUnit) {
        this.heatUnit = heatUnit;
    }
}
