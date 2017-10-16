package com.novoda.frankboylan.meetingseating;

import java.util.List;

public class SeatPresenterImpl implements SeatPresenter {
    private SeatModel model;
    private SeatDisplayer displayer;
    private final SeatListController seatListController;

    public SeatPresenterImpl(SeatListController seatListController, SeatDisplayer displayer, SeatModel model) {
        this.seatListController = seatListController;
        this.displayer = displayer;
        this.model = model;
    }

    public void startPresenting() {

    }

    public void onRefresh() {
        model.execSeatDataRetrievalTask();
        List<Seat> seatList = seatListController.getAllSeats();
        displayer.showFetchingDataToast();
        displayer.showSeats(seatList);
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

    public void bind(SeatDisplayer displayer) {
        this.displayer = displayer;
    }

    public void unbind() {
        displayer = null;
    }
}
