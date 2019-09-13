package com.example.roomlivedataapp.screens.mainScreen;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.roomlivedataapp.R;
import com.example.roomlivedataapp.databinding.ActivityMainNewBinding;
import com.example.roomlivedataapp.screens.JobSchedulerActivity;
import com.example.roomlivedataapp.screens.roomDataScreen.MainActivity;

public class MainNewActivity extends AppCompatActivity implements MainClicks {
    private ActivityMainNewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_new);
        binding.setOnClick(this);
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
