package com.novoda.frankboylan.meetingseating.SQLiteDataManagement;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class SQLiteUpdate {
    private SQLiteCreate database;
    private static final String TAG = "SQLiteUpdate";

    public SQLiteUpdate(Context context) {
        database = new SQLiteCreate(context);
    }

    /**
     * Updates the Timestamp in META_TABLE
     */
    public void updateMetaTimestamp(Long timestamp) {
        SQLiteDatabase db = database.getWritableDatabase();
        db.execSQL("UPDATE " + SQLiteCreate.META_TABLE + " SET " + SQLiteCreate.META_TIMESTAMP +
                           " = " + timestamp + ";");
    }
}
