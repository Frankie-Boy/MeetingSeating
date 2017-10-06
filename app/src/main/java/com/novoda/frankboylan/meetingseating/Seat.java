package com.novoda.frankboylan.meetingseating;

public class Seat {
    private int seatId, value, roomId;
    private String unitType;

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }
    public int getSeatId() {
        return seatId;
    }
    public void setValue(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }
    public String getUnitType() {
        return unitType;
    }
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
    public int getRoomId() {
        return roomId;
    }
    @Override
    public String toString() {
        return getSeatId() + ":  " + getValue() + getUnitType() + " RoomId: " + getRoomId();
    }
}
