package com.example.climphmusic.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.TextLinks;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.climphmusic.Adaptors.PlaylistRVAdaptor;
import com.example.climphmusic.Adaptors.TrendingRVAdaptor;
import com.example.climphmusic.Modal.AlbumRVModal;
import com.example.climphmusic.Modal.TrendingRVModal;
import com.example.climphmusic.R;
import com.example.climphmusic.databinding.FragmentTrendingBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.DocumentType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class TrendingFragment extends Fragment {

    public TrendingFragment() {
    }

    FragmentTrendingBinding fragmentTrendingBinding;
    JSONObject objectRV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentTrendingBinding = FragmentTrendingBinding.bind(inflater.inflate(R.layout.fragment_trending, container, false));
        fragmentTrendingBinding.TPShimmer.startShimmer();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 3);
        fragmentTrendingBinding.TPRV.setLayoutManager(gridLayoutManager);


        getTrending();


        return fragmentTrendingBinding.getRoot();
    }

//    @SuppressLint("NotifyDataSetChanged")
//    private void initializeTrendingPage() throws IOException, JSONException {
//        ArrayList<TrendingRVModal> trendingRVModalArrayList = new ArrayList<>();
//        TrendingRVAdaptor trendingRVAdaptor = new TrendingRVAdaptor(trendingRVModalArrayList, requireContext());
//        fragmentTrendingBinding.TPRV.setAdapter(trendingRVAdaptor);
//
//        String url = "https://www.jiosaavn.com/api.php?__call=webapi.getLaunchData&format=json&marker=0&api_version=4&ctx=web6dot0";
//
//        OkHttpClient httpClient = new OkHttpClient();
//        okhttp3.Request request = new okhttp3.Request.Builder()
//                .url(url)
//                .build();
//
//        okhttp3.Response res = httpClient.newCall(request).execute();
//        String responseBody = res.body().string();
//
//        // Remove DOCTYPE html declaration
//        responseBody = responseBody.replaceAll("<!DOCTYPE html>", "");
//
//
//        JSONObject jsonObject = new JSONObject(responseBody);
//
//
//        JSONArray trendingArray = null;
//        try {
//
//
//        } catch (JSONException e){
//            Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
//    }

    private void getTrending(){

        ArrayList<TrendingRVModal> trendingRVModalArrayList = new ArrayList<>();
        TrendingRVAdaptor trendingRVAdaptor = new TrendingRVAdaptor(trendingRVModalArrayList, requireContext());
        fragmentTrendingBinding.TPRV.setAdapter(trendingRVAdaptor);

        String urlMain = "https://www.jiosaavn.com/api.php?__call=webapi.getLaunchData&format=json&marker=0&api_version=4&ctx=web6dot0";

        Thread thread = new Thread(new Runnable() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void run() {
                try {

                    URL url = new URL(urlMain);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setDoOutput(true);
                    connection.connect();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while( (line = reader.readLine()) != null){
                        if(!line.contains("<!DOCTYPE html>"))
                        {
                            if(!line.startsWith("<!--") && !line.endsWith("-->")){
                                result.append(line);
                            }
                            
                        }
                    }



                    String response = result.toString();
                    Log.d("response",response);
                    JSONObject object = new JSONObject(response);

                     JSONArray trendingArray = object.getJSONArray("new_trending");
                    Toast.makeText(requireContext(), "trending", Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < trendingArray.length(); i++) {

                        JSONObject object1 = trendingArray.getJSONObject(i);

                        String id = object1.getString("id");
                        String albumName = object1.getString("title");
                        String albumImage = object1.getString("image");
                        String albumType = object1.getString("type");
                        String albumArtist = object1.getString("subtitle");
                        String albumUrl = object1.getString("perma_url");

                        fragmentTrendingBinding.TPShimmer.stopShimmer();
                        fragmentTrendingBinding.TPShimmer.setVisibility(View.GONE);
                        fragmentTrendingBinding.TPRV.setVisibility(View.VISIBLE);

                        TrendingRVModal rvModal = new TrendingRVModal(albumImage, albumName, albumArtist, id, albumUrl, albumType);
                        trendingRVModalArrayList.add(rvModal);

                    }
                    trendingRVAdaptor.notifyDataSetChanged();




                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();



    }

    private void getData() throws IOException {

        ArrayList<TrendingRVModal> trendingRVModalArrayList = new ArrayList<>();
        TrendingRVAdaptor trendingRVAdaptor = new TrendingRVAdaptor(trendingRVModalArrayList, requireContext());
        fragmentTrendingBinding.TPRV.setAdapter(trendingRVAdaptor);

        final OkHttpClient client = new OkHttpClient();
        String url = "https://www.jiosaavn.com/api.php?__call=webapi.getLaunchData&format=json&marker=0&api_version=4&ctx=web6dot0";
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type","application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(requireActivity(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try{
                    if(response.isSuccessful()){
                        requireActivity().runOnUiThread(new Runnable() {
                            @SuppressLint("NotifyDataSetChanged")
                            @Override
                            public void run() {
                                Toast.makeText(requireActivity(), "response successful", Toast.LENGTH_SHORT).show();
                                try {
                                    String responseBody = response.body().string();
                                    responseBody = responseBody.replaceAll("<!DOCTYPE html>", "");
                                    JSONObject jsonObject = new JSONObject(responseBody);

                                    JSONArray trendingArray = jsonObject.getJSONArray("new_trending");
                                    Toast.makeText(requireContext(), "trending", Toast.LENGTH_SHORT).show();
                                    for (int i = 0; i < trendingArray.length(); i++) {

                                        JSONObject object1 = trendingArray.getJSONObject(i);

                                        String id = object1.getString("id");
                                        String albumName = object1.getString("title");
                                        String albumImage = object1.getString("image");
                                        String albumType = object1.getString("type");
                                        String albumArtist = object1.getString("subtitle");
                                        String albumUrl = object1.getString("perma_url");

                                        fragmentTrendingBinding.TPShimmer.stopShimmer();
                                        fragmentTrendingBinding.TPShimmer.setVisibility(View.GONE);
                                        fragmentTrendingBinding.TPRV.setVisibility(View.VISIBLE);

                                        TrendingRVModal rvModal = new TrendingRVModal(albumImage, albumName, albumArtist, id, albumUrl, albumType);
                                        trendingRVModalArrayList.add(rvModal);

                                    }
                                    trendingRVAdaptor.notifyDataSetChanged();
                                    
                                } catch (IOException | JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                    }

                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });



    }
}