package com.novoda.frankboylan.meetingseating;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

class SeatModelImpl implements SeatModel {

    private AwsSeatMonitorService service;
    private SQLiteDataManagement sqliteDataManagement;
    private SQLiteDataDefinition sqliteDataDefinition;

    SeatModelImpl(SQLiteDataDefinition sqliteDataDefinition, SQLiteDataManagement sqliteDataManagement) {
        this.sqliteDataManagement = sqliteDataManagement;
        this.sqliteDataDefinition = sqliteDataDefinition;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AwsSeatMonitorService.BASE)
                .addConverterFactory(MoshiConverterFactory.create())
                .client(new OkHttpClient())
                .build();
        service = retrofit.create(AwsSeatMonitorService.class);
    }

    @Override
    public Response<RoomSeatData> retrieveData() {
        try {
            return service.seatMonitorData().execute();
        } catch (IOException e) {
            throw new IllegalStateException(e); // response throws an IOException when devices wifi is offline.
        }
    }

    @Override
    public void execSeatDataRetrievalTask() {
        SeatDataRetrievalTask task = new SeatDataRetrievalTask(sqliteDataManagement, sqliteDataDefinition);
        task.execute();
    }

    public List<Seat> getAllSeats() {
        return sqliteDataManagement.getAllSeats();
    }

    public List<Room> getAllRooms() {
        return sqliteDataManagement.getAllRooms();
    }

    @Override
    public void addSeatToCache(Seat seat) {
        sqliteDataManagement.addSeatToCache(seat);
    }

    @Override
    public void clearSeatCache() {
        sqliteDataManagement.clearSeatCache();
    }

    @Override
    public List<Seat> getCachedList() {
        return sqliteDataManagement.getCachedList();
    }

}
