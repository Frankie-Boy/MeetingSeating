package com.novoda.frankboylan.meetingseating.seats;

import android.widget.LinearLayout;

interface SeatPresenter {
    void bind(SeatDisplayer displayer);

    void unbind();

    void onRefresh();

    void resetAllSwitch();

    void fillSeatListFromDB();

    void uncheckSeatsWithMatchingId(int roomId);

    void checkSeatsWithMatchingId(int roomId);

    void setLinearLayouts(LinearLayout l1, LinearLayout l2);

    void fillFilterView();

    void onApplyFilter();

    void startPresenting();
}
