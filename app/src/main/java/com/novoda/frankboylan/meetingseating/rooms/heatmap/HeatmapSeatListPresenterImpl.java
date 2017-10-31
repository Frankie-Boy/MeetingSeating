package com.novoda.frankboylan.meetingseating.rooms.heatmap;

import java.util.List;

class HeatmapSeatListPresenterImpl implements HeatMapSeatListPresenter {
    private HeatmapSeatListDisplayer displayer;
    private HeatmapSeatListModel model;
    private List<HeatmapSeat> seatList;

    @Override
    public void bind(HeatmapSeatListDisplayer displayer) {
        this.displayer = displayer;
        model = new HeatmapSeatListModelImpl(this);
    }

    @Override
    public void unbind() {
        displayer = null;
    }

    @Override
    public void startPresenting() {

    }

    @Override
    public void getData(String roomId) {
        // ToDo: update Lists
        model.retrieveData(roomId);
    }

    @Override
    public void updateList(List<HeatmapSeat> seatList) {
        this.seatList = seatList;
        displayer.updateAdapter(seatList);
    }
}
