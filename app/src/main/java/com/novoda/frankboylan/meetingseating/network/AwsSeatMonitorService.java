package com.novoda.frankboylan.meetingseating.network;

import com.novoda.frankboylan.meetingseating.RoomSeatData;
import com.novoda.frankboylan.meetingseating.rooms.heatmap.HeatmapSeatData;
import com.novoda.frankboylan.meetingseating.rooms.heatmap.adv.repo.model.AdvHeatmapMeta;
import com.novoda.frankboylan.meetingseating.rooms.heatmap.adv.repo.model.AdvHeatmapRoom;
import com.novoda.frankboylan.meetingseating.rooms.heatmap.adv.repo.model.AdvHeatmapSeat;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AwsSeatMonitorService {
    String BASE = "https://f8v3dmak5d.execute-api.eu-west-1.amazonaws.com/";
    String ENV = "prod/";
    String SEAT_MONITOR = "seat-monitor-data/";

    String HEATMAP_BASE = "https://w62twg41g1.execute-api.eu-west-1.amazonaws.com/";
    String HEATMAP = "seat-heat-map-data?";

    @GET(ENV + SEAT_MONITOR)
    Call<RoomSeatData> seatMonitorData();

    @GET(ENV + HEATMAP)
    Call<HeatmapSeatData> seatHeatmapData(@Query("roomId") String roomId,
                                          @Query("start") int start,
                                          @Query("end") int end);

    @GET(ENV + SEAT_MONITOR)
    Observable<AdvHeatmapMeta> advMetadata();

    @GET(ENV + SEAT_MONITOR)
    Observable<AdvHeatmapRoom> advBase();

    @GET(ENV + SEAT_MONITOR)
    Observable<AdvHeatmapSeat> advHeatmapData();

}
