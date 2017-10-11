package com.novoda.frankboylan.meetingseating;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

class SqliteDML {
    private static final String TAG = "SqliteDML";
    // Database Strings
    private static final String TABLE_ROOM = "rooms";               // Room Table
    private static final String ROOM_ID = "room_id";
    private static final String ROOM_NAME = "room_name";
    private static final String ROOM_LOCATIONNAME = "room_locationname";
    private static final String ROOM_UNITNAME = "room_unitname";

    private static final String TABLE_SEAT = "seats";               // Seats Table
    private static final String SEAT_ID = "seat_id";
    private static final String SEAT_VALUE = "seat_value";
    private static final String SEAT_UNITTYPE = "seat_unittype";
    private static final String SEAT_ROOM_ID = "seat_roomid";

    private static final String TABLE_META = "metadata";            // Meta-data Table
    private static final String META_TIMESTAMP = "meta_timestamp";

    private SqliteDDL database;

    SqliteDML(Context context) {
        database = new SqliteDDL(context);
    }

    void setMetaTimestamp(Long timestamp) {
        SQLiteDatabase db = database.getWritableDatabase();
        db.execSQL("INSERT INTO " + TABLE_META + "(" + META_TIMESTAMP +
                ") VALUES (" + timestamp + ");");
    }

    /**
     * Returns last updates timestamp
     */
    Timestamp getMetaTimestamp() {
        Long timestamp;
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + META_TIMESTAMP + " FROM " + TABLE_META, null);
        if(cursor.moveToFirst()) {
            timestamp = cursor.getLong(0);
            return new Timestamp(timestamp);
        }
        cursor.close();
        db.close();
        return Timestamp.INVALID_TIMESTAMP;
    }

    /**
     * Insert a room row into the database, for back-end use only.
     * @param room
     */
    void addRoom(Room room) {
        SQLiteDatabase db = database.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ROOM_ID, room.getRoomId());
        values.put(ROOM_NAME, room.getRoomName());
        values.put(ROOM_LOCATIONNAME, room.getLocation());
        values.put(ROOM_UNITNAME, room.getUnitName());

        db.insert(TABLE_ROOM, null, values);
        db.close();
    }

    List<Room> getAllRooms() {
        List<Room> roomList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_ROOM;

        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Room room = new Room();
                room.setRoomId(cursor.getInt(0));
                room.setRoomName(cursor.getString(1));
                room.setLocation(cursor.getString(2));
                room.setUnitName(cursor.getString(3));

                roomList.add(room);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return roomList;
    }

    /**
     * Insert a new seat row into the database, for back-end use only.
     * @param seat
     */
    void addSeat(Seat seat) {
        SQLiteDatabase db = database.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SEAT_ID, seat.getSeatId());
        values.put(SEAT_VALUE, seat.getValue());
        values.put(SEAT_UNITTYPE, seat.getUnitType());
        values.put(SEAT_ROOM_ID, seat.getRoomId());

        db.insert(TABLE_SEAT, null, values);

        db.close();
    }

    List<Seat> getAllSeats() {
        List<Seat> seatList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_SEAT;

        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Seat seat = new Seat();
                seat.setSeatId(cursor.getInt(0));
                seat.setValue(cursor.getInt(1));
                seat.setUnitType(cursor.getString(2));
                seat.setRoomId(cursor.getInt(3));

                seatList.add(seat);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return seatList;
    }
}
