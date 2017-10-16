package com.novoda.frankboylan.meetingseating;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;

class SwitchItemOnClickListener implements Switch.OnClickListener {
    private static final String TAG = "SwitchItemOnClickListen";
    private Switch newSwitch;
    private SeatPresenter presenter;
    private LinearLayout layout;

    SwitchItemOnClickListener(Switch newSwitch, SeatPresenter presenter, LinearLayout layout) {
        this.newSwitch = newSwitch;
        this.presenter = presenter;
        this.layout = layout;
    }

    @Override
    public void onClick(View v) {
        if (newSwitch.getTag().equals("Room")) {
            if (newSwitch.isChecked()) {
                // Room Switch was just checked - Add all Seats with matching roomId to seatList
                presenter.addSeatsWithMatchingId(newSwitch.getId());

            } else if (!newSwitch.isChecked()) {
                // Room Switch was just unchecked - Remove all Seats with matching roomId from seatList
                presenter.removeSeatsWithMatchingId(newSwitch.getId());
                // Makes each Seat Switch with matching roomId turn off
            }
            presenter.updateSwitchUI(layout);
        } else if (newSwitch.getTag().equals("Seat")) {
            if (newSwitch.isChecked()) {
                // Seat Switch was just checked -  if Switch's with matching roomId are also checked, toggle Room Switch with matching roomId
                return;
            }
            // Seat Switch was just unchecked - Un-check Room Switch with matching roomId

        }
    }
}
