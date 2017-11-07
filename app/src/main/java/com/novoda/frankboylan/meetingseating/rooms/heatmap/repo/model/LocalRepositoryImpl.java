package com.novoda.frankboylan.meetingseating.rooms.heatmap.repo.model;

import java.util.concurrent.Executor;

public class LocalRepositoryImpl implements LocalRepository {
    private AdvHeatmapMetaDao metaDao;
    private AdvHeatmapRoomDao roomDao;
    private AdvHeatmapSeatDao seatDao;
    private Executor executor;

    public LocalRepositoryImpl(AdvHeatmapMetaDao metaDao, AdvHeatmapRoomDao roomDao, AdvHeatmapSeatDao seatDao, Executor executor) {
        this.metaDao = metaDao;
        this.roomDao = roomDao;
        this.seatDao = seatDao;
        this.executor = executor;
    }

    @Override
    public void insertMeta(final AdvHeatmapMeta metadata) {
        executor.execute(() -> {
            metaDao.updateMetaData(metadata);
        });
    }

    @Override
    public void insertRoom(AdvHeatmapRoom roomdata) {
        executor.execute(() -> {
            roomDao.insertRoom(roomdata);
        });
    }

    @Override
    public void insertSeat(AdvHeatmapSeat seatdata) {
        executor.execute(() -> {
            seatDao.insertSeat(seatdata);
        });
    }

    @Override
    public void deleteAllData() {
        executor.execute(() -> {
            metaDao.deleteAllMetadata();
            roomDao.deleteAllRooms();
            seatDao.deleteAllSeats();
        });
    }
}
