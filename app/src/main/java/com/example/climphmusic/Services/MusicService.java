package com.example.climphmusic.Services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.climphmusic.Activities.SongPlaygroundActivity;
import com.example.climphmusic.Modal.SearchSongRVModal;
import com.example.climphmusic.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import static com.example.climphmusic.Adaptors.AlbumDetailSongRVAdaptor.songRVModalArrayList;


public class MusicService extends Service {

    public IBinder binder = new MyBinder();
    public static MediaPlayer mediaPlayer = new MediaPlayer();
    MediaSessionCompat mediaSessionCompat;
    Bitmap artNotificationImage;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        mediaSessionCompat = new MediaSessionCompat(this,"my music");
        return binder;
    }

    public class MyBinder extends Binder{

        public MusicService getService(){
            return MusicService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

     public void showNotification(int playPauseBtn){


            String imgLink = SongPlaygroundActivity.songImage;

             new Thread(()-> {
                 try {
                     artNotificationImage = Picasso.get().load(imgLink).get();
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }).start();



        // for previous action
         Intent prevIntent = new Intent(this, NotificationReceiver.class).setAction(ApplicationClass.ACTION_PREVIOUS);
         PendingIntent prevPendingIntent = PendingIntent.getBroadcast(this,0,prevIntent,PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

         // for next action
         Intent nextIntent = new Intent(this,NotificationReceiver.class).setAction(ApplicationClass.ACTION_NEXT);
         PendingIntent nextPendingIntent = PendingIntent.getBroadcast(this,0,nextIntent,PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

         // for play action
         Intent playIntent = new Intent(this,NotificationReceiver.class).setAction(ApplicationClass.ACTION_PLAY);
         PendingIntent playPendingIntent = PendingIntent.getBroadcast(this,0,playIntent,PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

         // for previous action
         Intent exitIntent = new Intent(this,NotificationReceiver.class).setAction(ApplicationClass.ACTION_EXIT);
         PendingIntent exitPendingIntent = PendingIntent.getBroadcast(this,0,exitIntent,PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

         Notification notification = new NotificationCompat.Builder(this,ApplicationClass.CHANNEL_ID_1)
                 .setContentTitle(SongPlaygroundActivity.songName)
                 .setContentText(SongPlaygroundActivity.songArtist)
                 .setSmallIcon(R.drawable.avatar_1)
                 .setLargeIcon(artNotificationImage)
                 .setStyle(new androidx.media.app.NotificationCompat.MediaStyle().setMediaSession(mediaSessionCompat.getSessionToken()))
                 .setPriority(NotificationCompat.PRIORITY_HIGH)
                 .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                 .setOnlyAlertOnce(true)
                 .addAction(R.drawable.baseline_skip_previous_24,"Previous",prevPendingIntent)
                 .addAction(playPauseBtn,"Play",playPendingIntent)
                 .addAction(R.drawable.baseline_skip_next_24,"Next",nextPendingIntent)
                 .addAction(R.drawable.baseline_close_24,"Exit",exitPendingIntent)
                 .build();

         startForeground(13,notification);
     }
}
