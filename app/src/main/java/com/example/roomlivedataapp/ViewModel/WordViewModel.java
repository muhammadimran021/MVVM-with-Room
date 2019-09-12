package com.example.roomlivedataapp.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.roomlivedataapp.Repository.WordRepository;
import com.example.roomlivedataapp.models.Word;

import java.util.List;

public class WordViewModel extends AndroidViewModel {
    private WordRepository mRepository;
    private LiveData<List<Word>> mWordList;
    public WordViewModel(@NonNull Application application) {
        super(application);
        mRepository = new WordRepository(application);
        mWordList = mRepository.getAllWords();
    }

    public LiveData<List<Word>> getmWordList(){
        return mWordList;
    }

    public void insertWord(Word word){
        mRepository.insert(word);
    }
}
