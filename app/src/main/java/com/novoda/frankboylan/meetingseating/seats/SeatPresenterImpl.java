package com.novoda.frankboylan.meetingseating.seats;

import android.widget.LinearLayout;
import android.widget.Switch;

import com.novoda.frankboylan.meetingseating.Room;

import java.util.ArrayList;
import java.util.List;

class SeatPresenterImpl implements SeatPresenter {
    private static final String TAG = "SeatPresenter";
    private SeatModel model;
    private SeatDisplayer displayer;
    private List<Seat> seatList, cachedSeatList;
    private LinearLayout linearLayoutSeats, linearLayoutRooms;

    SeatPresenterImpl(SeatDisplayer displayer, SeatModel model) {
        this.displayer = displayer;
        this.model = model;
        seatList = new ArrayList<>();
        cachedSeatList = new ArrayList<>();
    }

    public void createAndFillLists() {
        cachedSeatList = model.getCachedList();
        if (cachedSeatList.isEmpty()) {     // There's no cached data, so load new data.
            seatList = model.getAllSeats();
        } else {
            seatList.clear();
            seatList.addAll(cachedSeatList);
            model.clearSeatCache();
        }
    }

    @Override
    public void setLinearLayouts(LinearLayout l1, LinearLayout l2) {
        linearLayoutRooms = l1;
        linearLayoutSeats = l2;
    }

    @Override
    public void resetAllSwitch() {
        for (int i = 0; i < linearLayoutSeats.getChildCount(); i++) {
            Switch button = (Switch) linearLayoutSeats.getChildAt(i);
            button.setChecked(true);
        }
        for (int i = 0; i < linearLayoutRooms.getChildCount(); i++) {
            Switch button = (Switch) linearLayoutRooms.getChildAt(i);
            button.setChecked(true);
        }
    }

    @Override
    public void onRefresh() {
        displayer.showToast("Fetching Data");
        seatList.clear();
        fillSeatListFromDB();
        displayer.updateSeatList(seatList);
        resetAllSwitch();
    }

    @Override
    public void updateAllLists() {
        List<Seat> cachedSeatList = model.getCachedList();
        if (cachedSeatList.isEmpty()) {     // There's no cached data, so load new data.
            createAndFillLists();
            return;
        }
        seatList.clear();
        seatList.addAll(cachedSeatList);
    }

    public void fillSeatListFromDB() {
        seatList = model.getAllSeats();
    }

    public void uncheckSeatsWithMatchingId(int roomId) {
        for (int i = 0; i < linearLayoutSeats.getChildCount(); i++) {
            Switch button = (Switch) linearLayoutSeats.getChildAt(i);
            Seat seat = (Seat) button.getTag();
            if (seat.getRoomId().equals(roomId)) {
                button.setChecked(false);
            }
        }
    }

    public void checkSeatsWithMatchingId(int roomId) {
        for (int i = 0; i < linearLayoutSeats.getChildCount(); i++) {
            Switch button = (Switch) linearLayoutSeats.getChildAt(i);
            Seat seat = (Seat) button.getTag();
            if (seat.getRoomId().equals(roomId)) {
                button.setChecked(true);
            }
        }
    }

    @Override
    public void bind(SeatDisplayer displayer) {
        this.displayer = displayer;
    }

    @Override
    public void unbind() {
        displayer = null;
    }


    @Override
    public void fillFilterView() {
        for (Room room : model.getAllRooms()) {
            displayer.addRoomSwitchElement(room);
        }
        for (Seat seat : model.getAllSeats()) {
            displayer.addSeatSwitchElement(seat);
        }
    }

    @Override
    public void onApplyFilter() {
        seatList.clear();
        model.clearSeatCache(); // clear the old cache
        for (int i = 0; i < linearLayoutSeats.getChildCount(); i++) {
            Switch button = (Switch) linearLayoutSeats.getChildAt(i);
            if (button.isChecked()) {
                seatList.add((Seat) button.getTag());
            }
        }

        // Makes a backup of seatList in the SQLite DB (SEAT_CACHE_TABLE)
        for (Seat seat : seatList) {
            model.addSeatToCache(seat);
        }

        displayer.updateSeatList(seatList);
    }

    public List<Seat> getSeatList() {
        return seatList;
    }

    void onFilterPressed() {
        List<Seat> cachedSeatList = model.getCachedList();
        displayer.updateSwitchList(cachedSeatList);
    }
}
