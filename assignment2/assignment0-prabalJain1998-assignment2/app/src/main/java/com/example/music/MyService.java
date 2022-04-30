package com.example.music;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class MyService extends Service{

    private MediaPlayer mediaPlayer1;
    private MediaPlayer mediaPlayer2;
    private MediaPlayer mediaPlayer3;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    public static final String CHANNEL_ID = "broadcast";
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
         if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(
                    CHANNEL_ID, "foreground Service", NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }

        Intent intent1 = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,intent1,0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_1)
                .setContentTitle("Music")
                .setContentText("Foreground Service Playing")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();
        startForeground(1,notification);
        play();
        return START_STICKY;
    }

    public void play(){
        mediaPlayer1 = MediaPlayer.create(getApplicationContext(), R.raw.songa);
        mediaPlayer2 = MediaPlayer.create(getApplicationContext(), R.raw.songb);
        mediaPlayer3 = MediaPlayer.create(getApplicationContext(), R.raw.songc);
        mediaPlayer1.setLooping(false);
        mediaPlayer2.setLooping(false);
        mediaPlayer3.setLooping(false);

        Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show();
        mediaPlayer1.start();
        mediaPlayer1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer1.stop();
                mediaPlayer2.start();
                mediaPlayer2.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer2.stop();
                        mediaPlayer3.start();
                        mediaPlayer3.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                            @Override
                            public void onPrepared(MediaPlayer player) {
                                mediaPlayer3.stop();
                            }

                        });
                    }

                });
            }
        });

    }

    /*
    public void onCreate(){
        Toast.makeText(this, "Service Created", Toast.LENGTH_SHORT).show();
        mediaPlayer1 = MediaPlayer.create(getApplicationContext(), R.raw.songa);
        mediaPlayer2 = MediaPlayer.create(getApplicationContext(), R.raw.songb);
        mediaPlayer3 = MediaPlayer.create(getApplicationContext(), R.raw.songc);
        mediaPlayer1.setLooping(false);
        mediaPlayer2.setLooping(false);
        mediaPlayer3.setLooping(false);

    }

    public void onStart(Intent intent, int startId){
        Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show();
        mediaPlayer1.start();
        mediaPlayer1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer1.stop();
                mediaPlayer2.start();
                mediaPlayer2.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer2.stop();
                        mediaPlayer3.start();
                        mediaPlayer3.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                            @Override
                            public void onPrepared(MediaPlayer player) {
                                mediaPlayer3.stop();
                            }

                        });
                    }

                });
            }
        });
    }

     */

    @Override
    public void onDestroy(){
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_SHORT).show();
        stopForeground(false);
        mediaPlayer1.stop();
        mediaPlayer2.stop();
        mediaPlayer3.stop();
        stopSelf();
        super.onDestroy();
    }


}
