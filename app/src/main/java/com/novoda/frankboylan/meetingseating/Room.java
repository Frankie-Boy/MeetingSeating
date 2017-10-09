package com.novoda.frankboylan.meetingseating;

class Room {
    private String roomName;
    private int roomId;
    private String roomLocation;
    private String roomUnitName;

    void setRoomName(String roomName) {
        this.roomName = roomName;
    }
    String getRoomName() {
        return roomName;
    }
    void setRoomId(int roomId) {
        this.roomId = roomId;
    }
    int getRoomId() {
        return roomId;
    }
    void setRoomLocation(String roomLocation) {
        this.roomLocation = roomLocation;
    }
    String getRoomLocation() {
        return roomLocation;
    }
    void setRoomUnitName(String roomUnitName) {
        this.roomUnitName = roomUnitName;
    }
    String getRoomUnitName() {
        return roomUnitName;
    }
    @Override
    public String toString() {
        return getRoomId() + ":  " + getRoomName() + " in " + getRoomLocation() + getRoomUnitName();
    }
}
