package com.novoda.frankboylan.meetingseating.rooms.heatmap;

class HeatmapSeatListPresenterImpl implements HeatMapSeatListPresenter {
    private HeatmapSeatListDisplayer displayer;
    private HeatmapSeatListModel model;

    @Override
    public void bind(HeatmapSeatListDisplayer displayer) {
        this.displayer = displayer;
        model = new HeatmapSeatListModelImpl();
    }

    @Override
    public void unbind() {
        displayer = null;
    }

    @Override
    public void startPresenting() {

    }

    @Override
    public void getData() {
        // ToDo: update Lists
        model.retrieveData();
    }
}
