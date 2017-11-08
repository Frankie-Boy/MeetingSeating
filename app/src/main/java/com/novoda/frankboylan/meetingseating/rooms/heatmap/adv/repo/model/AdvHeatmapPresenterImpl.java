package com.novoda.frankboylan.meetingseating.rooms.heatmap.adv.repo.model;

import android.os.AsyncTask;

import com.novoda.frankboylan.meetingseating.network.ConnectionStatus;
import com.novoda.frankboylan.meetingseating.network.RetrofitHelper;
import com.novoda.frankboylan.meetingseating.rooms.heatmap.adv.repo.AdvHeatmapDisplayer;
import com.novoda.frankboylan.meetingseating.rooms.heatmap.adv.repo.AdvHeatmapPresenter;

public class AdvHeatmapPresenterImpl implements AdvHeatmapPresenter {
    private AdvHeatmapDisplayer displayer;
    private InternalDatabase database;

    public AdvHeatmapPresenterImpl(InternalDatabase database) {
        this.database = database;
    }

    @Override
    public void bind(AdvHeatmapDisplayer displayer) {
        this.displayer = displayer;
    }

    @Override
    public void unbind() {
        displayer = null;
    }

    @Override
    public void startPresenting() {
        if (ConnectionStatus.hasActiveInternetConnection()) {
            //ToDo: check if Endpoint Timestamp > metaDao().getLatestTimestamp() THEN add replace data with new Endpoint data
            RetrofitHelper retrofit = new RetrofitHelper();
            retrofit.createService();
        }
        updateListsFromDB();
    }

    private void updateListsFromDB() {
        AsyncTask.execute(() -> {
            displayer.updateRoomList(database.roomDao().getAllRooms());
            displayer.updateSeatList(database.seatDao().getAllSeats());
            displayer.updateMetaTimestamp(database.metaDao().getLatestTimestamp());
        });
    }
}
