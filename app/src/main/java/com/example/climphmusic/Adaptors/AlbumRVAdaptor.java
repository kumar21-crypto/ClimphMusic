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

public class AlbumRVAdaptor extends RecyclerView.Adapter<AlbumRVAdaptor.ViewHolder> {

    public ArrayList<AlbumRVModal> albumRVModalArrayList;
    public Context context;

    public AlbumRVAdaptor(ArrayList<AlbumRVModal> albumRVModalArrayList, Context context) {
        this.albumRVModalArrayList = albumRVModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public AlbumRVAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_rv_modal,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AlbumRVAdaptor.ViewHolder holder, int position) {

        AlbumRVModal albumRVModal = albumRVModalArrayList.get(position);
        Picasso.get().load(albumRVModal.getAlbumImageUrl()).into(holder.albumIV);

        holder.albumNameTV.setText(albumRVModal.getAlbumName());
        holder.albumDetailTV.setText(albumRVModal.getArtistName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AlbumDetailActivity.class);
                intent.putExtra("album_name",albumRVModal.getAlbumName());
                intent.putExtra("album_artist",albumRVModal.getArtistName());
                intent.putExtra("type",albumRVModal.getAlbum_type());
                intent.putExtra("album_url",albumRVModal.getExternal_urls());
                intent.putExtra("album_image",albumRVModal.getAlbumImageUrl());
                intent.putExtra("album_id",albumRVModal.getAlbumId());
//                intent.putExtra("count",albumRVModal.getSongCount());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return albumRVModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView albumIV;
        private TextView albumNameTV, albumDetailTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            albumIV = itemView.findViewById(R.id.albumRVImage);
            albumNameTV = itemView.findViewById(R.id.albumRVName);
            albumDetailTV = itemView.findViewById(R.id.albumRVArtist);
        }
    }
}
