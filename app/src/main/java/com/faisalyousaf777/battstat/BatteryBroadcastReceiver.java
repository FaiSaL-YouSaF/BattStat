package com.faisalyousaf777.battstat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.Build;

public class BatteryBroadcastReceiver extends BroadcastReceiver {

    private OnBatteryChangeListener listener;

    public void setListener(OnBatteryChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, -1);
            int temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1);
            int voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1);
            String technology = intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY);
            int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                int cycleCount = intent.getIntExtra(BatteryManager.EXTRA_CYCLE_COUNT, -1);
                if (listener != null) {
                    listener.onCycleCountChanged(cycleCount);
                }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.BAKLAVA) {
                int capacity = intent.getIntExtra(BatteryManager.EXTRA_CAPACITY_LEVEL, -1);
                if (listener != null) {
                    listener.onCapacityLevelChanged(capacity);
                }
            }

            if (listener != null) {
                listener.onBatteryLevelChanged(level);
                listener.onChargingStatusChanged(status);
                listener.onHealthStatusChanged(health);
                listener.onTemperatureChanged(temperature / 10f); // Convert to degree Celsius
                listener.onVoltageChanged(voltage / 1000f); // Convert to volts
                listener.onTechnologyChanged(technology);
                listener.onPluggedChanged(plugged);
            }
        }
    }
}
