package com.novoda.frankboylan.meetingseating.seats;

import android.util.Log;

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

    SeatPresenterImpl(SeatModel model) {
        this.model = model;
        seatList = new ArrayList<>();
    }

    @Override
    public void onRefresh() {
        updateSeatList(model.getAllSeats());
    }

    private void updateSeatList(List<Seat> allSeats) {
        this.seatList.clear();
        this.seatList = allSeats;
        displayer.resetAllSwitch();
        displayer.updateSeatList(this.seatList);
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
    public void onApplyFilter(List<Seat> seatList) {
        this.seatList.clear();
        model.clearSeatCache(); // clear the old cache
        model.setMetaCacheToActive();
        for (Seat seat : seatList) {
            this.seatList.add(seat);
            model.addSeatToCache(seat);
        }
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
        seatList.clear();
        Log.d("HERE1:", model.isCacheActive() + "");
        if (model.isCacheActive()) {
            Log.d("HERE: ", " READING CACHE");
            seatList.addAll(model.getCachedList());
            displayer.updateSeatList(seatList);
            return;
        }
        seatList = model.getAllSeats();
        displayer.updateSeatList(seatList);
    }

    @Override
    public void onSeatModelChanged(List<Seat> seatList) {
        updateSeatList(seatList);
    }
}
