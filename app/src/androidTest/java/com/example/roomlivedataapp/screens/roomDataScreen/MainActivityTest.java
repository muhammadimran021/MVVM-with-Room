package com.example.roomlivedataapp.screens.roomDataScreen;

import androidx.test.rule.ActivityTestRule;

import com.example.roomlivedataapp.screens.mainScreen.MainNewActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);
    MainActivity mainActivity;
    @Before
    public void setUp() throws Exception {
        mainActivity = mainActivityActivityTestRule.getActivity();
    }

    @Test
    public void addData(){
        mainActivity.onFabClick();
    }

    @After
    public void tearDown() throws Exception {
    }
}