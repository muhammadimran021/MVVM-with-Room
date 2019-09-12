package com.example.roomlivedataapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomlivedataapp.R;
import com.example.roomlivedataapp.models.Word;

import java.util.List;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<Word> wordList;

    public WordListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public WordListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.word_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordListAdapter.ViewHolder holder, int position) {
        if (wordList != null) {
            Word word = wordList.get(position);
            holder.textView.setText(word.getWord());
        } else {
            holder.textView.setText("no items in holder!");
        }
    }

    public void setWords(List<Word> words){
        wordList = words;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (wordList != null)
            return wordList.size();
        else return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}
