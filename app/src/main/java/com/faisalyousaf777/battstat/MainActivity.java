package com.faisalyousaf777.battstat;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.appbar.MaterialToolbar;

public class MainActivity extends AppCompatActivity implements OnBatteryChangeListener {

    private CustomCircularProgress customCircularProgress;
    private TextView tvLevel, tvChargingStatus, tvTemperature, tvHealthStatus, tvTechnology, tvVoltage, tvPlugType, tvCycleCount, tvCapacityLevel;
    private ImageView ivChargingIndicator, ivTemperatureIcon, ivHealthIcon, ivTechnologyIcon, ivVoltageIcon, ivPluggedIcon, ivChargingStatusIcon;
    private BatteryBroadcastReceiver batteryBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize your views
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        customCircularProgress = findViewById(R.id.circular_progress_battery_percentage);
        GridLayout gridLayout = findViewById(R.id.layout_grid);
        tvLevel = findViewById(R.id.tv_battery_level);
        tvChargingStatus = findViewById(R.id.tv_battery_status);
        tvTemperature = findViewById(R.id.tv_battery_temperature);
        tvHealthStatus = findViewById(R.id.tv_battery_health);
        tvTechnology = findViewById(R.id.tv_battery_technology);
        tvVoltage = findViewById(R.id.tv_battery_voltage);
        tvPlugType = findViewById(R.id.tv_battery_plug_type);
        tvCycleCount = findViewById(R.id.tv_battery_cycle_count);
        tvCapacityLevel = findViewById(R.id.tv_battery_capacity_level);
        ivChargingIndicator = findViewById(R.id.iv_charging_indicator_icon);
        ivTemperatureIcon = findViewById(R.id.iv_temperature_icon);
        ivHealthIcon = findViewById(R.id.iv_health_icon);
        ivTechnologyIcon = findViewById(R.id.iv_technology_icon);
        ivVoltageIcon = findViewById(R.id.iv_voltage_icon);
        ivPluggedIcon = findViewById(R.id.iv_plug_type_icon);
        ivChargingStatusIcon = findViewById(R.id.iv_status_icon);
        LinearLayout layoutCycleCount = findViewById(R.id.layout_cycle_count_info);
        LinearLayout layoutCapacityLevel = findViewById(R.id.layout_capacity_level_info);

        batteryBroadcastReceiver = new BatteryBroadcastReceiver();
        batteryBroadcastReceiver.setListener(this);


        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.BAKLAVA) {
            gridLayout.removeView(layoutCapacityLevel);  // Remove Capacity Level

            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                gridLayout.removeView(layoutCycleCount);  // Remove Cycle Count
            }
        }
    }

    @Override
    public void onBatteryLevelChanged(int level) {
        tvLevel.setText(getString(R.string.battery_percentage_format, level));
        customCircularProgress.setProgress(level);
    }

    @Override
    public void onChargingStatusChanged(int status) {
        switch (status) {
            case BatteryManager.BATTERY_STATUS_UNKNOWN:
                tvChargingStatus.setText(String.format(getString(R.string.battery_charging_status_format), getString(R.string.unknown)));
                ivChargingStatusIcon.setImageResource(R.drawable.battery_unknown_48dp);
                break;
            case BatteryManager.BATTERY_STATUS_CHARGING:
                tvChargingStatus.setText(String.format(getString(R.string.battery_charging_status_format), getString(R.string.charging)));
                ivChargingStatusIcon.setImageResource(R.drawable.battery_charging_50_48dp);
                break;
            case BatteryManager.BATTERY_STATUS_DISCHARGING:
                tvChargingStatus.setText(String.format(getString(R.string.battery_charging_status_format), getString(R.string.discharging)));
                ivChargingStatusIcon.setImageResource(R.drawable.battery_discharging_48dp);
                break;
            case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                tvChargingStatus.setText(String.format(getString(R.string.battery_charging_status_format), getString(R.string.not_charging)));
                ivChargingStatusIcon.setImageResource(R.drawable.battery_not_charging_48dp);
                break;
            case BatteryManager.BATTERY_STATUS_FULL:
                tvChargingStatus.setText(String.format(getString(R.string.battery_charging_status_format), getString(R.string.full)));
                ivChargingStatusIcon.setImageResource(R.drawable.battery_full_charging_48dp);
                break;
            default:
                tvChargingStatus.setText(String.format(getString(R.string.battery_charging_status_format), getString(R.string.default_charging_status)));
        }
    }

    @Override
    public void onHealthStatusChanged(int health) {
        tvHealthStatus.setText(String.valueOf(health));
        switch (health) {
            case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                tvHealthStatus.setText(String.format(getString(R.string.battery_health_format), getString(R.string.unknown)));
                break;
            case BatteryManager.BATTERY_HEALTH_GOOD:
                tvHealthStatus.setText(String.format(getString(R.string.battery_health_format), getString(R.string.good)));
                ivHealthIcon.setImageResource(R.drawable.heart_check_48dp);
                break;
            case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                tvHealthStatus.setText(String.format(getString(R.string.battery_health_format), getString(R.string.over_heat)));
                break;
            case BatteryManager.BATTERY_HEALTH_DEAD:
                tvHealthStatus.setText(String.format(getString(R.string.battery_health_format), getString(R.string.dead)));
                ivHealthIcon.setImageResource(R.drawable.heart_broken_48dp);
                break;
            case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                tvHealthStatus.setText(String.format(getString(R.string.battery_health_format), getString(R.string.over_voltage)));
                break;
            case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                tvHealthStatus.setText(String.format(getString(R.string.battery_health_format), getString(R.string.unspecified_failure)));
                break;
            case BatteryManager.BATTERY_HEALTH_COLD:
                tvHealthStatus.setText(String.format(getString(R.string.battery_health_format), getString(R.string.cold)));
                break;
        }
    }

    @Override
    public void onTemperatureChanged(float temperatureInDegreeCelsius) {
        tvTemperature.setText(String.format(getString(R.string.battery_temperature_format), temperatureInDegreeCelsius));
        ivTemperatureIcon.setImageResource(R.drawable.device_thermostat_48dp_400);
    }

    @Override
    public void onVoltageChanged(float voltage) {
        tvVoltage.setText(String.format(getString(R.string.battery_voltage_format), voltage));
    }

    @Override
    public void onTechnologyChanged(String technology) {
        tvTechnology.setText(String.format(getString(R.string.battery_technology_format), technology));
        ivTechnologyIcon.setImageResource(R.drawable.memory_48dp);
    }

    @Override
    public void onPluggedChanged(int plugged) {
        switch (plugged) {
            case BatteryManager.BATTERY_PLUGGED_AC:
                tvPlugType.setText(String.format(getString(R.string.battery_plugged_format), getString(R.string.ac)));
                ivPluggedIcon.setImageResource(R.drawable.electrical_services_48dp);
                ivChargingIndicator.setVisibility(ImageView.VISIBLE);
                break;
            case BatteryManager.BATTERY_PLUGGED_USB:
                tvPlugType.setText(String.format(getString(R.string.battery_plugged_format), getString(R.string.usb)));
                ivPluggedIcon.setImageResource(R.drawable.usb_48dp);
                ivChargingIndicator.setVisibility(ImageView.VISIBLE);
                break;
            case BatteryManager.BATTERY_PLUGGED_WIRELESS:
                tvPlugType.setText(String.format(getString(R.string.battery_plugged_format), getString(R.string.wireless)));
                ivPluggedIcon.setImageResource(R.drawable.lightning_stand_48dp);
                ivChargingIndicator.setVisibility(ImageView.VISIBLE);
                break;
            case BatteryManager.BATTERY_PLUGGED_DOCK:
                tvPlugType.setText(String.format(getString(R.string.battery_plugged_format), getString(R.string.dock)));
                ivPluggedIcon.setImageResource(R.drawable.device_hub_48dp);
                ivChargingIndicator.setVisibility(ImageView.VISIBLE);
                break;
            default:
                tvPlugType.setText(String.format(getString(R.string.battery_plugged_format), getString(R.string.default_plugged_status)));
                ivPluggedIcon.setImageResource(R.drawable.power_off_48dp);
                ivChargingIndicator.setVisibility(ImageView.INVISIBLE);
                break;
        }
    }

    @Override
    public void onCycleCountChanged(int cycleCount) {
        tvCycleCount.setText(String.valueOf(cycleCount));
    }

    @Override
    public void onCapacityLevelChanged(int capacityLevel) {
        tvCapacityLevel.setText(String.valueOf(capacityLevel));
    }


    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(batteryBroadcastReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryBroadcastReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(batteryBroadcastReceiver);
    }
}