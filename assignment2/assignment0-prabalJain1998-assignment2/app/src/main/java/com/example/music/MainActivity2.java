package com.example.music;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener{

    Button downloadButton;
    DownloadManager downloadManager;
    MediaPlayer mediaPlayer;
    File file;
    final String url = "https://faculty.iiitd.ac.in/~mukulika/s1.mp3";
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent = getIntent();

        downloadButton = (Button) findViewById(R.id.button);
        downloadButton.setOnClickListener(this);

        Button stopButton = findViewById(R.id.stopButton);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
            }
        });

        Button playButton = findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                file = new File(getApplicationContext().getFilesDir() + "/" + "s1.mp3");
                System.out.println(file);
                mediaPlayer = new MediaPlayer();
                try{
                    mediaPlayer.setDataSource(file.getPath());
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        public void onPrepared(MediaPlayer player) {
                            mediaPlayer.start();
                        }
                    });
                    mediaPlayer.prepareAsync();
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }
    private class DownloadSong extends AsyncTask<String,Integer,String > {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(MainActivity2.this);
            dialog.setTitle("Song");
            dialog.setMessage("Downloading....");
            dialog.setIndeterminate(false);
            dialog.setMax(100);
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... u) {
            try{
                URL url = new URL(u[0]);
                URLConnection urlConnection = url.openConnection();
                urlConnection.connect();
                int fileLength = urlConnection.getContentLength();
                FileOutputStream fileOutputStream = getApplicationContext().openFileOutput("s1.mp3", Context.MODE_PRIVATE);
                InputStream inputstream = new BufferedInputStream(url.openStream());
                byte data[] = new byte[fileLength];
                int total = 0;
                int count;
                while ((count = inputstream.read(data)) != -1)
                {
                    total += count;
                    publishProgress((int)(total*100/fileLength));
                    fileOutputStream.write(data,0,count);
                }
                //after completing the background jobs close all the streams
                inputstream.close();
                fileOutputStream.close();


            }catch(final Exception e)
            {
                e.printStackTrace();
            }

            return "Done";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            dialog.setProgress(values[0]);

        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
            System.out.println("Completed");
            file = new File(getApplicationContext().getFilesDir() + "/" + "s1.mp3");
            System.out.println(file);
            mediaPlayer = new MediaPlayer();
            try{
                mediaPlayer.setDataSource(file.getPath());
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    public void onPrepared(MediaPlayer player) {
                        mediaPlayer.start();
                    }
                });
                mediaPlayer.prepareAsync();
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }



    @Override
    public void onClick(View view) {
        if(view == downloadButton){
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mob = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if((wifi != null && wifi.isConnected())||(mob != null && mob.isConnected())) {
                Toast.makeText(this, "Internet is Connected", Toast.LENGTH_SHORT).show();
                DownloadSong downloadSong = new DownloadSong();
                downloadSong.execute(url);
            }
            else{
                Toast.makeText(this,"Internet is not Connected",Toast.LENGTH_SHORT).show();
            }
        }

    }
}