package com.example.roomlivedataapp.screens.jobSheduler;

import android.widget.CompoundButton;
import android.widget.SeekBar;

public interface ScheduleClickEvents {
    void onClickScheduleJob();

    void onClickCancelJob();

    void deviceIdle(Boolean isChecked);

    void deviceCharging(Boolean isChecked);
    void onSeekValueChange(SeekBar seekBar, int progress, boolean isSeek);

}
