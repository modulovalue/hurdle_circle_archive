package com.modestasv.hurdlecircle.android;

import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.modestasv.hurdlecircle.Game;
import com.modestasv.hurdlecircle.Interfaces.IProxable;


public class AndroidLauncher extends AndroidApplication implements IProxable, SensorEventListener {


    private SensorManager sensorManager;
    private Sensor sensor;

    private float proxValue = 0;

    private boolean hasProximitySensor;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        hasProximitySensor = this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_SENSOR_PROXIMITY);

        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useWakelock = true;
        initialize(new Game(this), cfg);

    }

    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.values[0]==0){
            proxValue = 1;
        }
        else{
            proxValue = 0;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    protected void onStop() {
        sensorManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    public float getProx() {

        if(hasProximitySensor) {

            return proxValue;
        } else {
            return -1;
        }
    }
}
