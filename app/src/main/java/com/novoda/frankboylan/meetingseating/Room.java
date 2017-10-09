package com.novoda.frankboylan.meetingseating;

public class Room {
    private String roomName;
    private int roomId;
    private String roomLocation;
    private String roomUnitName;

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
    public String getRoomName() {
        return roomName;
    }
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
    public int getRoomId() {
        return roomId;
    }
    public void setRoomLocation(String roomLocation) {
        this.roomLocation = roomLocation;
    }
    public String getRoomLocation() {
        return roomLocation;
    }
    public void setRoomUnitName(String roomUnitName) {
        this.roomUnitName = roomUnitName;
    }
    public String getRoomUnitName() {
        return roomUnitName;
    }
    @Override
    public String toString() {
        return getRoomId() + ":  " + getRoomName() + " in " + getRoomLocation() + getRoomUnitName();
    }
}
