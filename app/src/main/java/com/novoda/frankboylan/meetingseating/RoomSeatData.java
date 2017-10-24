package com.novoda.frankboylan.meetingseating;

import com.squareup.moshi.Json;

import java.util.ArrayList;
import java.util.List;

class RoomSeatData {
    @Json(name = "lastUpdateTimestamp")
    private String lastUpdateTimestamp;
    @Json(name = "rooms")
    private List<Room> rooms = new ArrayList<>();

    String getLastUpdateTimestamp() {
        return lastUpdateTimestamp;
    }

    public List<Room> getRooms() {
        return rooms;
    }

}
