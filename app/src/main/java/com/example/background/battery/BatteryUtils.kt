package com.example.background.battery

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager

class BatteryUtils {
    fun getBatteryHealth(context: Context): String{
        val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        val batteryStatus = context.registerReceiver(null,intentFilter)
        val health = batteryStatus!!.getIntExtra(BatteryManager.EXTRA_HEALTH,BatteryManager.BATTERY_HEALTH_UNKNOWN)

        var status = ""
        when(health){
            BatteryManager.BATTERY_HEALTH_GOOD -> status = "GOOD"
            BatteryManager.BATTERY_HEALTH_OVERHEAT -> status = "OVERHEAT"
            BatteryManager.BATTERY_HEALTH_DEAD -> status = "HEALTH DEAD"
            BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE -> status = "OVER VOLTAGE"
            BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE -> status = "UNSPECIFIED FAILURE"
            BatteryManager.BATTERY_HEALTH_COLD -> status = "COLD"
        }

        return status
    }
}