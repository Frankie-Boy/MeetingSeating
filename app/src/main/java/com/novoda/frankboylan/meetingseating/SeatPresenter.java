package com.novoda.frankboylan.meetingseating;

import java.util.List;

public class SeatPresenter {
    ;
    private SeatDisplayer displayer;
    private final SQLiteDataDefinition sqLiteDataDefinition;
    private final SQLiteDataManagement sqLiteDataManagement;
    private final SeatListController seatListController;

    public SeatPresenter(SQLiteDataDefinition sqLiteDataDefinition, SQLiteDataManagement sqLiteDataManagement, SeatListController seatListController) {
        this.sqLiteDataDefinition = sqLiteDataDefinition;
        this.sqLiteDataManagement = sqLiteDataManagement;
        this.seatListController = seatListController;
    }

    public void startPresenting() {

    }

    public void onRefresh() {
        SeatDataRetrievalTask task = new SeatDataRetrievalTask(sqLiteDataManagement, sqLiteDataDefinition);
        task.execute();
        List<Seat> seatList = seatListController.getAllSeats();
        displayer.showFetchingDataToast();
        displayer.showSeats(seatList);
    }

    public void bind(SeatDisplayer displayer) {
        this.displayer = displayer;
    }

    public void unbind() {
        displayer = null;
    }
}
