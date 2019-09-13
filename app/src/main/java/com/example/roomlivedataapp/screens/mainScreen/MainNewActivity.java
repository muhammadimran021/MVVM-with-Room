package com.example.roomlivedataapp.screens.mainScreen;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.example.roomlivedataapp.R;
import com.example.roomlivedataapp.databinding.ActivityMainNewBinding;
import com.example.roomlivedataapp.screens.jobSheduler.JobSchedulerActivity;
import com.example.roomlivedataapp.screens.roomDataScreen.MainActivity;

public class MainNewActivity extends AppCompatActivity implements MainClicks {
    private ActivityMainNewBinding binding;
    private MainNewActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_new);
        viewModel  = ViewModelProviders.of(this).get(MainNewActivityViewModel.class);
        binding.setOnClick(this);
        binding.setViewModel(viewModel);
    }

    @Override
    public void onRoomButtonClick() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onJobSchedulerClick() {
        Intent intent = new Intent(this, JobSchedulerActivity.class);
        startActivity(intent);
    }
}
