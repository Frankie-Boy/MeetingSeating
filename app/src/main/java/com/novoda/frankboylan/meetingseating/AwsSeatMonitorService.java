package com.novoda.frankboylan.meetingseating;

import retrofit2.Call;
import retrofit2.http.GET;

interface AwsSeatMonitorService {
    String BASE = "https://f8v3dmak5d.execute-api.eu-west-1.amazonaws.com/";
    String ENV = "prod/";
    String SEAT_MONITOR = "seat-monitor-data/";
    String ENDPOINT_SEAT_MONITOR = BASE + ENV + SEAT_MONITOR; // RoomSeatData end-point URL

    @GET(AwsSeatMonitorService.ENV + AwsSeatMonitorService.SEAT_MONITOR)
    Call<RoomSeatData> seatMonitorData();
}
