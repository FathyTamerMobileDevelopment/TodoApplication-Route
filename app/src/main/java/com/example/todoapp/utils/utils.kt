package com.example.todoapp.utils

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

fun getCurrentDeviceLanguageCode(context: Context):String{
    return context.resources.configuration.locales[0].language
}

fun applyModeChange(isDark: Boolean) {
    if (isDark)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    else
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
}