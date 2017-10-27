package com.novoda.frankboylan.meetingseating.rooms.heatmap;

class HeatmapSeatListPresenterImpl implements HeatMapSeatListPresenter {
    private HeatmapSeatListDisplayer displayer;
    private HeatmapSeatListModel model;

    @Override
    public void bind(HeatmapSeatListDisplayer displayer) {
        this.displayer = displayer;
    }

    @Override
    public void unbind() {
        displayer = null;
    }

    @Override
    public void startPresenting() {

    }
}
