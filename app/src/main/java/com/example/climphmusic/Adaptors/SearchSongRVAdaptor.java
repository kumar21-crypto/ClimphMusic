package com.example.climphmusic.Adaptors;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.climphmusic.Activities.SongPlaygroundActivity;
import com.example.climphmusic.R;
import com.example.climphmusic.Modal.SearchSongRVModal;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchSongRVAdaptor extends RecyclerView.Adapter<SearchSongRVAdaptor.ViewHolder> {

    public ArrayList<SearchSongRVModal> searchSongRVModalArrayList;
    public Context context;

    public SearchSongRVAdaptor(ArrayList<SearchSongRVModal> searchSongRVModalArrayList, Context context) {
        this.searchSongRVModalArrayList = searchSongRVModalArrayList;
        this.context = context;
    }

    @SuppressLint("MissingInflatedId")
    @NonNull
    @Override
    public SearchSongRVAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_song_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchSongRVAdaptor.ViewHolder holder, int position) {

        SearchSongRVModal songRVModal = searchSongRVModalArrayList.get(position);
        Picasso.get().load(songRVModal.getSongImage()).into(holder.songImage);
        holder.songName.setText(songRVModal.getSongName());
        holder.songArtist.setText(songRVModal.getSongArtist());

        String url = songRVModal.getSongUrl();
        String singer = songRVModal.getSongArtist();
        String name = songRVModal.getSongName();
        String image = songRVModal.getSongImage1();
        String duration = songRVModal.getSongDuration();


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, SongPlaygroundActivity.class);
                intent.putExtra("url",url);
                intent.putExtra("singer",singer);
                intent.putExtra("name",name);
                intent.putExtra("image",image);
                intent.putExtra("duration",duration);
                intent.putExtra("class","SearchSongAdaptor");
//                intent.putExtra("position",position);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return searchSongRVModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView songImage;
        private TextView songName, songArtist;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            songImage = itemView.findViewById(R.id.searchSongImage);
            songName = itemView.findViewById(R.id.searchSongName);
            songArtist = itemView.findViewById(R.id.searchSongArtist);

        }
    }
}
