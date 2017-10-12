package com.novoda.frankboylan.meetingseating;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class SqliteDataDefinition extends SQLiteOpenHelper {
    private static int DATABASE_VERSION = 17;

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

    SqliteDataDefinition(Context context) {
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
    void clearData() {
        SQLiteDatabase db = this.getWritableDatabase();
        onUpgrade(db, 0, 1);
    }
}
