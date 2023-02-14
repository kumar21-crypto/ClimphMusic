package com.example.climphmusic.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.climphmusic.Modal.TrendingRVModal;
import com.example.climphmusic.R;
import com.example.climphmusic.databinding.AlbumRvModalBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TrendingRVAdaptor extends RecyclerView.Adapter<TrendingRVAdaptor.ViewHolder> {

    ArrayList<TrendingRVModal> trendingRVModalArrayList;
    Context context;

    public TrendingRVAdaptor(ArrayList<TrendingRVModal> trendingRVModalArrayList, Context context) {
        this.trendingRVModalArrayList = trendingRVModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public TrendingRVAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AlbumRvModalBinding binding = AlbumRvModalBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TrendingRVAdaptor.ViewHolder holder, int position) {

        TrendingRVModal modal = trendingRVModalArrayList.get(position);

        Picasso.get().load(modal.getAlbumImage()).into(holder.binding.albumRVImage);
        holder.binding.albumRVArtist.setText(modal.getAlbumArtist());
        holder.binding.albumRVName.setText(modal.getAlbumName());
    }

    @Override
    public int getItemCount() {
        return trendingRVModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        AlbumRvModalBinding binding;
        public ViewHolder(@NonNull AlbumRvModalBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
