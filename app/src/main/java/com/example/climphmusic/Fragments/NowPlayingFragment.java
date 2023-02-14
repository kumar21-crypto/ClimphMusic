package com.example.climphmusic.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.climphmusic.Activities.SongPlaygroundActivity;
import com.example.climphmusic.R;
import com.example.climphmusic.databinding.FragmentNowPlayingBinding;
import com.squareup.picasso.Picasso;
import static com.example.climphmusic.Services.MusicService.mediaPlayer;


public class NowPlayingFragment extends Fragment {

    public static FragmentNowPlayingBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);
        binding = FragmentNowPlayingBinding.bind(view);

        binding.songNowPlayingPlay.setOnClickListener(view1 -> {
            if(mediaPlayer.isPlaying()){
                pauseMusic();
            }
            else{
                playMusic();
            }
        });

        binding.getRoot().setOnClickListener(view1 -> {
            Intent intent = new Intent(requireContext(),SongPlaygroundActivity.class);
            intent.putExtra("index",mediaPlayer.getCurrentPosition());
            intent.putExtra("class","NowPlaying");
            ContextCompat.startActivity(requireContext(),intent,null);
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mediaPlayer.isPlaying()){
            binding.getRoot().setVisibility(View.VISIBLE);

            binding.songNowPlayingName.setSelected(true);
            binding.songNowPlayingArtistName.setSelected(true);
            Picasso.get().load(SongPlaygroundActivity.songImage).placeholder(R.drawable.avatar_1)
                    .into(binding.songNowPlayingImg);
            binding.songNowPlayingName.setText(SongPlaygroundActivity.songName);
            binding.songNowPlayingArtistName.setText(SongPlaygroundActivity.songArtist);

            if(mediaPlayer.isPlaying()){
                binding.songNowPlayingPlay.setImageResource(R.drawable.ic_pause);
            }else{
                binding.songNowPlayingPlay.setImageResource(R.drawable.baseline_play_arrow_24);
            }
        }
    }

    private void playMusic(){
        mediaPlayer.start();
        binding.songNowPlayingPlay.setImageResource(R.drawable.ic_pause);
        SongPlaygroundActivity.musicService.showNotification(R.drawable.ic_pause);
        SongPlaygroundActivity.play.setImageResource(R.drawable.ic_pause);

    }

    private void pauseMusic(){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            binding.songNowPlayingPlay.setImageResource(R.drawable.baseline_play_arrow_24);
            SongPlaygroundActivity.musicService.showNotification(R.drawable.baseline_play_circle_filled_24);
            SongPlaygroundActivity.play.setImageResource(R.drawable.baseline_play_arrow_24);
        }

    }
}