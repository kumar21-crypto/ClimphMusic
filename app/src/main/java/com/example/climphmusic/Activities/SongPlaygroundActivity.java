package com.example.climphmusic.Activities;


import androidx.appcompat.app.AppCompatActivity;
import static com.example.climphmusic.Services.MusicService.mediaPlayer;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.audiofx.AudioEffect;
import android.media.audiofx.BassBoost;
import android.media.audiofx.Equalizer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.climphmusic.Modal.SearchSongRVModal;
import com.example.climphmusic.Services.MusicService;
import com.example.climphmusic.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import static com.example.climphmusic.Adaptors.AlbumDetailSongRVAdaptor.songRVModalArrayList;

public class SongPlaygroundActivity extends AppCompatActivity implements ServiceConnection {

    public static String songImage, songUrl, songName, songArtist, songDuration;
    @SuppressLint("StaticFieldLeak")
    public static ImageView playgroundImage, previousActivity, songPrev, songNext,equalizer;
    public TextView playgroundSongName, playgroundSongArtist;
    @SuppressLint("StaticFieldLeak")
    public static ImageView play, pause;
    SeekBar seekBar;
    public String whatClass;

    Handler handler = new Handler();

    Bitmap currImage;
    AudioManager audioManager;
    int requestFocusRes,pos;
    AudioManager.OnAudioFocusChangeListener audioFocusChangeListener;
    public static MusicService musicService = null;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_playground);

        whatClass = getIntent().getStringExtra("class");
        pos = getIntent().getIntExtra("position",0);
        Toast.makeText(this, "position "+pos, Toast.LENGTH_SHORT).show();
        Objects.requireNonNull(getSupportActionBar()).hide();
        initiateVariables();
        getIntentFromActivity();
        initializeLayout();

        // for song play
        play.setOnClickListener(view -> {

            if (!mediaPlayer.isPlaying()) {
                play.setImageResource(R.drawable.baseline_pause_circle_filled_24);
                mediaPlayer.start();
            } else {
                mediaPlayer.pause();
                play.setImageResource(R.drawable.baseline_play_circle_filled_24);
            }
        });

        // for song next
        songNext.setOnClickListener(view -> {
            songNextMethod();
        });

        // for song previous
        songPrev.setOnClickListener(view -> {
            songPrevMethod();
        });

        // gog back to previous activity
        previousActivity.setOnClickListener(view -> {
            super.onBackPressed();
        });

        // equalizer click
        equalizer.setOnClickListener(view -> {
            Intent intent = new Intent(AudioEffect.ACTION_DISPLAY_AUDIO_EFFECT_CONTROL_PANEL);
            intent.putExtra(AudioEffect.EXTRA_AUDIO_SESSION,mediaPlayer.getAudioSessionId());
            intent.putExtra(AudioEffect.EXTRA_PACKAGE_NAME,getBaseContext().getPackageName());
            intent.putExtra(AudioEffect.EXTRA_CONTENT_TYPE,AudioEffect.CONTENT_TYPE_MUSIC);
            startActivityIfNeeded(intent,100);
        });

    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
        if(resultCode == 100 || resultCode==RESULT_OK){
            return;
        }
    }

    private void songPrevMethod() {
        mediaPlayer.reset();
        pos--;
        SearchSongRVModal songRVModal = songRVModalArrayList.get(pos);
        songUrl = songRVModal.getSongUrl();
        songImage = songRVModal.getSongImage();
        songName = songRVModal.getSongName();
        songArtist = songRVModal.getSongArtist();
        streamSong();
        Picasso.get().load(songRVModal.getSongImage()).into(playgroundImage);
        playgroundSongName.setText(songRVModal.getSongName());
        playgroundSongArtist.setText(songRVModal.getSongArtist());
        musicService.showNotification(R.drawable.baseline_pause_circle_filled_24);
    }

    private void songNextMethod() {

        mediaPlayer.reset();
        pos++;
        SearchSongRVModal songRVModal = songRVModalArrayList.get(pos);
        songUrl = songRVModal.getSongUrl();
        songImage = songRVModal.getSongImage();
        songName = songRVModal.getSongName();
        songArtist = songRVModal.getSongArtist();
        streamSong();
        Picasso.get().load(songRVModal.getSongImage()).into(playgroundImage);
        playgroundSongName.setText(songRVModal.getSongName());
        playgroundSongArtist.setText(songRVModal.getSongArtist());
        musicService.showNotification(R.drawable.baseline_pause_circle_filled_24);
    }


    private void initializeLayout() {

        if(Objects.equals(whatClass, "NowPlaying")){
            Toast.makeText(this, "duration " + mediaPlayer.getDuration() + " curr "+mediaPlayer.getCurrentPosition(), Toast.LENGTH_SHORT).show();

        }
        else if (Objects.equals(whatClass, "SearchSongAdaptor")) {

            Intent intent = new Intent(this,MusicService.class);
            bindService(intent,this,BIND_AUTO_CREATE);
            startService(intent);
            setAttributes();
        }
        else if (Objects.equals(whatClass, "AlbumDetailSongAdaptor")) {

            Intent intent1 = new Intent(this,MusicService.class);
            bindService(intent1,this,BIND_AUTO_CREATE);
            startService(intent1);
            setAttributes();

        }
        else{
            Intent intent1 = new Intent(this,MusicService.class);
            bindService(intent1,this,BIND_AUTO_CREATE);
            startService(intent1);
            setAttributes();
        }

    }


    private void initiateVariables() {
        playgroundImage = findViewById(R.id.songPlaygroundImage);
        playgroundSongName = findViewById(R.id.songPlaygroundSongName);
        playgroundSongArtist = findViewById(R.id.songPlaygroundSongArtist);
        songPrev = findViewById(R.id.songPlaygroundPrevious);
        songNext = findViewById(R.id.songPlaygroundNext);
        play = findViewById(R.id.songPlaygroundPlay);
        previousActivity = findViewById(R.id.songPlaygroundPreviousActivity);
        seekBar = findViewById(R.id.songPlaygroundSeekbar);
        handler = new Handler();
        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        equalizer = findViewById(R.id.songPlaygroundEqualizer);
    }

    private void getIntentFromActivity() {
        songImage = getIntent().getStringExtra("image");
        songUrl = getIntent().getStringExtra("url");
        songName = getIntent().getStringExtra("name");
        songArtist = getIntent().getStringExtra("singer");

    }

    private void streamSong() {

        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(songUrl);
            mediaPlayer.setOnPreparedListener(mediaPlayer -> {
                seekBar.setMax(mediaPlayer.getDuration());
                    mediaPlayer.start();
                    MainActivity.fragmentContainerView.setVisibility(View.VISIBLE);
//                        isPlaying = true;
                    new Timer().scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            seekBar.setProgress(mediaPlayer.getCurrentPosition());
                        }
                    }, 0, 1000);

                    play.setImageResource(R.drawable.baseline_pause_circle_filled_24);
                });

            mediaPlayer.prepareAsync();
            mediaPlayer.setOnBufferingUpdateListener((mediaPlayer, i) -> {
                double ratio = i / 100.0;
                int bufferingLevel = (int) (mediaPlayer.getDuration() * ratio);
                seekBar.setSecondaryProgress(bufferingLevel);
            });

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    Toast.makeText(SongPlaygroundActivity.this, "set on completion", Toast.LENGTH_SHORT).show();
                    songNextMethod();
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setAttributes() {
        Picasso.get().load(songImage).into(playgroundImage);
        playgroundSongName.setText(songName);
        playgroundSongArtist.setText(songArtist);

    }


    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        MusicService.MyBinder myBinder =(MusicService.MyBinder) iBinder;
        musicService = myBinder.getService();
        Toast.makeText(this, "connected", Toast.LENGTH_SHORT).show();
        streamSong();
        musicService.showNotification(R.drawable.baseline_pause_circle_filled_24);
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        musicService = null;
//        isPlaying = false;
        Toast.makeText(this, "disconnected", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        unbindService(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Intent intent = new Intent(this,MusicService.class);
//        bindService(intent,this,BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        unbindService(this);
    }
}