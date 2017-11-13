package com.novoda.frankboylan.meetingseating.network;

import com.novoda.frankboylan.meetingseating.rooms.heatmap.adv.repo.model.AdvHeatmapMeta;
import com.novoda.frankboylan.meetingseating.rooms.heatmap.adv.repo.model.AdvHeatmapRoom;
import com.novoda.frankboylan.meetingseating.rooms.heatmap.adv.repo.model.AdvHeatmapSeat;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class RetrofitHelper {
    public Observable<AdvHeatmapMeta> serviceMetaData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AwsSeatMonitorService.BASE)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(new OkHttpClient())
                .build();
        AwsSeatMonitorService aws = retrofit.create(AwsSeatMonitorService.class);

        return aws.advMetadata()
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread());
    }

    public Observable<AdvHeatmapRoom> serviceRoomData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AwsSeatMonitorService.BASE)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(new OkHttpClient())
                .build();
        AwsSeatMonitorService aws = retrofit.create(AwsSeatMonitorService.class);

        return aws.advBase()
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread());
    }

    public Observable<AdvHeatmapSeat> serviceHeatmapData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AwsSeatMonitorService.BASE)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(new OkHttpClient())
                .build();
        AwsSeatMonitorService aws = retrofit.create(AwsSeatMonitorService.class);

        return aws.advHeatmapData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread());
    }
}
