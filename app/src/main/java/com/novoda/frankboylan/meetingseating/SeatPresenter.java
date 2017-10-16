package com.novoda.frankboylan.meetingseating;

interface SeatPresenter {
    void bind(SeatDisplayer displayer);

    void unbind();

    void onRefresh();

    void onResume();

    void onDestroy();

    void startPresenting();

}
