package com.novoda.frankboylan.meetingseating.rooms.heatmap;

import android.util.Log;

import com.novoda.frankboylan.meetingseating.AwsSeatMonitorService;
import com.novoda.frankboylan.meetingseating.HeatmapSeatData;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class HeatmapSeatListModelImpl implements HeatmapSeatListModel {
    private AwsSeatMonitorService service;

    HeatmapSeatListModelImpl() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AwsSeatMonitorService.HEATMAP_BASE)
                .addConverterFactory(MoshiConverterFactory.create())
                .client(new OkHttpClient())
                .build();
        service = retrofit.create(AwsSeatMonitorService.class);
    }

    @Override
    public void retrieveData() {
        service.seatHeatmapData().enqueue(new Callback<HeatmapSeatData>() {
            @Override
            public void onResponse(Call<HeatmapSeatData> call, Response<HeatmapSeatData> response) {
                if (response.isSuccessful()) {
                    Log.d("HeatmapSeatListModel", response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<HeatmapSeatData> call, Throwable t) {

            }
        });
    }
}
