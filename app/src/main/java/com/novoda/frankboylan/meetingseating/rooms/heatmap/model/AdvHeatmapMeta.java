package com.novoda.frankboylan.meetingseating.rooms.heatmap.model;

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

    public String getLastUpdateTimestamp() {
        return lastUpdateTimestamp;
    }

    public void setLastUpdateTimestamp(String lastUpdateTimestamp) {
        this.lastUpdateTimestamp = lastUpdateTimestamp;
    }

    public String getHeatUnit() {
        return heatUnit;
    }

    public void setHeatUnit(String heatUnit) {
        this.heatUnit = heatUnit;
    }
}
