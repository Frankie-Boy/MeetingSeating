package com.novoda.frankboylan.meetingseating.rooms.heatmap.adv.repo.model;

public interface LocalRepository {
    public void insertMeta(AdvHeatmapMeta metadata);

    public void insertRoom(AdvHeatmapRoom roomdata);

    public void insertSeat(AdvHeatmapSeat seatdata);

    public void deleteAllData();
}
