package com.novoda.frankboylan.meetingseating;

class SettingsModelImpl implements SettingsModel {

    private SQLiteDataManagement sqLiteDataManagement;

    SettingsModelImpl(SQLiteDataManagement sqLiteDataManagement) {
        this.sqLiteDataManagement = sqLiteDataManagement;
    }

    @Override
    public void replaceWithDataset(int i) {
        sqLiteDataManagement.replaceWithDataset(i);
    }
}
