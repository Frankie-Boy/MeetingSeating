package com.novoda.frankboylan.meetingseating;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.novoda.frankboylan.meetingseating.SqliteDataDefinition.META_TABLE;
import static com.novoda.frankboylan.meetingseating.SqliteDataDefinition.META_TIMESTAMP;
import static com.novoda.frankboylan.meetingseating.SqliteDataDefinition.ROOM_ID;
import static com.novoda.frankboylan.meetingseating.SqliteDataDefinition.ROOM_LOCATIONNAME;
import static com.novoda.frankboylan.meetingseating.SqliteDataDefinition.ROOM_NAME;
import static com.novoda.frankboylan.meetingseating.SqliteDataDefinition.ROOM_TABLE;
import static com.novoda.frankboylan.meetingseating.SqliteDataDefinition.ROOM_UNITNAME;
import static com.novoda.frankboylan.meetingseating.SqliteDataDefinition.SEAT_ID;
import static com.novoda.frankboylan.meetingseating.SqliteDataDefinition.SEAT_ROOM_ID;
import static com.novoda.frankboylan.meetingseating.SqliteDataDefinition.SEAT_TABLE;
import static com.novoda.frankboylan.meetingseating.SqliteDataDefinition.SEAT_UNITTYPE;
import static com.novoda.frankboylan.meetingseating.SqliteDataDefinition.SEAT_VALUE;

class SqliteDataManagement {
    private SqliteDataDefinition database;

    SqliteDataManagement(Context context) {
        database = new SqliteDataDefinition(context);
    }

    void setMetaTimestamp(Long timestamp) {
        SQLiteDatabase db = database.getWritableDatabase();
        db.execSQL("INSERT INTO " + META_TABLE + "(" + META_TIMESTAMP +
                ") VALUES (" + timestamp + ");");
    }

    /**
     * Returns last updates timestamp
     */
    Timestamp getMetaTimestamp() {
        Long timestamp;
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + META_TIMESTAMP + " FROM " + META_TABLE, null);
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
     */
    void addRoom(Room room) {
        SQLiteDatabase db = database.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ROOM_ID, room.getRoomId());
        values.put(ROOM_NAME, room.getRoomName());
        values.put(ROOM_LOCATIONNAME, room.getLocation());
        values.put(ROOM_UNITNAME, room.getUnitName());

        db.insert(ROOM_TABLE, null, values);
        db.close();
    }

    List<Room> getAllRooms() {
        List<Room> roomList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + ROOM_TABLE;

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
     */
    void addSeat(Seat seat) {
        SQLiteDatabase db = database.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SEAT_ID, seat.getSeatId());
        values.put(SEAT_VALUE, seat.getValue());
        values.put(SEAT_UNITTYPE, seat.getUnitType());
        values.put(SEAT_ROOM_ID, seat.getRoomId());

        db.insert(SEAT_TABLE, null, values);

        db.close();
    }

    List<Seat> getAllSeats() {
        List<Seat> seatList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + SEAT_TABLE;

        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Seat seat = new Seat(); // Optimise this cursor.
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

    List<Seat> getSeatsWithRoomId(int roomId) {
        List<Seat> seatList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + SEAT_TABLE +
                " WHERE " + SEAT_ROOM_ID + " = " + roomId;

        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

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
