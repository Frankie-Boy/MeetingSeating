package com.novoda.frankboylan.meetingseating;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

class CustomRoomAdapter extends ArrayAdapter<Room> implements View.OnClickListener {
    private static String TAG = "CustomRoomAdapter";
    private List<Room> roomList;
    private Context context;

    public CustomRoomAdapter(Context context, List<Room> roomList) {
        super(context, -1, roomList);
        this.context = context;
        this.roomList = roomList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.seat_list_item, parent, false);
        TextView tvRoomName = rowView.findViewById(R.id.tv_room_name);
        TextView tvLocation = rowView.findViewById(R.id.tv_room_location);
        TextView tvUnitName = rowView.findViewById(R.id.tv_room_unitname);
        TextView tvSeatCount = rowView.findViewById(R.id.tv_room_seatcount);

        Room room = roomList.get(position);
        tvRoomName.setText(room.getRoomName());
        tvLocation.setText(room.getLocation());
        tvUnitName.setText(room.getUnitName());
        int seatCount = room.getSeats().size();
        if (seatCount == 0) {
            tvSeatCount.setText("0");
        } else {
            tvSeatCount.setText(String.valueOf(seatCount));
        }

        return rowView;
    }

    @Override
    public void onClick(View v) {
        // ToDo: launch inner view for Room statistics
    }
}
