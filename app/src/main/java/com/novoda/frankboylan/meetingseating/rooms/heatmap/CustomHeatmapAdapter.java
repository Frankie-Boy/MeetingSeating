package com.novoda.frankboylan.meetingseating.rooms.heatmap;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
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

    public View getView(int position, View rowView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        rowView = inflater.inflate(R.layout.heatmap_list_item, null);
        HeatmapSeat seat = seatList.get(position);
        return customiseRowView(rowView, seat);
    }

    private View customiseRowView(View rowView, HeatmapSeat seat) {
        // ToDo: check front icon state (assuming Greyscale)

        LinearLayout llHeatmapItem = rowView.findViewById(R.id.ll_heatmap_item);
        TextView tvHeatmapTitle = rowView.findViewById(R.id.tv_heatmap_title);

        // Setting up Greyscale themed UI
        llHeatmapItem.setBackgroundColor(heatValueToGreyscaleBackgroundColor(seat.getHeatValue()));

        tvHeatmapTitle.setText(seat.toString());
        tvHeatmapTitle.setTextColor(heatValueToGreyscaleTextColor(seat.getHeatValue()));

        return rowView;
    }

    private int heatValueToGreyscaleBackgroundColor(int heatValue) {
        float base = 1 - (float) heatValue / 100;
        float col = 255 * base;
        return Color.rgb((int) col, (int) col, (int) col);
    }

    private int heatValueToGreyscaleTextColor(int heatValue) {
        if (heatValue > 70) {
            return Color.rgb(255, 255, 255);
        }
        return Color.rgb(0, 0, 0);
    }
}
