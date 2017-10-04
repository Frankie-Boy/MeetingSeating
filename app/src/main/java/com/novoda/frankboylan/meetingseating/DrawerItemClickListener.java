package com.novoda.frankboylan.meetingseating;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

class DrawerItemClickListener implements android.widget.AdapterView.OnItemClickListener {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Context context = view.getContext();
        // ToDo: Tidy up switch statement
        Intent intent;
        switch (position) {
            case 0: // Home
                break;
            case 1: // Seat list
                intent = new Intent(context, SeatActivity.class);
                context.startActivity(intent);
                break;
            case 2: // Room list
                break;
            case 3: // Settings
                break;
            case 4: // Logout
                intent = new Intent(context, SignInActivity.class);
                context.startActivity(intent);
                ((Activity)context).finish();
                break;
            default: // Log a missing item
        }
    }
}
