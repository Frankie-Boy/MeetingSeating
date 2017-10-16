package com.novoda.frankboylan.meetingseating;

import retrofit2.Response;

public interface SeatModel {
    Response<RoomSeatData> retrieveData();

    void execSeatDataRetrievalTask();
}
