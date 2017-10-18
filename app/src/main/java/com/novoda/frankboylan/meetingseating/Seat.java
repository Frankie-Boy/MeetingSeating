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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Seat seat = (Seat) o;

        if (seatId != null ? !seatId.equals(seat.seatId) : seat.seatId != null) {
            return false;
        }
        if (roomId != null ? !roomId.equals(seat.roomId) : seat.roomId != null) {
            return false;
        }
        if (value != null ? !value.equals(seat.value) : seat.value != null) {
            return false;
        }
        return unitType != null ? unitType.equals(seat.unitType) : seat.unitType == null;

    }

    @Override
    public int hashCode() {
        int result = seatId != null ? seatId.hashCode() : 0;
        result = 31 * result + (roomId != null ? roomId.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (unitType != null ? unitType.hashCode() : 0);
        return result;
    }
}
