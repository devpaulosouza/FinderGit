package com.vinicius.findergit.about;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.vinicius.findergit.R;
import com.vinicius.findergit.base.BaseActivity;

public class AboutActivity extends BaseActivity implements SensorEventListener {

    private boolean eeMode = false;
    private SensorManager mSensorManager;

    ImageView ivEe;
    TextView tvEe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ivEe = (ImageView) findViewById(R.id.iv_kawaii);
        tvEe = (TextView) findViewById(R.id.tv_kawaii);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AboutActivity.super.onBackPressed();
            }
        });

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mSensorManager != null && !eeMode) {
            mSensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float values[] = sensorEvent.values;
        if (!eeMode && Math.abs(values[0])+Math.abs(values[1])+Math.abs(values[2]) > 130) {
            eeMode = true;
            ivEe.setVisibility(View.VISIBLE);
            tvEe.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
