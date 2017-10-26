package com.novoda.frankboylan.meetingseating.seats;

import com.novoda.frankboylan.meetingseating.AwsSeatMonitorService;
import com.novoda.frankboylan.meetingseating.Room;
import com.novoda.frankboylan.meetingseating.RoomSeatData;
import com.novoda.frankboylan.meetingseating.SQLiteDataManagement.SQLiteDelete;
import com.novoda.frankboylan.meetingseating.SQLiteDataManagement.SQLiteInsert;
import com.novoda.frankboylan.meetingseating.SQLiteDataManagement.SQLiteRead;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class SeatModelImpl implements SeatModel {

    private AwsSeatMonitorService service;
    private SQLiteRead sqliteRead;
    private SQLiteDelete sqliteDelete;
    private SQLiteInsert sqliteInsert;

    SeatModelImpl(SQLiteRead sqliteRead, SQLiteDelete sqliteDelete, SQLiteInsert sqliteInsert) {
        this.sqliteRead = sqliteRead;
        this.sqliteDelete = sqliteDelete;
        this.sqliteInsert = sqliteInsert;
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
            throw new IllegalStateException(e);
        }
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
