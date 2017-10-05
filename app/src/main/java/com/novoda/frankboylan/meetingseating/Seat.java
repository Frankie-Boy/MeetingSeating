package com.novoda.frankboylan.meetingseating;

public class Seat {
    private int seatId, value;
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

    @Override
    public String toString() {
        return getSeatId() + ":  " + getValue() + getUnitType();
    }
}
