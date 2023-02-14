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

public class PlaylistRVAdaptor extends RecyclerView.Adapter<PlaylistRVAdaptor.ViewHolder> {

    public ArrayList<AlbumRVModal> albumRVModalArrayList;
    public Context context;

    public PlaylistRVAdaptor(ArrayList<AlbumRVModal> albumRVModalArrayList, Context context) {
        this.albumRVModalArrayList = albumRVModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public PlaylistRVAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_rv_modal,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistRVAdaptor.ViewHolder holder, int position) {

        AlbumRVModal rvModal = albumRVModalArrayList.get(position);

        Picasso.get().load(rvModal.getAlbumImageUrl()).into(holder.playlistImage);
        holder.playlistName.setText(rvModal.getAlbumName());
        holder.playlistSubtitle.setText(rvModal.getAlbumSubtitle());

        holder.itemView.setOnClickListener(view -> {

            Intent intent = new Intent(context, AlbumDetailActivity.class);
            intent.putExtra("image",rvModal.getAlbumImageUrl());
            intent.putExtra("name",rvModal.getAlbumName());
            intent.putExtra("subtitle",rvModal.getAlbumSubtitle());
            intent.putExtra("count",rvModal.getSongCount());
            intent.putExtra("type",rvModal.getAlbum_type());
            intent.putExtra("url",rvModal.getExternal_urls());
            intent.putExtra("id",rvModal.getAlbumId());
            intent.putExtra("intent","playlistAdaptor");
            context.startActivity(intent);

        });

    }

    @Override
    public int getItemCount() {
        return albumRVModalArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView playlistImage;
        private TextView playlistName,playlistSubtitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            playlistImage = itemView.findViewById(R.id.albumRVImage);
            playlistName = itemView.findViewById(R.id.albumRVName);
            playlistSubtitle = itemView.findViewById(R.id.albumRVArtist);

        }
    }
}
