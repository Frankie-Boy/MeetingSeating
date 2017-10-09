package com.novoda.frankboylan.meetingseating;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = "DBHelper";

    private static int DATABASE_VERSION = 16;

    // Database Strings
    private static final String DATABASE_NAME = "meeting_seating_db";

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

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_ROOM = "CREATE TABLE " + TABLE_ROOM + "(" +
                ROOM_ID + " INTEGER PRIMARY KEY, " +
                ROOM_NAME + " VARCHAR(50), " +
                ROOM_LOCATIONNAME + " VARCHAR(50), " +
                ROOM_UNITNAME + " VARCHAR(50)" +
                ");";
        db.execSQL(CREATE_TABLE_ROOM);

        String CREATE_TABLE_SEAT = "CREATE TABLE " + TABLE_SEAT + "(" +
                SEAT_ID + " INTEGER, " +
                SEAT_VALUE + " DECIMAL(5,2), " +
                SEAT_UNITTYPE + " VARCHAR(5), " +
                SEAT_ROOM_ID + " INTEGER, " +
                " FOREIGN KEY (" + SEAT_ROOM_ID + ") REFERENCES " + TABLE_ROOM + "(" + ROOM_ID + ")" +
                "PRIMARY KEY (" + SEAT_ID + ", " + SEAT_ROOM_ID + ")" +
                ");";
        db.execSQL(CREATE_TABLE_SEAT);

        String CREATE_META_TABLE = "CREATE TABLE " + TABLE_META + "(" +
                META_TIMESTAMP + " INTEGER PRIMARY KEY" +
                ");";
        db.execSQL(CREATE_META_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROOM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SEAT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_META);
        onCreate(db);
    }

    /**
     * Deletes, then Re-creates both TABLE_ROOM & TABLE_SEAT
     */
    public void clearData() {
        SQLiteDatabase db = this.getWritableDatabase();
        onUpgrade(db, 0, 1);
    }

    public void setMetaTimestamp(Long timestamp) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO " + TABLE_META + "(" + META_TIMESTAMP +
        ") VALUES (" + timestamp + ");");
    }

    /**
     * Returns last updates timestamp
     */
    public Timestamp getMetaTimestamp() {
        Long timestamp;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT " + META_TIMESTAMP + " FROM " + TABLE_META, null);

        if(c.moveToFirst()) {
            timestamp = c.getLong(0);
            return new Timestamp(timestamp);
        }
        return Timestamp.INVALID_TIMESTAMP;
    }

    /**
     * Insert a room row into the database, for back-end use only.
     * @param room
     */
    public void addRoom(Room room) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ROOM_ID, room.getRoomId());
        values.put(ROOM_NAME, room.getRoomName());
        values.put(ROOM_LOCATIONNAME, room.getLocation());
        values.put(ROOM_UNITNAME, room.getUnitName());

        db.insert(TABLE_ROOM, null, values);
        db.close();
    }

    public List<Room> getAllRooms() {
        List<Room> roomList = new ArrayList<Room>();

        String selectQuery = "SELECT  * FROM " + TABLE_ROOM;

        SQLiteDatabase db = this.getWritableDatabase();
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
        return roomList;
    }

    /**
     * Insert a new seat row into the database, for back-end use only.
     * @param seat
     */
    public void addSeat(Seat seat) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SEAT_ID, seat.getSeatId());
        values.put(SEAT_VALUE, seat.getValue());
        values.put(SEAT_UNITTYPE, seat.getUnitType());
        values.put(SEAT_ROOM_ID, seat.getRoomId());

        db.insert(TABLE_SEAT, null, values);

        db.close();
    }

    public List<Seat> getAllSeats() {
        List<Seat> seatList = new ArrayList<Seat>();

        String selectQuery = "SELECT  * FROM " + TABLE_SEAT;

        SQLiteDatabase db = this.getWritableDatabase();
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
        return seatList;
    }


}
