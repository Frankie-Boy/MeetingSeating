package com.novoda.frankboylan.meetingseating.seats.model;

import com.novoda.frankboylan.meetingseating.AwsSeatMonitorService;
import com.novoda.frankboylan.meetingseating.Room;
import com.novoda.frankboylan.meetingseating.RoomSeatData;
import com.novoda.frankboylan.meetingseating.SQLiteDataManagement.SQLiteDelete;
import com.novoda.frankboylan.meetingseating.SQLiteDataManagement.SQLiteInsert;
import com.novoda.frankboylan.meetingseating.SQLiteDataManagement.SQLiteRead;
import com.novoda.frankboylan.meetingseating.seats.Seat;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

class SeatModelImpl implements SeatModel {

    private AwsSeatMonitorService service;
    private SQLiteRead sqliteRead;
    private SQLiteDelete sqliteDelete;
    private SQLiteInsert sqliteInsert;
    private SeatDataRetrievalTask seatDataRetrievalTask;

    SeatModelImpl(SQLiteRead sqliteRead, SQLiteDelete sqliteDelete, SQLiteInsert sqliteInsert, SeatDataRetrievalTask seatDataRetrievalTask) {
        this.sqliteRead = sqliteRead;
        this.sqliteDelete = sqliteDelete;
        this.sqliteInsert = sqliteInsert;
        this.seatDataRetrievalTask = seatDataRetrievalTask;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AwsSeatMonitorService.BASE)
                .addConverterFactory(MoshiConverterFactory.create())
                .client(new OkHttpClient())
                .build();
        service = retrofit.create(AwsSeatMonitorService.class);
    }

    @Override
    public void retrieveData() {
        service.seatMonitorData().enqueue(new Callback<RoomSeatData>() {
            @Override
            public void onResponse(Call<RoomSeatData> call, Response<RoomSeatData> response) {
                RoomSeatData roomSeatData = response.body();
                long serverResponseTimestamp = 0L;

                if (response.isSuccessful() && roomSeatData != null) {
                    serverResponseTimestamp = Long.valueOf(roomSeatData.getLastUpdateTimestamp());
                }
                long databaseTimestamp = sqliteRead.getMetaTimestamp().getTimestamp();

                if (serverResponseTimestamp > databaseTimestamp) {  // Checking data's Timestamp is newer than stored version.
                    seatDataRetrievalTask.execute(roomSeatData);
                }
            }

            @Override
            public void onFailure(Call<RoomSeatData> call, Throwable t) {
                throw new IllegalStateException(t);
            }
        });
    }

    public List<Seat> getAllSeats() {
        return sqliteRead.getAllSeats();
    }

    public List<Room> getAllRooms() {
        return sqliteRead.getAllRooms();
    }

    @Override
    public void addSeatToCache(Seat seat) {
        sqliteInsert.addSeatToCache(seat);
    }

    @Override
    public void clearSeatCache() {
        sqliteDelete.clearSeatCache();
    }

    @Override
    public List<Seat> getCachedList() {
        return sqliteRead.getCachedList();
    }
}
