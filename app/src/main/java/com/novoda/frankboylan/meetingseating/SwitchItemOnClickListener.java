package com.novoda.frankboylan.meetingseating;

import android.view.View;
import android.widget.Switch;

class SwitchItemOnClickListener implements Switch.OnClickListener {
    private Switch newSwitch;

    SwitchItemOnClickListener(Switch newSwitch) {
        this.newSwitch = newSwitch;
    }

    @Override
    public void onClick(View v) {
        //If switch's name = room / if switch's name = seats / ischecked / !isChecked
        if(newSwitch.getTag() == "Room") {
            if(newSwitch.isChecked()) {
                // Room Switch was just checked - Add all Seats with matching roomId to seatList
                return;
            }
            // Room Switch was just unchecked - Remove all Seats with matching roomId from seatList
        } else if (newSwitch.getTag() == "Seat") {
            if (newSwitch.isChecked()) {
                // Seat Switch was just checked -  if Switch's with matching roomId are also checked, toggle Room Switch with matching roomId
                return;
            }
            // Seat Switch was just unchecked - Un-check Room Switch with matching roomId
        }
    }
}
