package com.novoda.frankboylan.meetingseating;

import android.view.View;
import android.widget.Switch;

class SwitchItemOnClickListener implements Switch.OnClickListener {
    private static final String TAG = "SwitchItemOnClickListen";
    private Switch button;
    private SeatPresenter presenter;

    SwitchItemOnClickListener(Switch newSwitch, SeatPresenter presenter) {
        this.button = newSwitch;
        this.presenter = presenter;
    }

    @Override
    public void onClick(View v) {
        if (button.getTag().equals("Room")) {
            if (button.isChecked()) {
                presenter.checkSeatsWithMatchingId(button.getId());
                return;
            }
            presenter.uncheckSeatsWithMatchingId(button.getId());
        } else if (button.getTag().equals("Seat")) {
            if (button.isChecked()) {
                // Seat Switch was just checked -  if Switch's with matching roomId are also checked, toggle Room Switch with matching roomId
                return;
            }
            // Seat Switch was just unchecked - Un-check Room Switch with matching roomId

        }
    }
}
