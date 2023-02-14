package com.example.climphmusic.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.climphmusic.Adaptors.AlbumDetailSongRVAdaptor;
import com.example.climphmusic.Modal.SearchSongRVModal;
import com.example.climphmusic.databinding.ActivityAlbumDetailBinding;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AlbumDetailActivity extends AppCompatActivity {


    ActivityAlbumDetailBinding binding;
    String albumImage,albumName,albumArtist,albumSongCount,albumType,albumUrl,albumID,albumIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAlbumDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.shimmerSongs.startShimmer();
        Objects.requireNonNull(getSupportActionBar()).hide();

        albumType = getIntent().getStringExtra("type");

        if(albumType != null){
            switch (albumType){
                case "song":
                    getIntentSongs();
                    setAttributes();
                    setSongs();
                    break;

                case "playlist":
                    getIntentSongs();
                    setAttributes();
                    setPlaylistSongs();
                    break;

                case "album":
                    getIntentSongs();
                    setAttributes();
                    setAlbumSongs();
                    break;
            }
        }

        albumIntent = getIntent().getStringExtra("intent");
        if(albumIntent != null){
            if(albumIntent.equals("playlistAdaptor") || albumIntent.equals("chartAdaptor")){
                Toast.makeText(this, "adaptor", Toast.LENGTH_SHORT).show();
                getIntentFromActivity();
                setAttributes();
                setPlaylistSongs();
            }
        }

        
        binding.albumDetailBack.setOnClickListener(view -> {
            super.onBackPressed();
        });


    }

    private void setAlbumSongs() {

        ArrayList<SearchSongRVModal> songRVModalArrayList = new ArrayList<>();
        AlbumDetailSongRVAdaptor songRVAdaptor = new AlbumDetailSongRVAdaptor(songRVModalArrayList,this);
        binding.albumDetailAlbumSongsRV.setAdapter(songRVAdaptor);

        String url = "https://saavn.me/albums?link=" + albumUrl;
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest songObjRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONObject object = response.getJSONObject("data");
                            JSONArray topChatArray = object.getJSONArray("songs");

                            for (int i = 0; i < topChatArray.length(); i++) {

                                JSONObject object1 = topChatArray.getJSONObject(i);

                                String songName = object1.getString("name");
                                String songImageUrl = object1.getJSONArray("image").getJSONObject(2).getString("link");

                                String songArtist = object1.getString("primaryArtists");
                                String songUrl = object1.getJSONArray("downloadUrl").getJSONObject(4).getString("link");

                                binding.shimmerSongs.stopShimmer();
                                binding.shimmerSongs.setVisibility(View.GONE);
                                binding.albumDetailAlbumSongsRV.setVisibility(View.VISIBLE);

                                SearchSongRVModal songRVModal = new SearchSongRVModal(songName,songImageUrl,songUrl,songArtist);
                                songRVModalArrayList.add(songRVModal);
                            }

                            songRVAdaptor.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AlbumDetailActivity.this, "fail"+ error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        } )
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        queue.add(songObjRequest);
    }

    private void setSongs() {
        ArrayList<SearchSongRVModal> songRVModalArrayList = new ArrayList<>();
        AlbumDetailSongRVAdaptor songRVAdaptor = new AlbumDetailSongRVAdaptor(songRVModalArrayList,this);
        binding.albumDetailAlbumSongsRV.setAdapter(songRVAdaptor);

        String url = "https://saavn.me/songs?link=" + albumUrl;
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest songObjRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            JSONArray topChatArray = response.getJSONArray("data");


                                JSONObject object1 = topChatArray.getJSONObject(0);

                                String songName = object1.getString("name");
                                String songImageUrl = object1.getJSONArray("image").getJSONObject(2).getString("link");
                                String songArtist = object1.getString("primaryArtists");
                                String songUrl = object1.getJSONArray("downloadUrl").getJSONObject(4).getString("link");

                                binding.shimmerSongs.stopShimmer();
                                binding.shimmerSongs.setVisibility(View.GONE);
                                binding.albumDetailAlbumSongsRV.setVisibility(View.VISIBLE);

                                SearchSongRVModal songRVModal = new SearchSongRVModal(songName,songImageUrl,songUrl,songArtist);
                                songRVModalArrayList.add(songRVModal);


                            songRVAdaptor.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AlbumDetailActivity.this, "fail"+ error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        } )
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        queue.add(songObjRequest);
    }

    @SuppressLint("SetTextI18n")
    private void setAttributes() {
        Picasso.get().load(albumImage).into(binding.albumDetailAlbumImage);
        binding.albumDetailAlbumName.setText(albumName);
        binding.albumDetailAlbumArtist.setText(albumArtist);
        binding.albumDetailAlbumType.setText(albumType);

//            binding.albumDetailSongCount.setText(albumSongCount + " Songs");

    }

    private void getIntentSongs(){
        albumImage = getIntent().getStringExtra("album_image");
        albumName = getIntent().getStringExtra("album_name");
        albumType = getIntent().getStringExtra("type");
        albumUrl = getIntent().getStringExtra("album_url");
        albumID = getIntent().getStringExtra("album_id");
        albumArtist = getIntent().getStringExtra("album_artist");
    }
    private void getIntentFromActivity() {
        albumImage = getIntent().getStringExtra("image");
        albumName = getIntent().getStringExtra("name");
        albumArtist = getIntent().getStringExtra("subtitle");
        albumSongCount = getIntent().getStringExtra("count");
        albumType = getIntent().getStringExtra("type");
        albumUrl = getIntent().getStringExtra("url");
        albumID = getIntent().getStringExtra("id");
    }

    private void setPlaylistSongs() {

        ArrayList<SearchSongRVModal> songRVModalArrayList = new ArrayList<>();
        AlbumDetailSongRVAdaptor songRVAdaptor = new AlbumDetailSongRVAdaptor(songRVModalArrayList,this);
        binding.albumDetailAlbumSongsRV.setAdapter(songRVAdaptor);

        String url = "https://saavn.me/playlists?id=" + albumID;
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest songObjRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONObject object = response.getJSONObject("data");
                            JSONArray topChatArray = object.getJSONArray("songs");

                            for (int i = 0; i < topChatArray.length(); i++) {

                                JSONObject object1 = topChatArray.getJSONObject(i);

                                String songName = object1.getString("name");
                                String songImageUrl = object1.getJSONArray("image").getJSONObject(2).getString("link");

                                String songArtist = object1.getString("primaryArtists");
                                String songUrl = object1.getJSONArray("downloadUrl").getJSONObject(4).getString("link");

                                binding.shimmerSongs.stopShimmer();
                                binding.shimmerSongs.setVisibility(View.GONE);
                                binding.albumDetailAlbumSongsRV.setVisibility(View.VISIBLE);

                                SearchSongRVModal songRVModal = new SearchSongRVModal(songName,songImageUrl,songUrl,songArtist);
                                songRVModalArrayList.add(songRVModal);
                            }

                            songRVAdaptor.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AlbumDetailActivity.this, "fail"+ error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        } )
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        queue.add(songObjRequest);
    }
}