package com.example.climphmusic.Services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.climphmusic.Activities.SongPlaygroundActivity;
import com.example.climphmusic.Fragments.NowPlayingFragment;
import com.example.climphmusic.R;
import com.example.climphmusic.Services.ApplicationClass;
import com.example.climphmusic.Services.MusicService;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        switch (intent.getAction()){

            case ApplicationClass.ACTION_PREVIOUS:
                Toast.makeText(context, "previous clicked", Toast.LENGTH_SHORT).show();
                break;

            case ApplicationClass.ACTION_PLAY:
                Toast.makeText(context, "play clicked", Toast.LENGTH_SHORT).show();
                if(MusicService.mediaPlayer.isPlaying()){
                    Toast.makeText(context, "song pause", Toast.LENGTH_SHORT).show();
                    pauseMusic();
                }
                else{
                    Toast.makeText(context, "song play", Toast.LENGTH_SHORT).show();
                    playMusic();
                }
                break;

            case ApplicationClass.ACTION_NEXT:
                Toast.makeText(context, "next clicked", Toast.LENGTH_SHORT).show();
                break;

            case ApplicationClass.ACTION_EXIT:
                Toast.makeText(context, "exit clicked", Toast.LENGTH_SHORT).show();

                break;
        }


    }

    private void playMusic(){
//        SongPlaygroundActivity.isPlaying = true;
        if(SongPlaygroundActivity.musicService.mediaPlayer != null){
            SongPlaygroundActivity.musicService.mediaPlayer.start();
            SongPlaygroundActivity.musicService.showNotification(R.drawable.baseline_pause_circle_filled_24);
            SongPlaygroundActivity.play.setImageResource(R.drawable.baseline_pause_circle_filled_24);
            NowPlayingFragment.binding.songNowPlayingPlay.setImageResource(R.drawable.baseline_pause_circle_filled_24);
        }


    }

    private void pauseMusic(){
//        SongPlaygroundActivity.isPlaying = false;
        if(SongPlaygroundActivity.musicService.mediaPlayer != null){
            SongPlaygroundActivity.musicService.mediaPlayer.pause();
            SongPlaygroundActivity.musicService.showNotification(R.drawable.baseline_play_circle_filled_24);
            SongPlaygroundActivity.play.setImageResource(R.drawable.baseline_play_circle_filled_24);
            NowPlayingFragment.binding.songNowPlayingPlay.setImageResource(R.drawable.baseline_play_arrow_24);
        }


    }
}
