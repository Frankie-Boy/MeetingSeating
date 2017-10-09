package com.novoda.frankboylan.meetingseating;

import com.squareup.moshi.Json;

public class Seat {

    @Json(name = "seatId")
    private Integer seatId;
    private Integer roomId;
    @Json(name = "value")
    private Integer value;
    @Json(name = "unitType")
    private String unitType;

    public Integer getSeatId() {
        return seatId;
    }

    public void setSeatId(Integer seatId) {
        this.seatId = seatId;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
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