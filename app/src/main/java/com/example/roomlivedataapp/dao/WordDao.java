package com.example.roomlivedataapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.roomlivedataapp.models.Word;

import java.util.List;
@Dao
public interface WordDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insetWord(Word word);

    @Query("DELETE from word_table")
    void deleteAllWords();

    @Query("Select * from word_table")
    LiveData<List<Word>> getAllWords();
}
