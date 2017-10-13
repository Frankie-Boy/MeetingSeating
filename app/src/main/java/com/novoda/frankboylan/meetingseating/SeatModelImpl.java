package com.novoda.frankboylan.meetingseating;

import java.io.IOException;

import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

class SeatModelImpl implements SeatModel {

    private AwsSeatMonitorService service;

    SeatModelImpl() {
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
}
