package com.novoda.frankboylan.meetingseating;

import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

class SeatPresenterImpl implements SeatPresenter {
    private static final String TAG = "SeatPresenter";
    private SeatModel model;
    private SeatDisplayer displayer;
    private List<Seat> seatList, seatListFiltered, seatListFull;
    private List<Room> roomListFull;

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
        seatListFiltered.addAll(seatList);

        roomListFull = new ArrayList<>();
        fillRoomListFromDB();
    }

    @Override
    public void updateSwitchUI(LinearLayout llSeatsExpandableContent) {
        for (int i = 0; i < llSeatsExpandableContent.getChildCount(); i++) {
            Boolean found = false;
            Switch button = (Switch) llSeatsExpandableContent.getChildAt(i);
            Seat seatTag = (Seat) button.getTag();

            for (Seat seat : seatListFiltered) {
                if (Objects.equals(seat.getRoomId(), seatTag.getRoomId())) {
                    button.setChecked(true);
                    found = true;
                    break;
                }
            }
            if (!found) {
                button.setChecked(false);
            }
        }
    }

    public void onRefresh() {
        model.execSeatDataRetrievalTask();
        fillSeatListFromDB();
        displayer.showToast("Fetching Data");
        displayer.updateSeatList();
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

    public void removeSeatsWithMatchingId(int roomId) {
        Iterator<Seat> i = seatListFiltered.iterator();
        while (i.hasNext()) {
            Seat seat = i.next();
            if (seat.getRoomId().equals(roomId)) {
                i.remove();
            }
        }
        displayer.updateSeatList();
    }

    public void addSeatsWithMatchingId(int roomId) {
        seatListFiltered.addAll(model.getSeatsWithMatchingId(roomId));
        displayer.updateSeatList();
    }

    public void bind(SeatDisplayer displayer) {
        this.displayer = displayer;
    }

    public void unbind() {
        displayer = null;
    }

    public void clearAndFillSeatListFilter() {
        seatListFiltered.clear();
        seatListFiltered.addAll(seatList);
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
    public void onApplyFitler() {
        Log.d(TAG, "seatList: " + seatList);
        Log.d(TAG, "seatListFiltered: " + seatListFiltered);
        seatList.clear();
        seatList.addAll(seatListFiltered);
        seatListFiltered.clear();
        displayer.updateSeatList();
    }
}
