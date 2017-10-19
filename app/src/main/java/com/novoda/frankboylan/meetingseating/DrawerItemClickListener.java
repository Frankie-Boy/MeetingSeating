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
        Intent intent = new Intent();
        switch (position) {
            case 0: // Home
                intent = new Intent(context, StatisticsActivity.class);
                break;
            case 1: // Seat list
                intent = new Intent(context, SeatActivity.class);
                break;
            case 2: // Room list
                intent = new Intent(context, RoomActivity.class);
                break;
            case 3: // Settings
                intent = new Intent(context, SettingsActivity.class);
                break;
            case 4: // Logout
                intent = new Intent(context, LoginActivity.class);
                break;
            default: // Log a missing item
        }
        ((Activity) context).finish(); // Temp solution to fix back pressed security issue
        context.startActivity(intent);
    }
}
