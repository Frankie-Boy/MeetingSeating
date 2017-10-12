package com.novoda.frankboylan.meetingseating;

import android.content.Context;
import android.view.View;
import android.widget.Switch;

class SwitchItemOnClickListener implements Switch.OnClickListener {
    private static final String TAG = "SwitchItemOnClickListen";
    private Switch newSwitch;
    private SeatListController seatListController;

    SwitchItemOnClickListener(Switch newSwitch, Context context) {
        this.newSwitch = newSwitch;
        seatListController = new SeatListController(context);
    }

    @Override
    public void onClick(View v) {
        if (newSwitch.getTag().equals("Room")) {
            if (newSwitch.isChecked()) {
                // Room Switch was just checked - Add all Seats with matching roomId to seatList
                seatListController.addSeatsWithMatchingId(newSwitch.getId());
                SeatActivity.updateSwitchUI();

            } else if (!newSwitch.isChecked()) {
                // Room Switch was just unchecked - Remove all Seats with matching roomId from seatList
                seatListController.removeSeatsWithMatchingId(newSwitch.getId());
                // Makes each Seat Switch with matching roomId turn off
                SeatActivity.updateSwitchUI();
            }
        } else if (newSwitch.getTag().equals("Seat")) {
            if (newSwitch.isChecked()) {
                // Seat Switch was just checked -  if Switch's with matching roomId are also checked, toggle Room Switch with matching roomId
                return;
            }
            // Seat Switch was just unchecked - Un-check Room Switch with matching roomId

        }
    }
}
