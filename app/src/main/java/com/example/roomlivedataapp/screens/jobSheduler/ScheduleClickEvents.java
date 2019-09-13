package com.example.roomlivedataapp.screens.jobSheduler;

import android.widget.CompoundButton;
import android.widget.SeekBar;

public interface ScheduleClickEvents {
    void onClickScheduleJob();

    void onClickCancelJob();

    void onDeviceIdleChange(CompoundButton button, Boolean isChecked);

    void deviceIdle(Boolean isChecked);

    void deviceCharging(Boolean isChecked);

    void onSeekValueChange(SeekBar seekBar, int progress, boolean isSeek);

}
