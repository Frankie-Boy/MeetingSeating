package com.novoda.frankboylan.meetingseating.rooms.heatmap.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
class AdvHeatmapSeat {
    @PrimaryKey
    private int seatId;

    @PrimaryKey
    private int roomId;

    @ColumnInfo(name = "heat")
    private int heat;

    @ColumnInfo(name = "posX")
    private int posX;

    @ColumnInfo(name = "posY")
    private int posY;

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getHeat() {
        return heat;
    }

    public void setHeat(int heat) {
        this.heat = heat;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
}
