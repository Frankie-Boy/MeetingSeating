package com.novoda.frankboylan.meetingseating.settings;

class SettingsPresenterImpl implements SettingsPresenter {

    private SettingsDisplayer displayer;
    private SettingsModel model;

    SettingsPresenterImpl(SettingsDisplayer displayer, SettingsModel model) {
        this.displayer = displayer;

        this.model = model;
    }

    @Override
    public void bind(SettingsDisplayer displayer) {
        this.displayer = displayer;
    }

    @Override
    public void unbind() {
        this.displayer = null;
    }

    @Override
    public void loadDataset(int i) {
        if (i == 2) {
            displayer.showToast("That dataset hasn't been created yet!");
        } else {
            model.replaceWithDataset(i);
            displayer.showToast("Dataset #" + (i + 1) + " loaded.");

        }
    }
}
