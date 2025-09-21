package com.faisalyousaf777.battstat;

public interface OnBatteryChangeListener {
    void onBatteryLevelChanged(int level);
    void onChargingStatusChanged(int status);
    void onHealthStatusChanged(int health);
    void onTemperatureChanged(float temperatureInDegreeCelsius);
    void onVoltageChanged(float voltage);
    void onTechnologyChanged(String technology);
    void onPluggedChanged(int plugged);
    void onCycleCountChanged(int cycleCount);
    void onCapacityLevelChanged(int capacityLevel);
}
