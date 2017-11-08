package com.novoda.frankboylan.meetingseating.network;

import android.util.Log;

import com.novoda.frankboylan.meetingseating.rooms.heatmap.adv.repo.model.AdvHeatmapMeta;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class RetrofitHelper {
    public void createService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AwsSeatMonitorService.BASE)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(new OkHttpClient())
                .build();
        AwsSeatMonitorService aws = retrofit.create(AwsSeatMonitorService.class);
        Observable<AdvHeatmapMeta> metadata = aws.advHeatmapData();

        metadata.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(advHeatmapData -> {
                    Log.e("RetrofitHelper.java", advHeatmapData.getLastUpdateTimestamp());
                });
    }
}
