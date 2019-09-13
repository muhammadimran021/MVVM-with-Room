package com.example.roomlivedataapp.screens.jobSheduler;

import android.widget.CompoundButton;

public interface ScheduleClickEvents {
    void onClickScheduleJob();

    void onClickCancelJob();

    void onDeviceIdleChange(CompoundButton button, Boolean isChecked);

}
