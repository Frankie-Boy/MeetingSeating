package com.novoda.frankboylan.meetingseating;

class Seat {
    private int seatId, value, roomId;
    private String unitType;

    void setSeatId(int seatId) {
        this.seatId = seatId;
    }
    int getSeatId() {
        return seatId;
    }
    void setValue(int value) {
        this.value = value;
    }
    int getValue() {
        return value;
    }
    void setUnitType(String unitType) {
        this.unitType = unitType;
    }
    String getUnitType() {
        return unitType;
    }
    void setRoomId(int roomId) {
        this.roomId = roomId;
    }
    int getRoomId() {
        return roomId;
    }
    @Override
    public String toString() {
        return getSeatId() + ":  " + getValue() + getUnitType() + " RoomId: " + getRoomId();
    }
}
