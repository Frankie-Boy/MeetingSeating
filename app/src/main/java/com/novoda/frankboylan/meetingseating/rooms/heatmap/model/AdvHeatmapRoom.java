package com.novoda.frankboylan.meetingseating.rooms.heatmap.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "rooms")
class AdvHeatmapRoom {

    @PrimaryKey
    @ColumnInfo(name = "room_id")
    private int roomId;

    @ColumnInfo(name = "location")
    private String location;

    @ColumnInfo(name = "unit_name")
    private String unitName;

    @ColumnInfo(name = "room_name")
    private String roomName;

    @ColumnInfo(name = "size_x")
    private int sizeX;

    @ColumnInfo(name = "size_y")
    private int sizeY;

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getSizeX() {
        return sizeX;
    }

    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    }
}
