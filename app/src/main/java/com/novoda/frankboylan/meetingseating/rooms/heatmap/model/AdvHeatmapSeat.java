package com.novoda.frankboylan.meetingseating.rooms.heatmap.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "seats",
        foreignKeys = @ForeignKey(
                onDelete = CASCADE,
                entity = AdvHeatmapRoom.class,
                parentColumns = "room_id",
                childColumns = "room_id"))
class AdvHeatmapSeat {
    @PrimaryKey
    private int seatId;
    private int roomId;

    @ColumnInfo(name = "heat_value")
    private int heatValue;

    @ColumnInfo(name = "pos_x")
    private int posX;

    @ColumnInfo(name = "pos_y")
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

    public int getHeatValue() {
        return heatValue;
    }

    public void setHeatValue(int heatValue) {
        this.heatValue = heatValue;
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
