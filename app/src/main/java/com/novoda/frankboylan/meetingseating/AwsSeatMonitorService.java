package com.novoda.frankboylan.meetingseating;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AwsSeatMonitorService {
    String BASE = "https://f8v3dmak5d.execute-api.eu-west-1.amazonaws.com/";
    String ENV = "prod/";
    String SEAT_MONITOR = "seat-monitor-data/";
    String HEATMAP_BASE = "https://w62twg41g1.execute-api.eu-west-1.amazonaws.com/";
    String HEATMAP = "seat-heat-map-data?roomId=234&start=1234&end=4321";

    @GET(AwsSeatMonitorService.ENV + AwsSeatMonitorService.SEAT_MONITOR)
    Call<RoomSeatData> seatMonitorData();

    @GET(AwsSeatMonitorService.ENV + AwsSeatMonitorService.HEATMAP)
    Call<HeatmapSeatData> seatHeatmapData();
}
