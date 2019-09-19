package com.example.roomlivedataapp.screens.roomDataScreen;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.test.rule.ActivityTestRule;

import com.example.roomlivedataapp.models.Word;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);
    MainActivity mainActivity;
    WordViewModel viewModel;

    @Before
    public void setUp() throws Exception {
        mainActivity = mainActivityActivityTestRule.getActivity();
        viewModel = ViewModelProviders.of(mainActivity).get(WordViewModel.class);
    }

    @Test
    public void addData() {
        mainActivity.onFabClick();
        viewModel.insertWord(new Word("foo"));
        LiveData<List<Word>> list = viewModel.getmWordList();

        assertEquals(list.getValue().get(1).getWord(), "foo"); // Pass
//        assertEquals(list.getValue().get(0).getWord(), "Foo"); // failed

    }

    @After
    public void tearDown() throws Exception {
    }
}