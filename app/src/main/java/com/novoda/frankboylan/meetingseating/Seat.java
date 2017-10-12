package com.novoda.frankboylan.meetingseating;

import com.squareup.moshi.Json;

class Seat {

    @Json(name = "seatId")
    private Integer seatId;
    private Integer roomId;
    @Json(name = "value")
    private Integer value;
    @Json(name = "unitType")
    private String unitType;

    Integer getSeatId() {
        return seatId;
    }

    void setSeatId(Integer seatId) {
        this.seatId = seatId;
    }

    Integer getValue() {
        return value;
    }

    void setValue(Integer value) {
        this.value = value;
    }

    String getUnitType() {
        return unitType;
    }

    void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    Integer getRoomId() {
        return roomId;
    }

    void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "seatId=" + seatId +
                ", roomId=" + roomId +
                ", value=" + value +
                ", unitType='" + unitType + '\'' +
                '}';
    }
}
