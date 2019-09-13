package com.example.roomlivedataapp.screens.jobSheduler;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
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
    }
}
