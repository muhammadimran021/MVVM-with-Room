package com.example.roomlivedataapp.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "word_table")
public class Word {

    // every entity need primary key and also remember one thing primary key can not be null
    @PrimaryKey
    @NonNull
    //if you want to separate member from other members of entity then use ColumnInfo
    @ColumnInfo(name = "word")
    private String mWord;

    public Word(@NonNull String word) {
        this.mWord = word;
    }

    public String getWord() {
        return this.mWord;
    }
}