package com.example.roomlivedataapp.screens.roomDataScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.example.roomlivedataapp.R;
import com.example.roomlivedataapp.Utils.AppNotifications;
import com.example.roomlivedataapp.viewModel.WordViewModel;
import com.example.roomlivedataapp.adapters.WordListAdapter;
import com.example.roomlivedataapp.databinding.ActivityMainBinding;
import com.example.roomlivedataapp.models.Word;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.roomlivedataapp.Utils.AppNotifications.ACTION_UPDATE_NOTIFICATION;

public class MainActivity extends AppCompatActivity implements ClickListeners {
    private RecyclerView recyclerView;
    private WordListAdapter adapter;
    private WordViewModel viewModel;
    private ActivityMainBinding binding;
    private NotificationReceiver mReceiver = new NotificationReceiver();
    AppNotifications appNotifications;
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
        // Register the broadcast receiver to receive the update action from
        // the notification.
        registerReceiver(mReceiver,
                new IntentFilter(ACTION_UPDATE_NOTIFICATION));
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
        appNotifications = new AppNotifications(this);
        appNotifications.SendNotification(this);
    }

    /**
     * Unregisters the receiver when the app is being destroyed.
     */
    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    /**
     * The broadcast receiver class for notifications.
     * Responds to the update notification pending intent action.
     */
    public class NotificationReceiver extends BroadcastReceiver {

        public NotificationReceiver() {
        }

        /**
         * Receives the incoming broadcasts and responds accordingly.
         *
         * @param context Context of the app when the broadcast is received.
         * @param intent The broadcast intent containing the action.
         */
        @Override
        public void onReceive(Context context, Intent intent) {
            // Update the notification.
            appNotifications.updateNotification(context);
        }
    }
}
