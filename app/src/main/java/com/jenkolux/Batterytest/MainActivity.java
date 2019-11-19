package com.jenkolux.Batterytest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;

import android.widget.TextView;

import com.github.lzyzsd.circleprogress.CircleProgress;


public class MainActivity extends AppCompatActivity {

    TextView chargingStatus,voltage,temperature,health,chargingSource;

    CircleProgress circleProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWork();
        loadBatteryInfo();
    }

    private void loadBatteryInfo() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(broadcastReceiver,intentFilter);

    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateBatteryData(intent);
        }
    };

    private void updateBatteryData(Intent intent) {
        //getChargingSource(intent);
        setLevel(intent);
        setVoltage(intent);
       // setType(intent);
        setHealth(intent);
        setChargingStatus(intent);
        //setTemperature(intent);

    }


    private void getChargingSource(Intent intent) {
        int sourceTemp = intent.getIntExtra("plugged",-1);

        switch (sourceTemp)
        {
            case BatteryManager.BATTERY_PLUGGED_AC:
                chargingSource.setText("AC");
                break;
            case BatteryManager.BATTERY_PLUGGED_USB:
                chargingSource.setText("USB");
                break;
            case BatteryManager.BATTERY_PLUGGED_WIRELESS:
                chargingSource.setText("WIRELESS");
                break;
            default:
                chargingSource.setText("Null");
                break;
        }
    }

    private void setTemperature(Intent intent) {
        float tempTemp = intent.getIntExtra("temperature",-1)/10;
        temperature.setText(tempTemp + " ℃");
    }

    private void setType(Intent intent) {
        intent.getStringExtra("technology");
    }

    private void setChargingStatus(Intent intent) {
        int statusTemp = intent.getIntExtra("status",-1);
        switch (statusTemp){
            case BatteryManager.BATTERY_STATUS_UNKNOWN:
                chargingStatus.setText("Unknown");
                break;
            case BatteryManager.BATTERY_STATUS_CHARGING:
                chargingStatus.setText("Charging");
                break;
            case BatteryManager.BATTERY_STATUS_DISCHARGING:
                chargingStatus.setText("Discharging");
                break;
            case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                chargingStatus.setText("NotCharging");
                break;
            case BatteryManager.BATTERY_STATUS_FULL:
                chargingStatus.setText("Charging Full");
            default:
                chargingStatus.setText("Null");
                break;
        }
    }


    private void setHealth(Intent intent) {
        int val = intent.getIntExtra("health",0);

        switch (val){
            case BatteryManager.BATTERY_HEALTH_COLD:
                health.setText("Cold");
                break;
            case BatteryManager.BATTERY_HEALTH_DEAD:
                health.setText("Dead");
                break;
            case  BatteryManager.BATTERY_HEALTH_GOOD:
                health.setText("Good");
                break;
            case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                 health.setText("Unknown");
                 break;
            case  BatteryManager.BATTERY_HEALTH_OVERHEAT:
                health.setText("Overheat");
                break;
            case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                health.setText("");
                break;
            case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                health.setText("Over Voltage");
                break;
        }
    }

    private void setVoltage(Intent intent) {
        final float vlotageTemp = (float) (intent.getIntExtra("voltage",0)*0.001);
        if(vlotageTemp == 0)return;

        voltage.setText(vlotageTemp + " v");
    }


    private void setLevel(Intent intent) {
        final int value = intent.getIntExtra("level",-1);
        if( value == -1){
            return;
        }
        circleProgress.setProgress(value);
    }


    private void initWork() {
        circleProgress = findViewById(R.id.circle_progress);
        chargingStatus = findViewById(R.id.chargingStatus);
        health = findViewById(R.id.health);
        voltage = findViewById(R.id.voltage);
    }


}
