package com.example.roomlivedataapp.services;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.roomlivedataapp.Utils.AppNotifications;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class NotificationJobService extends JobService {
    private AppNotifications appNotifications;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        appNotifications = new AppNotifications(getApplicationContext());
        appNotifications.SendNotification(getApplicationContext(),
                "Job Started", "Notification Job Scheduler Start");
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        appNotifications.SendNotification(getApplicationContext(),
                "Job Stop", "Notification Job Scheduler Stop");
        return false;
    }
}
