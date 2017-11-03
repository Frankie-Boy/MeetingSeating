package com.novoda.frankboylan.meetingseating.seats;

import android.view.View;
import android.widget.Switch;

class SwitchItemOnClickListener implements Switch.OnClickListener {
    private Switch button;
    private SeatDisplayer displayer;

    SwitchItemOnClickListener(Switch newSwitch, SeatDisplayer displayer) {
        this.button = newSwitch;
        this.displayer = displayer;
    }

    @Override
    public void onClick(View v) {
        if (button.getTag().equals("Room")) {
            if (button.isChecked()) {
                displayer.checkSeatsWithMatchingId(button.getId());
                return;
            }
            displayer.uncheckSeatsWithMatchingId(button.getId());
        }
    }
}
