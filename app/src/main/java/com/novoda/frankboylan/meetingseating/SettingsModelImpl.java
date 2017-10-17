package com.novoda.frankboylan.meetingseating;

class SettingsModelImpl implements SettingsModel {

    private SQLiteDataDefinition sqliteDataDefinition;
    private SQLiteDataManagement sqLiteDataManagement;

    SettingsModelImpl(SQLiteDataDefinition sqliteDataDefinition, SQLiteDataManagement sqLiteDataManagement) {
        this.sqliteDataDefinition = sqliteDataDefinition;
        this.sqLiteDataManagement = sqLiteDataManagement;
    }

    @Override
    public void replaceWithDataset(int i) {
        sqLiteDataManagement.replaceWithDataset(i);
    }
}
