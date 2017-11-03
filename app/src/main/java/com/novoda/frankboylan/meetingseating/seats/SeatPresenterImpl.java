package com.novoda.frankboylan.meetingseating.seats;

import android.widget.LinearLayout;
import android.widget.Switch;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.novoda.frankboylan.meetingseating.ConnectionStatus;
import com.novoda.frankboylan.meetingseating.rooms.Room;
import com.novoda.frankboylan.meetingseating.seats.model.SeatModel;

import java.util.ArrayList;
import java.util.List;

public class SeatPresenterImpl implements SeatPresenter, SeatModel.SeatModelListener {
    private SeatModel model;
    private SeatDisplayer displayer;
    private List<Seat> seatList;
    private LinearLayout linearLayoutSeats, linearLayoutRooms;

    SeatPresenterImpl(SeatModel model) {
        this.model = model;
        seatList = new ArrayList<>();
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
        updateSeatList(model.getAllSeats());
    }

    private void updateSeatList(List<Seat> allSeats) {
        this.seatList.clear();
        this.seatList = allSeats;
        resetAllSwitch();
        displayer.updateSeatList(this.seatList);
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

    @Override
    public void startPresenting() {
        if (ConnectionStatus.hasActiveInternetConnection()) {
            model.retrieveData(this);
            DatabaseReference firebaseDb = FirebaseDatabase.getInstance().getReference();
            firebaseDb.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    displayer.updateGreeting(dataSnapshot);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        List<Seat> cachedSeatList = model.getCachedList();
        if (cachedSeatList.isEmpty()) {     // There's no cached data, so load new data.
            seatList = model.getAllSeats();
            displayer.updateSeatList(seatList);
            return;
        }
        seatList.clear();
        seatList.addAll(cachedSeatList);
        displayer.updateSeatList(seatList);
    }

    void onFilterPressed() {
        List<Seat> cachedSeatList = model.getCachedList();
        displayer.updateSwitchList(cachedSeatList);
    }

    @Override
    public void onSeatModelChanged(List<Seat> seatList) {
        updateSeatList(seatList);
    }
}
