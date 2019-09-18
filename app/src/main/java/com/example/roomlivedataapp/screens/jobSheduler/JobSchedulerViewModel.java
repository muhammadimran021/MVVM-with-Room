package com.example.roomlivedataapp.screens.jobSheduler;

import android.app.Application;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.roomlivedataapp.R;
import com.example.roomlivedataapp.services.NotificationJobService;

public class JobSchedulerViewModel extends AndroidViewModel implements ScheduleClickEvents {
    public MutableLiveData<Integer> radioButton = new MutableLiveData<>();
    private MutableLiveData<Boolean> deviceIdel = new MutableLiveData<>();
    private LiveData<Boolean> deviceIdelLiveData = deviceIdel;
    private MutableLiveData<Boolean> deviceCharging = new MutableLiveData<>();
    private LiveData<Boolean> deviceChargingLiveData = deviceCharging;
    private MutableLiveData<Integer> seekBarProgress = new MutableLiveData<>();
    LiveData<Integer> getSeekBar = seekBarProgress;
    private static final int JOB_ID = 0;
    JobScheduler mScheduler;
    private MutableLiveData<Boolean> mutableLiveData = new MutableLiveData<>();
    LiveData<Boolean> isShowToast = mutableLiveData;

    public JobSchedulerViewModel(@NonNull Application application) {
        super(application);
        init();
    }

    private void init() {
        radioButton.setValue(R.id.noNetwork);
        deviceIdel.setValue(false);
        deviceCharging.setValue(false);
    }


    @Override
    public void onClickScheduleJob() {
        ScheduleJob();
    }

    private void ScheduleJob() {
        int selectedNetwordkId = 0;
        boolean seekbarProgess = false;
        if (getSeekBar.getValue() != null) {
            seekbarProgess = getSeekBar.getValue() > 0;
        }
        if (radioButton.getValue() != null) {
            switch (radioButton.getValue()) {
                case R.id.noNetwork:
                    selectedNetwordkId = JobInfo.NETWORK_TYPE_NONE;
                    break;
                case R.id.anyNetwork:
                    selectedNetwordkId = JobInfo.NETWORK_TYPE_ANY;
                    break;
                case R.id.wifiNetwork:
                    selectedNetwordkId = JobInfo.NETWORK_TYPE_UNMETERED;
                    break;
            }
        }

        ComponentName componentName = new ComponentName(getApplication().getPackageName(),
                NotificationJobService.class.getName());
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, componentName)
                .setRequiredNetworkType(selectedNetwordkId)
                .setRequiresDeviceIdle(deviceIdelLiveData.getValue())
                .setRequiresCharging(deviceChargingLiveData.getValue());

        if (seekbarProgess) {
            builder.setOverrideDeadline(getSeekBar.getValue() * 1000);
        }

        boolean setConstraint = selectedNetwordkId != JobInfo.NETWORK_TYPE_NONE
                || deviceChargingLiveData.getValue()
                || deviceIdelLiveData.getValue()
                || seekbarProgess;

        if (setConstraint) {
            JobInfo myJob = builder.build();
            mScheduler.schedule(myJob);
            mutableLiveData.setValue(true);
        }else {
            mutableLiveData.setValue(false);
        }
    }

    @Override
    public void onClickCancelJob() {
        if (mScheduler != null) {
            mScheduler.cancelAll();
            mScheduler = null;
        }
    }

    @Override
    public void deviceIdle(Boolean isChecked) {
        deviceIdel.setValue(isChecked);
    }

    @Override
    public void deviceCharging(Boolean isChecked) {
        deviceCharging.setValue(isChecked);
    }

    @Override
    public void onSeekValueChange(SeekBar seekBars, int progress, boolean isSeek) {
        seekBarProgress.setValue(progress);
    }

}
