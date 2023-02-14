package com.example.climphmusic.Adaptors;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.climphmusic.Activities.AlbumDetailActivity;
import com.example.climphmusic.Modal.AlbumRVModal;
import com.example.climphmusic.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChartRVAdaptor extends RecyclerView.Adapter<ChartRVAdaptor.ViewHolder> {

    public ArrayList<AlbumRVModal> albumRVModalArrayList;
    public Context context;

    public ChartRVAdaptor(ArrayList<AlbumRVModal> albumRVModalArrayList, Context context) {
        this.albumRVModalArrayList = albumRVModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ChartRVAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_rv_modal,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChartRVAdaptor.ViewHolder holder, int position) {

        AlbumRVModal rvModal = albumRVModalArrayList.get(position);

        Picasso.get().load(rvModal.getAlbumImageUrl()).into(holder.chartImage);
        holder.chartName.setText(rvModal.getAlbumName());

        holder.itemView.setOnClickListener(view -> {

            Intent intent = new Intent(context, AlbumDetailActivity.class);
            intent.putExtra("image",rvModal.getAlbumImageUrl());
            intent.putExtra("name",rvModal.getAlbumName());
            intent.putExtra("subtitle",rvModal.getAlbumSubtitle());
            intent.putExtra("count",rvModal.getSongCount());
            intent.putExtra("type",rvModal.getAlbum_type());
            intent.putExtra("url",rvModal.getExternal_urls());
            intent.putExtra("id",rvModal.getAlbumId());
            intent.putExtra("intent","chartAdaptor");
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return albumRVModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView chartImage,playlistImage;
        private TextView chartName,playlistName,playlistSubtitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            chartImage = itemView.findViewById(R.id.albumRVImage);
            chartName = itemView.findViewById(R.id.albumRVName);

        }
    }
}
