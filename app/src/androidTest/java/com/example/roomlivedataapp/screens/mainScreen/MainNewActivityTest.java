package com.example.roomlivedataapp.screens.mainScreen;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class MainNewActivityTest {
    @Rule
    public ActivityTestRule<MainNewActivity> mainNewActivityActivityTestRule =
            new ActivityTestRule<>(MainNewActivity.class);
    private MainNewActivity mainNewActivity;

    @Test
    public void TestFunc(){
        mainNewActivity.onRoomButtonClick();
    }

    @Before
    public void setUp() throws Exception {
        mainNewActivity = mainNewActivityActivityTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
    }
}