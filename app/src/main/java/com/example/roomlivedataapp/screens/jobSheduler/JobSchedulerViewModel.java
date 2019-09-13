package com.example.roomlivedataapp.screens.jobSheduler;

import android.app.Application;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.roomlivedataapp.R;

public class JobSchedulerViewModel extends AndroidViewModel implements ScheduleClickEvents {
    public MutableLiveData<Integer> radioButton = new MutableLiveData<>();
    public MutableLiveData<Boolean> deviceIdel = new MutableLiveData<>();
    public MutableLiveData<Boolean> deviceCharging = new MutableLiveData<>();
    public MutableLiveData<Integer> seekBarProgress = new MutableLiveData<>();


    public JobSchedulerViewModel(@NonNull Application application) {
        super(application);
        init();
    }

    private void init() {
        seekBarProgress.setValue(10);
        radioButton.setValue(R.id.noNetwork);
    }


    @Override
    public void onClickScheduleJob() {
        if (radioButton.getValue() != null) {
            switch (radioButton.getValue()) {
                case R.id.noNetwork:
                    Toast.makeText(getApplication(), "no network", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.anyNetwork:
                    Toast.makeText(getApplication(), "any network", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.wifiNetwork:
                    Toast.makeText(getApplication(), "wifi network", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    @Override
    public void onClickCancelJob() {

    }

    @Override
    public void onDeviceIdleChange(CompoundButton button, Boolean isChecked) {

    }

    @Override
    public void deviceIdle(Boolean isChecked) {
        if (isChecked)
            Toast.makeText(getApplication(), "device Idle check", Toast.LENGTH_SHORT).show();
        else Toast.makeText(getApplication(), "device Idle Uncheck", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void deviceCharging(Boolean isChecked) {
        if (isChecked)
            Toast.makeText(getApplication(), "device charge check", Toast.LENGTH_SHORT).show();
        else Toast.makeText(getApplication(), "device charge Uncheck", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSeekValueChange(SeekBar seekBar, int progress, boolean isSeek) {
        seekBarProgress.setValue(20);
    }
}
