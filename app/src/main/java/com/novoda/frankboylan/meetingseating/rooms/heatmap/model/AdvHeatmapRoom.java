package com.novoda.frankboylan.meetingseating.rooms.heatmap.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;

@Entity
class AdvHeatmapRoom {

    @PrimaryKey
    private int roomId;

    @ColumnInfo(name = "location")
    private String location;

    @ColumnInfo(name = "unitName")
    private String unitName;

    @ColumnInfo(name = "roomName")
    private String roomName;

    @ColumnInfo(name = "sizeX")
    private int sizeX;

    @ColumnInfo(name = "sizeY")
    private int sizeY;

    @ColumnInfo(name = "heatUnit")
    private String heatUnit;

    @ColumnInfo(name = "seats")
    private List<AdvHeatmapSeat> seatList;

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

    public String getHeatUnit() {
        return heatUnit;
    }

    public void setHeatUnit(String heatUnit) {
        this.heatUnit = heatUnit;
    }

    public List<AdvHeatmapSeat> getSeatList() {
        return seatList;
    }

    public void setSeatList(List<AdvHeatmapSeat> seatList) {
        this.seatList = seatList;
    }

    public void updateSeatListId() { // ToDo: use this
        for (AdvHeatmapSeat seat : seatList) {
            seat.setRoomId(roomId);
        }
    }
}
