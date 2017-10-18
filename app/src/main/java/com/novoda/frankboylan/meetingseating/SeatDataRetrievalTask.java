package com.novoda.frankboylan.meetingseating;

import android.os.AsyncTask;

import retrofit2.Response;

class SeatDataRetrievalTask extends AsyncTask<Void, Void, Void> {
    private static final String TAG = "SeatDataRetrievalTask";

    private SQLiteDataManagement sqliteDataManagement;
    private SQLiteDataDefinition sqliteDataDefinition;

    SeatDataRetrievalTask(SQLiteDataManagement sqliteDataManagement, SQLiteDataDefinition sqliteDataDefinition) {
        this.sqliteDataManagement = sqliteDataManagement;
        this.sqliteDataDefinition = sqliteDataDefinition;
    }

    @Override
    protected Void doInBackground(Void... params) {
        SeatModel model = new SeatModelImpl(sqliteDataDefinition, sqliteDataManagement);
        Response<RoomSeatData> response = model.retrieveData();
        RoomSeatData roomSeatData = response.body();
        long serverResponseTimestamp = 0L;

        if (response.isSuccessful() && roomSeatData != null) {
            serverResponseTimestamp = Long.valueOf(roomSeatData.getLastUpdateTimestamp());
        }
        long databaseTimestamp = sqliteDataManagement.getMetaTimestamp().getTimestamp();

        if (serverResponseTimestamp > databaseTimestamp) {  // Checking data is newer than stored version.
            sqliteDataManagement.insertDataset(roomSeatData);
        }
        return null;
    }
}
