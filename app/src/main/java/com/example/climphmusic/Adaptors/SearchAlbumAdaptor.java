package com.example.climphmusic.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.climphmusic.R;
import com.example.climphmusic.Modal.SearchAlbumRVModal;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchAlbumAdaptor extends RecyclerView.Adapter<SearchAlbumAdaptor.ViewHolder> {

    public ArrayList<SearchAlbumRVModal> searchAlbumRVModalArrayList;
    public Context context;

    public SearchAlbumAdaptor(ArrayList<SearchAlbumRVModal> searchAlbumRVModalArrayList, Context context) {
        this.searchAlbumRVModalArrayList = searchAlbumRVModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public SearchAlbumAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_album_layout,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull SearchAlbumAdaptor.ViewHolder holder, int position) {

        SearchAlbumRVModal albumRVModal = searchAlbumRVModalArrayList.get(position);

        Picasso.get().load(albumRVModal.getAlbumImageUrl()).into(holder.albumImage);
        holder.albumName.setText(albumRVModal.getAlbumName());
        holder.albumSongCount.setText(albumRVModal.getAlbumSongCount());
        holder.albumDescription.setText(albumRVModal.getAlbumDescription());

    }

    @Override
    public int getItemCount() {
        return searchAlbumRVModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView albumImage;
        private TextView albumName,albumSongCount,albumDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            albumImage = itemView.findViewById(R.id.searchAlbumImage);
            albumName = itemView.findViewById(R.id.searchAlbumName);
            albumSongCount = itemView.findViewById(R.id.searchAlbumSongCount);
            albumDescription = itemView.findViewById(R.id.searchAlbumDescription);

        }
    }
}
