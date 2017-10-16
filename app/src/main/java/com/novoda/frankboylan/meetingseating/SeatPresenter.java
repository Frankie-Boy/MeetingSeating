package com.novoda.frankboylan.meetingseating;

import android.widget.LinearLayout;

import java.util.List;

interface SeatPresenter {
    void bind(SeatDisplayer displayer);

    void unbind();

    void onRefresh();

    void updateSwitchUI(LinearLayout linearLayout);

    void resetAllSwitch(LinearLayout l1, LinearLayout l2);

    void fillSeatListFromDB();

    void fillRoomListFromDB();

    void removeSeatsWithMatchingId(int roomId);

    void addSeatsWithMatchingId(int roomId);

    void createAndFillLists();

    void clearAndFillSeatListFilter();

    void fillFilterView();

    void onApplyFitler();

    List<Seat> getSeatList();
}
