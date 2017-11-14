package com.novoda.frankboylan.meetingseating;

import com.novoda.frankboylan.meetingseating.rooms.heatmap.HeatmapSeatData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AwsSeatMonitorService {
    String BASE = "https://j52dra0rwi.execute-api.eu-west-1.amazonaws.com/";
    String ENV = "debug/";
    String SEAT_MONITOR = "debug-master-seatdata";
    String HEATMAP = "debug-master-heatmap?";

    @GET(ENV + SEAT_MONITOR)
    Call<RoomSeatData> seatMonitorData();

    @GET(ENV + HEATMAP)
    Call<HeatmapSeatData> seatHeatmapData(@Query("roomId") String roomId);
}
