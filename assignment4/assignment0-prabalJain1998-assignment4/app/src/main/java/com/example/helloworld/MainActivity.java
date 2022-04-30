package com.example.helloworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.room.Room;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;


public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;

    Sensor accelerometerSensor, linearAccelerometerSensor, temperatureSensor, lightSensor, gpsSensor, proximitySensor, pressureSensor;

    //Initializing Variables for Motion
    private float[] mGravity;
    private double mAccel;
    private double mAccelCurrent;
    private double mAccelLast;
    private int hitCount = 0;
    private double hitSum = 0;
    private double hitResult = 0;
    private final int SAMPLE_SIZE = 50;
    private final double THRESHOLD = 0.1;

    private static final String TAG = "MainActivity";

    //Initializing Switches
    Switch accelerometerSwitch, linearAccelerationSwitch, temperatureSwitch, lightSwitch, gpsSwitch,proximitySwitch,pressureSwitch;

    //Initializing TextViews
    TextView accTextView, linearTextView, lightTextView, temperatureTextView, gpsTextView, proximityTextView, stateTextView, averageAccelerometerTextView, averageLightTextView, pressureTextView;

    //Initializing Button
    Button averageAccelerationButton, averageLightButton;

    //Initializing BooleanChecks
    Boolean accCheck, linearAccCheck, tempCheck, lightCheck, gpsCheck, proximityCheck, pressureCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        client = LocationServices.getFusedLocationProviderClient(getApplicationContext());
        database = RoomDB.getInstance(getApplicationContext());

        //Finding Text Views
        accTextView = findViewById(R.id.accTextView);
        linearTextView = findViewById(R.id.linearTextView);
        lightTextView = findViewById(R.id.lightTextView);
        temperatureTextView = findViewById(R.id.temperatureTextView);
        gpsTextView = findViewById(R.id.gpsTextView);
        proximityTextView = findViewById(R.id.proximityTextView);
        stateTextView = findViewById(R.id.stateTextView);
        averageAccelerometerTextView = findViewById(R.id.averageAccelerometerTextview);
        averageLightTextView = findViewById(R.id.averageLightTextView);
        pressureTextView = findViewById(R.id.pressureTextView);

        //Finding Switch Views
        accelerometerSwitch = (Switch) findViewById(R.id.accelerometer);
        linearAccelerationSwitch = (Switch) findViewById(R.id.linearAcceleration);
        temperatureSwitch = (Switch) findViewById(R.id.temperature);
        lightSwitch = (Switch) findViewById(R.id.light);
        gpsSwitch = (Switch) findViewById(R.id.gps);
        proximitySwitch = (Switch) findViewById(R.id.proximity);
        pressureSwitch = (Switch) findViewById(R.id.pressureSwitch);
        //Button
        averageAccelerationButton = findViewById(R.id.averageAccelerometerButton);
        averageLightButton = findViewById(R.id.averageLightButton);

        pressureSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                pressureCheck = isChecked;
                if (isChecked) {
                    mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
                    pressureSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
                    if(pressureSensor == null){
                        Toast.makeText(getApplicationContext(),"Pressure NOT Supported",Toast.LENGTH_SHORT).show();
                    }
                    mSensorManager.registerListener(MainActivity.this, pressureSensor, SensorManager.SENSOR_DELAY_NORMAL);
                } else {
                    pressureTextView.setText("");
                    mSensorManager.unregisterListener(MainActivity.this,pressureSensor);
                }
            }
        });
        accelerometerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                accCheck = isChecked;
                if (isChecked) {
                    mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
                    accelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                    if(accelerometerSensor == null){
                        Toast.makeText(getApplicationContext(),"Accelerometer NOT Supported",Toast.LENGTH_SHORT).show();
                    }
                    mAccel = 0.00f;
                    mAccelCurrent = SensorManager.GRAVITY_EARTH;
                    mAccelLast = SensorManager.GRAVITY_EARTH;
                    mSensorManager.registerListener(MainActivity.this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
                } else {
                    accTextView.setText("");
                    stateTextView.setText("");
                    mSensorManager.unregisterListener(MainActivity.this,accelerometerSensor);
                }
            }
        });

        averageAccelerationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Accelerometer> lst = database.accelerometerDao().data(System.currentTimeMillis()-3600000,System.currentTimeMillis());
                System.out.println(lst.size());
                long sumX = 0, sumY=0, sumZ=0;
                for(int i=0;i<lst.size();i++){
                    sumX += lst.get(i).getX();
                    sumY += lst.get(i).getY();
                    sumZ += lst.get(i).getZ();
                }
                sumX /= lst.size();
                sumY /= lst.size();
                sumZ /= lst.size();
                averageAccelerometerTextView.setText("Average is : X : "+sumX+" Y :"+sumY+" Z : "+sumZ);
            }
        });

        averageLightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Light> lst = database.lightDao().data(System.currentTimeMillis()-3600000,System.currentTimeMillis());
                System.out.println(lst.size());
                long sumL = 0;
                for(int i=0;i<lst.size();i++){
                    sumL += lst.get(i).getLight();
                }
                sumL /= lst.size();

                averageLightTextView.setText("Average is :"+sumL);
            }
        });

        linearAccelerationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                linearAccCheck = isChecked;
                if (isChecked) {
                    mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
                    linearAccelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
                    if(linearAccelerometerSensor == null){
                        Toast.makeText(getApplicationContext(),"Linear-Accelerometer NOT Supported",Toast.LENGTH_SHORT).show();
                    }
                    mSensorManager.registerListener(MainActivity.this, linearAccelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);

                } else {
                    linearTextView.setText("");
                    mSensorManager.unregisterListener(MainActivity.this,linearAccelerometerSensor);
                }
            }
        });

        lightSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                lightCheck = isChecked;
                if (isChecked) {
                    mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
                    lightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
                    if(lightSensor == null){
                        Toast.makeText(getApplicationContext(),"Light Sensor NOT Supported",Toast.LENGTH_SHORT).show();
                    }
                    mSensorManager.registerListener(MainActivity.this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);

                } else {
                    lightTextView.setText("");
                    mSensorManager.unregisterListener(MainActivity.this,lightSensor);
                }
            }
        });

        temperatureSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                tempCheck = isChecked;
                if (isChecked) {
                    mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
                    temperatureSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
                    if (temperatureSensor == null) {
                        Toast.makeText(getApplicationContext(), "Temperature Sensor Not Found", Toast.LENGTH_SHORT).show();
                    }
                    mSensorManager.registerListener(MainActivity.this, temperatureSensor, SensorManager.SENSOR_DELAY_NORMAL);

                } else {
                    temperatureTextView.setText("");
                    mSensorManager.unregisterListener(MainActivity.this,temperatureSensor);
                }
            }
        });

        gpsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                gpsCheck = isChecked;
                if (isChecked) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                                ContextCompat.checkSelfPermission(getApplicationContext(),
                                        Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                            //When permission is granted call method
                            getCurrentLocation();
                        } else {
                            //When permission not granted
                            //Request permission
                            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
                        }
                    }

                }else{
                    gpsTextView.setText("");
                }
            }

        });

        proximitySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                proximityCheck = isChecked;
                if(isChecked){
                    mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
                    proximitySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
                    if(proximitySensor == null){
                        Toast.makeText(getApplicationContext(),"Proximity Sensor NOT Supported",Toast.LENGTH_SHORT).show();
                    }
                    mSensorManager.registerListener(MainActivity.this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);

                }else{
                    proximityTextView.setText("");
                    mSensorManager.unregisterListener(MainActivity.this,proximitySensor);
                }
            }
        });


    }




    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    Location location;
    LocationRequest locationRequest;

    public void checkPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ){//Can add more as per requirement

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                    123);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //check condition

        if(requestCode ==100 && grantResults.length > 0&&
                ( grantResults[0]+grantResults[1])==PackageManager.PERMISSION_GRANTED){

            //When permissions are granted
            //Call method
            getCurrentLocation();
        }
        else {
            Toast.makeText(getApplicationContext(),"Permission Denied....",Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        LocationManager locationManager = (LocationManager) this.
                getSystemService(Context.LOCATION_SERVICE);


        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                checkPermission();
            }
            client.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>(){
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    location = task.getResult();
                    if(location != null){
                        float lat = (float)location.getLatitude();
                        float lon = (float)(location.getLongitude());
                        GPS g = new GPS();
                        g.setLatitude(lat);
                        g.setLongitude(lon);
                        database.gpsDao().insert(g);
                        Toast.makeText(getApplicationContext(),lat+"  "+lon,Toast.LENGTH_SHORT).show();
                        gpsTextView.setText("Latitude : "+lat+" Longitude : "+lon);
                    }
                    else{
                        locationRequest = new LocationRequest()
                                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                .setInterval(10000)
                                .setFastestInterval(1000)
                                .setNumUpdates(1);

                        //Initialise Location callBack
                        LocationCallback locationCallback = new LocationCallback(){
                            @Override
                            public void onLocationResult(LocationResult result){
                                location = result.getLastLocation();
                                //get longitude and latitude
                                  float lat = (float)location.getLatitude();
                                  float lon = (float)(location.getLongitude());
                                GPS g = new GPS();
                                g.setLatitude(lat);
                                g.setLongitude(lon);
                                database.gpsDao().insert(g);
                                Toast.makeText(getApplicationContext(),lat+"  "+lon,Toast.LENGTH_SHORT).show();
                                gpsTextView.setText("Latitude : "+lat+" Longitude : "+lon);
                            }
                        };

                        //Request location updates
                        client.requestLocationUpdates(locationRequest,locationCallback, Looper.myLooper());
                    }

                }
            });
        }
        else{

            //When locationservice is not enables
            //Open settings
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }


    float acc_X, acc_y,acc_z;
    RoomDB database;
    FusedLocationProviderClient client;

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensor = sensorEvent.sensor;
        if((sensor.getType() == Sensor.TYPE_ACCELEROMETER) && accCheck){
             acc_X = sensorEvent.values[0];
             acc_y = sensorEvent.values[1];
             acc_z = sensorEvent.values[2];

            Accelerometer accelerometer = new Accelerometer();
            accelerometer.setId(System.currentTimeMillis());
            accelerometer.setX(acc_X);
            accelerometer.setY(acc_y);
            accelerometer.setZ(acc_z);

            database.accelerometerDao().insert(accelerometer);

            String total = "X: "+acc_X+" Y: "+acc_y+" Z: "+acc_z;
            accTextView.setText(total);


            //Code for Stationary or Walking
            mGravity = sensorEvent.values.clone();
            double x = mGravity[0];
            double y = mGravity[1];
            double z = mGravity[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = Math.sqrt(x * x + y * y + z * z);
            double delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;

            if (hitCount <= SAMPLE_SIZE) {
                hitCount++;
                hitSum += Math.abs(mAccel);
            } else {
                hitResult = hitSum / SAMPLE_SIZE;

                Log.d(TAG, String.valueOf(hitResult));

                if (hitResult > THRESHOLD) {
                   stateTextView.setText("Moving");
                } else {
                    stateTextView.setText("Stationary");
                }

                hitCount = 0;
                hitSum = 0;
                hitResult = 0;

        }
        }else if((sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) && linearAccCheck){
            float l_X = sensorEvent.values[0];
            float l_y = sensorEvent.values[1];
            float l_z = sensorEvent.values[2];
            linearTextView.setText("X : "+l_X+" Y: "+l_y+" Z: "+l_z);
            LinearAccelerometer linearAccelerometer = new LinearAccelerometer();
            linearAccelerometer.setId(System.currentTimeMillis());
            linearAccelerometer.setX(l_X);
            linearAccelerometer.setY(l_y);
            linearAccelerometer.setZ(l_z);
            database.linearAccelerometerDao().insert(linearAccelerometer);
        }else if((sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) && tempCheck){
            temperatureTextView.setText("Temperature is :"+ sensorEvent.values[0]);
            Temperature temp = new Temperature();
            temp.setId(System.currentTimeMillis());
            temp.setTemp(sensorEvent.values[0]);
            database.temperatureDao().insert(temp);

        }else if((sensor.getType() == Sensor.TYPE_LIGHT) && lightCheck){
            lightTextView.setText("Light : "+sensorEvent.values[0]);
            float light_value = sensorEvent.values[0];
            Light light = new Light();
            light.setId(System.currentTimeMillis());
            light.setLight(light_value);
            database.lightDao().insert(light);

        }
        else if((sensor.getType() == Sensor.TYPE_PROXIMITY)&& proximityCheck){
                proximityTextView.setText("Proximity Working : "+sensorEvent.values[0]);
                Proximity proximity = new Proximity();
                proximity.setId(System.currentTimeMillis());
                proximity.setDistance(sensorEvent.values[0]);
                database.proximityDao().insert(proximity);

        }
        else if((sensor.getType() == Sensor.TYPE_PRESSURE) && pressureCheck){
            pressureTextView.setText("Pressure is :"+sensorEvent.values[0]);
            Pressure pressure = new Pressure();
            pressure.setId(System.currentTimeMillis());
            pressure.setPressure(sensorEvent.values[0]);
            database.pressureDao().insert(pressure);

        }
        else{
                //do nothing Let's Dance
        }
    }


}
