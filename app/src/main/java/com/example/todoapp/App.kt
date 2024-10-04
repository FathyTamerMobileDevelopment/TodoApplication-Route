package com.example.todoapp

import android.app.Application
import android.content.res.Configuration.UI_MODE_NIGHT_MASK
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import com.example.todoapp.database.AppDatabase
import com.example.todoapp.utils.DARK_MODE_KEY
import com.example.todoapp.utils.SETTING_SP_NAME
import com.example.todoapp.utils.applyModeChange

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        AppDatabase.initDatabase(this)

        setNightLightMode()
    }

    private fun setNightLightMode() {
        val isDark = getSharedPreferences(SETTING_SP_NAME, MODE_PRIVATE)
            .getBoolean(DARK_MODE_KEY, getDeviceModeState())

        applyModeChange(isDark)
    }

    private fun getDeviceModeState():Boolean{
        val currentNightMode = resources.configuration.uiMode and UI_MODE_NIGHT_MASK

        return currentNightMode== UI_MODE_NIGHT_YES
    }
}