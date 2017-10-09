package com.novoda.frankboylan.meetingseating;

import com.squareup.moshi.Json;

import java.util.ArrayList;
import java.util.List;

class RoomSeatData {
    @Json(name = "lastUpdateTimestamp")
    String lastUpdateTimestamp;
    @Json(name = "rooms")
    List<Room> rooms = new ArrayList<>();

    public String getLastUpdateTimestamp() {
        return lastUpdateTimestamp;
    }

    public List<Room> getRooms() {
        return rooms;
    }

}
