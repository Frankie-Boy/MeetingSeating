package com.novoda.frankboylan.meetingseating;

import java.util.List;

public interface SeatDisplayer {
    void showFetchingDataToast();

    void showSeats(List<Seat> seatList);
}
