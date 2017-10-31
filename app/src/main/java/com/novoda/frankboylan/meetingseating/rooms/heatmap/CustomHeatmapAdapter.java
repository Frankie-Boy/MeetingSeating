package com.novoda.frankboylan.meetingseating.rooms.heatmap;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.novoda.frankboylan.meetingseating.R;

import java.util.List;

class CustomHeatmapAdapter extends ArrayAdapter<HeatmapSeat> {
    private final Context context;
    private final List<HeatmapSeat> seatList;

    CustomHeatmapAdapter(@NonNull Context context, List<HeatmapSeat> seatList) {
        super(context, R.layout.heatmap_list_item, seatList);
        this.seatList = seatList;
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("HERE: ", position + "");
        HeatmapSeat seat = seatList.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.heatmap_list_item, null);

        // ToDo: check front icon state (assuming Greyscale)

        LinearLayout llHeatmapItem = convertView.findViewById(R.id.ll_heatmap_item);
        llHeatmapItem.setBackgroundColor(heatValueToColourGreyscale(seat.getHeatValue()));

        TextView tvHeatmapTitle = convertView.findViewById(R.id.tv_heatmap_title);
        tvHeatmapTitle.setText(seat.toString());
        tvHeatmapTitle.setTextColor(Color.rgb(255, 30, 55));

        return convertView;
    }

    private int heatValueToColourGreyscale(int heatValue) {
        float base = (float) heatValue / 100;
        float col = 255 * base;
        return Color.rgb((int) col, (int) col, (int) col);
    }
}
