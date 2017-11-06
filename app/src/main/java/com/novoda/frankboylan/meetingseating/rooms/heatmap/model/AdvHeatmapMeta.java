package com.novoda.frankboylan.meetingseating.rooms.heatmap.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

import java.util.List;

@Entity
public class AdvHeatmapMeta {

    @ColumnInfo(name = "lastUpdateTimestamp")
    private String lastUpdateTimestamp;

    @ColumnInfo(name = "rooms")
    private List<AdvHeatmapRoom> roomList;

    public String getLastUpdateTimestamp() {
        return lastUpdateTimestamp;
    }

    public void setLastUpdateTimestamp(String lastUpdateTimestamp) {
        this.lastUpdateTimestamp = lastUpdateTimestamp;
    }

    public List<AdvHeatmapRoom> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<AdvHeatmapRoom> roomList) {
        this.roomList = roomList;
    }
}
