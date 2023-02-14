package com.example.climphmusic.Adaptors;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.climphmusic.Activities.SongPlaygroundActivity;
import com.example.climphmusic.Modal.SearchSongRVModal;
import com.example.climphmusic.R;
import com.example.climphmusic.databinding.SearchSongLayoutBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AlbumDetailSongRVAdaptor extends RecyclerView.Adapter<AlbumDetailSongRVAdaptor.ViewHolder> {

    public static ArrayList<SearchSongRVModal> songRVModalArrayList;
    public Context context;

    public AlbumDetailSongRVAdaptor(ArrayList<SearchSongRVModal> songRVModalArrayList, Context context) {
        this.songRVModalArrayList = songRVModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public AlbumDetailSongRVAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SearchSongLayoutBinding binding = SearchSongLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumDetailSongRVAdaptor.ViewHolder holder, int position) {

        SearchSongRVModal songRVModal = songRVModalArrayList.get(position);

        Picasso.get().load(songRVModal.getSongImage()).into(holder.binding.searchSongImage);
        holder.binding.searchSongName.setText(songRVModal.getSongName());
        holder.binding.searchSongArtist.setText(songRVModal.getSongArtist());


        holder.itemView.setOnClickListener(view -> {

            Intent intent = new Intent(context, SongPlaygroundActivity.class);
            intent.putExtra("name",songRVModal.getSongName());
            intent.putExtra("singer",songRVModal.getSongArtist());
            intent.putExtra("image",songRVModal.getSongImage());
            intent.putExtra("url",songRVModal.getSongUrl());
            intent.putExtra("class","AlbumDetailSongAdaptor");
            intent.putExtra("position",position);
            context.startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        return songRVModalArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        SearchSongLayoutBinding binding;
        public ViewHolder(@NonNull SearchSongLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
