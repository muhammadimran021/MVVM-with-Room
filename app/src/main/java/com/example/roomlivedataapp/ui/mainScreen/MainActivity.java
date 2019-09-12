package com.example.roomlivedataapp.ui.mainScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.roomlivedataapp.R;
import com.example.roomlivedataapp.viewModel.WordViewModel;
import com.example.roomlivedataapp.adapters.WordListAdapter;
import com.example.roomlivedataapp.databinding.ActivityMainBinding;
import com.example.roomlivedataapp.models.Word;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements ClickListeners {
    private RecyclerView recyclerView;
    private WordListAdapter adapter;
    private WordViewModel viewModel;
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(WordViewModel.class);
        binding.setVmodel(viewModel);
        binding.setClick(this);
        init();

        //Observer on ViewModel Word List
        viewModel.getmWordList().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                adapter.setWords(words);
            }
        });

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                viewModel.insertWord(new Word("Hello7"));
            }
        },1000);
    }

    private void init() {
        recyclerView = findViewById(R.id.recyclerview);
        adapter = new WordListAdapter(this);
        binding.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    int i=0;
    @Override
    public void onFabClick() {
        viewModel.insertWord(new Word("Hello 8"+i));
        i++;
    }
}
