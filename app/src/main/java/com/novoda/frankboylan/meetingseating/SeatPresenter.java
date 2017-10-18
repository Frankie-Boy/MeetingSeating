package com.novoda.frankboylan.meetingseating;

import android.widget.LinearLayout;

import java.util.List;

interface SeatPresenter {
    void bind(SeatDisplayer displayer);

    void unbind();

    void onRefresh();

    void resetAllSwitch();

    void fillSeatListFromDB();

    void fillRoomListFromDB();

    void uncheckSeatsWithMatchingId(int roomId);

    void checkSeatsWithMatchingId(int roomId);

    void createAndFillLists();

    void setLinearLayouts(LinearLayout l1, LinearLayout l2);

    void fillFilterView();

    void onApplyFilter();

    void updateCachedList();

    List<Seat> getSeatList();
}
