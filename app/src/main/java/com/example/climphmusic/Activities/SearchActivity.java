package com.example.climphmusic.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.climphmusic.R;
import com.example.climphmusic.Adaptors.SearchAlbumAdaptor;
import com.example.climphmusic.Modal.SearchAlbumRVModal;
import com.example.climphmusic.Adaptors.SearchSongRVAdaptor;
import com.example.climphmusic.Modal.SearchSongRVModal;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SearchActivity extends AppCompatActivity {

    SearchView searchView;
    TextView album,song;

    String id ,songName,songUrl,songImage,songImage1,songDuration,songArtist;
    String query_intent;
    ShimmerFrameLayout shimmerSongs,shimmerAlbums;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Objects.requireNonNull(getSupportActionBar()).hide();
        shimmerSongs = findViewById(R.id.shimmer_search_songs);
        shimmerAlbums = findViewById(R.id.shimmer_search_albums);
        shimmerSongs.startShimmer();
        shimmerAlbums.startShimmer();
        searchView = findViewById(R.id.search_all_activity);
        album = findViewById(R.id.searchAlbumHeading);
        song = findViewById(R.id.searchSongHeading);
        query_intent = getIntent().getStringExtra("query");

        if(query_intent != null){
            initializeSearchAlbum(query_intent);
            initializeSearchSong(query_intent);
        }


        @SuppressLint("DiscouragedApi") int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text",null,null);
        EditText editText = (EditText) searchView.findViewById(id);
        editText.setTextColor(Color.WHITE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                initializeSearchAlbum(s);
                initializeSearchSong(s);
                editText.getText().clear();
                editText.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        searchView.setOnClickListener(view -> {
            super.onBackPressed();
        });

    }

    private void initializeSearchSong(String s) {

        RecyclerView searchSongRV = findViewById(R.id.searchSongRV);
        ArrayList<SearchSongRVModal> searchSongRVModalArrayList = new ArrayList<>();
        SearchSongRVAdaptor searchSongRVAdaptor = new SearchSongRVAdaptor(searchSongRVModalArrayList,this);
        searchSongRV.setAdapter(searchSongRVAdaptor);

        String url = "https://saavn.me/search/songs?query="+ s + "&page=1&limit=4";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(JSONObject response) {

                        song.setVisibility(View.VISIBLE);

                        try {
                            JSONObject object = response.getJSONObject("data");
                            JSONArray songArray = object.getJSONArray("results");

                            for(int i=0; i<songArray.length(); i++){

                                JSONObject albumObj = songArray.getJSONObject(i);
                                id = albumObj.getString("id");
                                songName = albumObj.getString("name");
                                songUrl = albumObj.getJSONArray("downloadUrl").getJSONObject(4).getString("link");
                                songImage = albumObj.getJSONArray("image").getJSONObject(0).getString("link");
                                songImage1 = albumObj.getJSONArray("image").getJSONObject(2).getString("link");
                                songArtist = albumObj.getString("primaryArtists");
                                songDuration = albumObj.getString("duration");

                                shimmerSongs.stopShimmer();
                                shimmerSongs.setVisibility(View.GONE);
                                searchSongRV.setVisibility(View.VISIBLE);

                                SearchSongRVModal songRVModal = new SearchSongRVModal(id, songName, songImage, songImage1, songUrl, songArtist, songDuration);
                                searchSongRVModalArrayList.add(songRVModal);
                            }

                            searchSongRVAdaptor.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SearchActivity.this, "Fail to get data : " + error, Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        queue.add(jsonObjectRequest);
    }

    private void initializeSearchAlbum(String base_url) {

        RecyclerView searchAlbumRV = findViewById(R.id.searchAlbumRV);
        ArrayList<SearchAlbumRVModal> searchAlbumRVModalArrayList = new ArrayList<>();
        SearchAlbumAdaptor searchAlbumAdaptor = new SearchAlbumAdaptor(searchAlbumRVModalArrayList,this);
        searchAlbumRV.setAdapter(searchAlbumAdaptor);

        String url = "https://saavn.me/search/all?query="+base_url;
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(JSONObject response) {

                        album.setVisibility(View.VISIBLE);

                        try {
                            JSONObject object = response.getJSONObject("data");
                            JSONObject object1 = object.getJSONObject("albums");
                            JSONArray albumArray = object1.getJSONArray("results");

                            for(int i=0; i<albumArray.length(); i++){

                                JSONObject albumObj = albumArray.getJSONObject(i);
                                String id = albumObj.getString("id");
                                String albumName = albumObj.getString("title");
                                String albumUrl = albumObj.getString("url");
                                String albumSongCount = "1 Song";
                                String albumImage = albumObj.getJSONArray("image").getJSONObject(0).getString("link");
                                String albumDesc = albumObj.getString("description");

                                shimmerAlbums.stopShimmer();
                                shimmerAlbums.setVisibility(View.GONE);
                                searchAlbumRV.setVisibility(View.VISIBLE);
                                SearchAlbumRVModal rvModal = new SearchAlbumRVModal(id,albumName,albumUrl,albumSongCount,albumImage,albumDesc);
                                searchAlbumRVModalArrayList.add(rvModal);
                            }

                            searchAlbumAdaptor.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SearchActivity.this, "Fail to get data : " + error, Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        queue.add(jsonObjectRequest);
    }
}