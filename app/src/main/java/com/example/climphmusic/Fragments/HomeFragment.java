package com.example.climphmusic.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.climphmusic.Activities.MainActivity;
import com.example.climphmusic.Activities.SearchActivity;
import com.example.climphmusic.Adaptors.AlbumRVAdaptor;
import com.example.climphmusic.Adaptors.ChartRVAdaptor;
import com.example.climphmusic.Adaptors.PlaylistRVAdaptor;
import com.example.climphmusic.Modal.AlbumRVModal;
import com.example.climphmusic.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HomeFragment extends Fragment {

    SearchView searchView;
    TextView greeting,chartTV,playlistTV,trendingTV,albumTV;
    ChipNavigationBar bottomNavBar;
    View view;
    EditText editText;
    ShimmerFrameLayout shimmer1,shimmer2,shimmer3,shimmer4;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);


        searchView = view.findViewById(R.id.search_all);
        searchView.clearFocus();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();
        greeting = view.findViewById(R.id.greet);
        chartTV = view.findViewById(R.id.idTVTopChartHeading);
        playlistTV = view.findViewById(R.id.idTVPlayListHeading);
        trendingTV = view.findViewById(R.id.idTVTrendingHeading);
        albumTV = view.findViewById(R.id.idTVPopularHeading);
        bottomNavBar = view.findViewById(R.id.bottom_nav_bar);
        shimmer1 = view.findViewById(R.id.shimmer_album);
        shimmer2 = view.findViewById(R.id.shimmer_playlist);
        shimmer3 = view.findViewById(R.id.shimmer_charts);
        shimmer4 = view.findViewById(R.id.shimmer_trending);

        // for start shimmer effect
        shimmer1.startShimmer();
        shimmer2.startShimmer();
        shimmer3.startShimmer();
        shimmer4.startShimmer();

        @SuppressLint("DiscouragedApi") int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text",null,null);
        editText = (EditText) searchView.findViewById(id);
        editText.setTextColor(Color.WHITE);



        searchView.setOnClickListener(view -> {
            searchView.clearFocus();
            Intent intent = new Intent(getActivity(), SearchActivity.class);
            startActivity(intent);
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Intent intent = new Intent(requireActivity(),SearchActivity.class);
                intent.putExtra("query",s);
                searchView.clearFocus();
                editText.getText().clear();
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        Thread thread = new Thread(() -> {

            initializeTopPlayLists();
            initializePopularAlbum();
            initializeTopCharts();
            initializeTrendingAlbum();

        });

        thread.start();

        return view;
    }

        private void initializeTopPlayLists() {

            RecyclerView topPlaylistRV = view.findViewById(R.id.idRVPlayLists);
            ArrayList<AlbumRVModal> albumRVModalArrayList = new ArrayList<>();
            PlaylistRVAdaptor playlistRVAdaptor = new PlaylistRVAdaptor(albumRVModalArrayList,getActivity());
            topPlaylistRV.setAdapter(playlistRVAdaptor);

            String url = "https://saavn.me/modules?language=hindi,english";
            RequestQueue queue = Volley.newRequestQueue(requireActivity());

            @SuppressLint("NotifyDataSetChanged") JsonObjectRequest trendingObjRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    response -> {

                        try {
                            JSONObject object = response.getJSONObject("data");
                            JSONArray topChatArray = object.getJSONArray("playlists");

                            for(int i=0; i< topChatArray.length(); i++){

                                JSONObject object1 = topChatArray.getJSONObject(i);

                                String id = object1.getString("id");
                                String playlistName = object1.getString("title");
                                String playlistImageUrl = object1.getJSONArray("image").getJSONObject(2).getString("link");
                                String playlistSongCount = object1.getString("songCount");
                                String playListType = object1.getString("type");
                                String playlistSubtitle = object1.getString("subtitle");
                                String playListUrl = object1.getString("url");

                                shimmer1.stopShimmer();
                                shimmer1.setVisibility(View.GONE);
                                topPlaylistRV.setVisibility(View.VISIBLE);

                                AlbumRVModal rvModal = new AlbumRVModal(playlistName,id,playlistImageUrl,playlistSubtitle,playlistSongCount,playListUrl,playListType);
                                albumRVModalArrayList.add(rvModal);
                            }

                            playlistRVAdaptor.notifyDataSetChanged();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }, error -> Toast.makeText(getActivity(), "Fail to get data of trending song : " + error, Toast.LENGTH_SHORT).show())
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String,String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    return headers;
                }
            };

            queue.add(trendingObjRequest);
        }

        private void initializeTopCharts() {

            RecyclerView trendingRV = view.findViewById(R.id.idRVTopCharts);
            ArrayList<AlbumRVModal> albumRVModalArrayList = new ArrayList<>();
            ChartRVAdaptor chartRVAdaptor = new ChartRVAdaptor(albumRVModalArrayList,getActivity());
            trendingRV.setAdapter(chartRVAdaptor);

            String url = "https://saavn.me/modules?language=hindi,english";
            RequestQueue queue = Volley.newRequestQueue(requireActivity());

            @SuppressLint("NotifyDataSetChanged") JsonObjectRequest trendingObjRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    response -> {

                        try {
                            JSONObject object = response.getJSONObject("data");
                            JSONArray topChatArray = object.getJSONArray("charts");

                            for(int i=0; i< topChatArray.length(); i++){

                                JSONObject object1 = topChatArray.getJSONObject(i);

                                String id = object1.getString("id");
                                String chartName = object1.getString("title");
                                String chartImageUrl = object1.getJSONArray("image").getJSONObject(2).getString("link");
                                String chartSubtitle = object1.getString("subtitle");
                                String count = "unknown";
                                String chartType = object1.getString("type");
                                String chartUrl = object1.getString("url");

                                shimmer3.stopShimmer();
                                shimmer3.setVisibility(View.GONE);
                                trendingRV.setVisibility(View.VISIBLE);

                                AlbumRVModal rvModal = new AlbumRVModal(chartType,chartName,chartSubtitle,chartUrl,id,chartImageUrl,chartSubtitle,count);
                                albumRVModalArrayList.add(rvModal);
                            }

                            chartRVAdaptor.notifyDataSetChanged();
                            chartTV.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }, error -> Toast.makeText(getActivity(), "Fail to get data of trending song : " + error, Toast.LENGTH_SHORT).show())
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String,String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    return headers;
                }
            };

            queue.add(trendingObjRequest);
        }

        @Override
        public void onStart() {
            super.onStart();
            setGreeting();
        }

        private void setGreeting() {

            Calendar calendar = Calendar.getInstance();
            int time = calendar.get(Calendar.HOUR_OF_DAY);
            String greet = "";

            if(time < 12){
                greet = "Good Morning ,";
            } else if (time < 16) {
                greet = "Good Afternoon ,";
            } else if (time < 20) {
                greet = "Good Evening ,";
            } else {
                greet = "Good Night ,";
            }

            greeting.setText(greet);
        }

        private void initializeTrendingAlbum() {

            RecyclerView trendingRV = view.findViewById(R.id.idRVTrendingAlbums);
            ArrayList<AlbumRVModal> albumRVModalArrayList = new ArrayList<>();
            AlbumRVAdaptor albumRVAdaptor = new AlbumRVAdaptor(albumRVModalArrayList,getActivity());
            trendingRV.setAdapter(albumRVAdaptor);

            String url = "https://saavn.me/modules?language=hindi,english";
            RequestQueue queue = Volley.newRequestQueue(getActivity());

            @SuppressLint("NotifyDataSetChanged") JsonObjectRequest trendingObjRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    response -> {

                        try {
                            JSONObject object = response.getJSONObject("data");
                            JSONObject trendingObj = object.getJSONObject("trending");
                            JSONArray trendingArray = trendingObj.getJSONArray("songs");

                            for(int i=0; i< trendingArray.length(); i++){
                                JSONObject object1 = trendingArray.getJSONObject(i);

                                String id = object1.getString("id");
                                String songName = object1.getString("name");
                                String songImageUrl = object1.getJSONArray("image").getJSONObject(2).getString("link");
                                String songArtist = object1.getJSONArray("primaryArtists").getJSONObject(0).getString("name");

                                shimmer4.stopShimmer();
                                shimmer4.setVisibility(View.GONE);
                                trendingRV.setVisibility(View.VISIBLE);

                                AlbumRVModal rvModal = new AlbumRVModal(songName,songArtist,id,songImageUrl);
                                albumRVModalArrayList.add(rvModal);
                            }

                            albumRVAdaptor.notifyDataSetChanged();
                            trendingTV.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }, error -> Toast.makeText(getActivity(), "Fail to get data of trending song : " + error, Toast.LENGTH_SHORT).show())
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String,String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    return headers;
                }
            };

            queue.add(trendingObjRequest);

        }

        private void initializePopularAlbum() {

            RecyclerView albumRV = view.findViewById(R.id.idRVPopularAlbums);
            ArrayList<AlbumRVModal> albumRVModalArrayList = new ArrayList<>();
            AlbumRVAdaptor albumRVAdaptor = new AlbumRVAdaptor(albumRVModalArrayList,getActivity());
            albumRV.setAdapter(albumRVAdaptor);

            String url = "https://saavn.me/modules?language=hindi,english";
            RequestQueue queue = Volley.newRequestQueue(requireActivity());

            @SuppressLint("NotifyDataSetChanged") JsonObjectRequest albumObjRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    response -> {

                        try {
                            JSONObject object = response.getJSONObject("data");
                            JSONArray albumArray = object.getJSONArray("albums");

                            for (int i = 0; i < albumArray.length(); i++)
                            {
                                JSONObject albumObj = albumArray.getJSONObject(i);
                                String id = albumObj.getString("id");
                                String albumName = albumObj.getString("name");
                                String albumImageUrl = albumObj.getJSONArray("image").getJSONObject(2).getString("link");
                                String albumUrl = albumObj.getString("url");
                                String albumType = albumObj.getString("type");
                                String artistName = "Arjun";
//                                String count = albumObj.getString("songCount");

                                switch (albumType) {
                                    case "song":
                                        artistName = albumObj.getJSONArray("primaryArtists").getJSONObject(0).getString("name");
                                        break;

                                    case "album":
                                        artistName = albumObj.getJSONArray("artists").getJSONObject(0).getString("name");
                                        break;

                                    case "playlist":

                                        break;
                                }


                                shimmer2.stopShimmer();
                                shimmer2.setVisibility(View.GONE);
                                albumRV.setVisibility(View.VISIBLE);

                                AlbumRVModal rvModal = new AlbumRVModal(albumName,artistName,albumUrl,id,albumImageUrl,albumType);
//                                rvModal.setSongCount(count);
                                albumRVModalArrayList.add(rvModal);

                            }
                            albumRVAdaptor.notifyDataSetChanged();
                            albumTV.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }, error -> Toast.makeText(getActivity(), "Fail to get data : " + error, Toast.LENGTH_SHORT).show())
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String,String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    return headers;
                }
            };

            queue.add(albumObjRequest);
        }




}