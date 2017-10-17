package com.novoda.frankboylan.meetingseating;

interface SettingsPresenter {
    void bind(SettingsDisplayer displayer);

    void unbind();

    void loadDataset(int i);
}
