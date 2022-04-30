package com.example.music;
import android.content.BroadcastReceiver;
import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {
    public static final String CHANNEL_1_ID = "channel1";
    Intent serviceIntent;
    Button startServiceButton;
    Button stopServiceButton;
    Button checkInternetButton;

    public static final String TAG = "BroadcastReceiver";

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED)) {
                Toast.makeText(MainActivity.this, "" + "Power is Disconnected", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "POWER DISCONNECTED");
            }
            if (intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)) {
                Toast.makeText(MainActivity.this, "" + "Power is connected", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "POWER CONNECTED");
            }
            if(intent.getAction().equals(Intent.ACTION_BATTERY_LOW)){
                Toast.makeText(MainActivity.this, "" + "Battery is Low", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "BATTERY LOW");
            }
            if(intent.getAction().equals(Intent.ACTION_BATTERY_OKAY)){
                Toast.makeText(MainActivity.this, "" + "Battery is Okay", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "BATTERY OKAY");
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkInternetButton = findViewById(R.id.checkInternet);
        startServiceButton = findViewById(R.id.startServiceButton);
        stopServiceButton = findViewById(R.id.stopServiceButton);

        this.registerReceiver(this.receiver, new IntentFilter(Intent.ACTION_BATTERY_LOW));
        this.registerReceiver(this.receiver, new IntentFilter(Intent.ACTION_BATTERY_OKAY));
        this.registerReceiver(this.receiver, new IntentFilter(Intent.ACTION_POWER_DISCONNECTED));
        this.registerReceiver(this.receiver, new IntentFilter(Intent.ACTION_POWER_CONNECTED));

        startServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new FirstFragment());
                startForegroundService(new Intent(getApplicationContext(), MyService.class));
                }
        });

        stopServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new BlankFragment());
                stopService(new Intent(getApplicationContext(), MyService.class));
            }
        });

        checkInternetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });
    }

    public void loadFragment(Fragment fr){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fr);
        fragmentTransaction.commit();
    }






}