package com.example.roomlivedataapp.screens.jobSheduler;

import android.view.View;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class JobSchedulerActivityTest {
    @Rule
    public ActivityTestRule<JobSchedulerActivity> jobSchedulerActivityActivityTestRule =
            new ActivityTestRule<>(JobSchedulerActivity.class);
    JobSchedulerActivity jobSchedulerActivity;

    @Test
    public void testView(){

    }

    @Before
    public void setUp() throws Exception {
        jobSchedulerActivity = jobSchedulerActivityActivityTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
    }
}