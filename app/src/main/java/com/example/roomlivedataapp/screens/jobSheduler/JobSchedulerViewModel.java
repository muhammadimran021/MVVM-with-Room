package com.example.roomlivedataapp.screens.jobSheduler;

import android.app.Application;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class JobSchedulerViewModel extends AndroidViewModel implements ScheduleClickEvents{

    public JobSchedulerViewModel(@NonNull Application application) {
        super(application);
    }


    @Override
    public void onClickScheduleJob() {
        Toast.makeText(getApplication(), "Click", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickCancelJob() {

    }

    @Override
    public void onDeviceIdleChange(CompoundButton button, Boolean isChecked) {

    }
}
