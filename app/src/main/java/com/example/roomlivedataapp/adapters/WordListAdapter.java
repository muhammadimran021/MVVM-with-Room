package com.example.roomlivedataapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomlivedataapp.R;
import com.example.roomlivedataapp.databinding.WordItemLayoutBinding;
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
        WordItemLayoutBinding binding = DataBindingUtil.inflate(inflater, R.layout.word_item_layout, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull WordListAdapter.ViewHolder holder, int position) {
        holder.binding.setWord(wordList.get(position));
    }

    public void setWords(List<Word> words) {
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
        private WordItemLayoutBinding binding;

        public ViewHolder(@NonNull WordItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
