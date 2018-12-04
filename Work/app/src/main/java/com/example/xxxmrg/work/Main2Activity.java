package com.example.xxxmrg.work;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
//        Intent intent = getIntent();
//        String message = intent.getStringExtra("MESSAGE");
//
        TextView textView = findViewById(R.id.textView);
//        textView.setText(message);

        textView.setMovementMethod(ScrollingMovementMethod.getInstance());
        SensorManager manager;
        List<Sensor> sensorList;

        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorList = manager.getSensorList(Sensor.TYPE_ALL);
        Toast.makeText(this, sensorList.size() + "", Toast.LENGTH_LONG).show();
        //List<String> sensorNameList = new ArrayList<>();
        for (Sensor sensor : sensorList){
            String str = "设备名称: " + sensor.getName() + "\n" + "设备版本: " +
                    sensor.getVersion() + "\n" + "设备供应商: " + sensor.getVendor() + "\n";
            textView.append(str);
        }
    }



}
