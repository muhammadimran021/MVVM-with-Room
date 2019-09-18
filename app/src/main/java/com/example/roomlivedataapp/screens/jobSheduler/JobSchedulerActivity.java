package com.example.roomlivedataapp.screens.jobSheduler;

import android.app.job.JobScheduler;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.roomlivedataapp.R;
import com.example.roomlivedataapp.databinding.ActivityJobSchedulerBinding;

public class JobSchedulerActivity extends AppCompatActivity {
    private JobSchedulerViewModel viewModel;
    private ActivityJobSchedulerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_job_scheduler);
        viewModel = ViewModelProviders.of(this).get(JobSchedulerViewModel.class);
        binding.setEvents(viewModel);
        binding.setModel(viewModel);
        viewModel.mScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        viewModel.getSeekBar.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.progress.setText(String.valueOf(integer));
            }
        });

        viewModel.isShowToast.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    Toast.makeText(JobSchedulerActivity.this, "Job Scheduled", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(JobSchedulerActivity.this, "Job can not Scheduled", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
