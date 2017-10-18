package com.novoda.frankboylan.meetingseating;

import android.widget.LinearLayout;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.List;

class SeatPresenterImpl implements SeatPresenter {
    private static final String TAG = "SeatPresenter";
    private SeatModel model;
    private SeatDisplayer displayer;
    private List<Seat> seatList, seatListFiltered, seatListFull;
    private List<Room> roomListFull;
    private LinearLayout linearLayoutSeats, linearLayoutRooms;

    SeatPresenterImpl(SeatDisplayer displayer, SeatModel model) {
        this.displayer = displayer;
        this.model = model;
    }

    public void createAndFillLists() {
        seatList = new ArrayList<>();
        fillSeatListFromDB();

        seatListFull = new ArrayList<>();
        seatListFull.addAll(seatList);

        seatListFiltered = new ArrayList<>();

        roomListFull = new ArrayList<>();
        fillRoomListFromDB();
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
        model.execSeatDataRetrievalTask();
        seatList.clear();
        fillSeatListFromDB();
        displayer.updateSeatList(seatList);
        resetAllSwitch();
    }

    public List<Seat> getSeatList() {
        return seatList;
    }

    public void fillSeatListFromDB() {
        seatList = model.getAllSeats();
    }

    public void fillRoomListFromDB() {
        roomListFull = model.getAllRooms();
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
        for (Room room : roomListFull) {
            displayer.addRoomSwitchElement(room);
        }
        for (Seat seat : seatListFull) {
            displayer.addSeatSwitchElement(seat);
        }
    }

    @Override
    public void onApplyFilter() {
        for (int i = 0; i < linearLayoutSeats.getChildCount(); i++) {
            Switch button = (Switch) linearLayoutSeats.getChildAt(i);
            if (button.isChecked()) {
                seatListFiltered.add((Seat) button.getTag());
            }
        }
        seatList.clear();
        seatList = new ArrayList<>();
        seatList.addAll(seatListFiltered);
        seatListFiltered.clear();
        displayer.updateSeatList(seatList);
    }
}
