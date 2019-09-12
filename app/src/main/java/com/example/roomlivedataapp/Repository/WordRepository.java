package com.example.roomlivedataapp.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.RoomDatabase;

import com.example.roomlivedataapp.dao.WordDao;
import com.example.roomlivedataapp.models.Word;
import com.example.roomlivedataapp.roomDatabase.WordRoomDatabase;

import java.util.List;

public class WordRepository {
    private WordDao wordDao;
    private LiveData<List<Word>> wordList;

    public WordRepository(Application application){
        WordRoomDatabase roomDatabase = WordRoomDatabase.getDatabase(application);
        wordDao = roomDatabase.wordDao();
        wordList = wordDao.getAllWords();
    }

    public LiveData<List<Word>> getAllWords(){
        return wordList;
    }

    public void insert(Word word){
        new InsertWord(wordDao).execute(word);
    }

    private static class InsertWord extends AsyncTask<Word,Void,Void>{
        private WordDao mAsyncWordDao;

        InsertWord(WordDao mAsyncWordDao) {
            this.mAsyncWordDao = mAsyncWordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            mAsyncWordDao.insetWord(words[0]);
            return null;
        }
    }
}
