package com.novoda.frankboylan.meetingseating.rooms.heatmap;

interface HeatMapSeatListPresenter {
    void bind(HeatmapSeatListDisplayer displayer);

    void unbind();

    void startPresenting();

    void getData();
}
