package com.example.todoapp.utils

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun Calendar.ignoreTime() {
    set(Calendar.HOUR_OF_DAY, 0)
    set(Calendar.MINUTE, 0)
    set(Calendar.SECOND, 0)
    set(Calendar.MILLISECOND, 0)
}

fun Calendar.ignoreDate() {
    set(Calendar.DAY_OF_MONTH, 0)
    set(Calendar.MONTH, 0)
    set(Calendar.YEAR, 0)
}

fun Calendar.setDate(year: Int, month: Int, day: Int) {
    set(Calendar.YEAR, year)
    set(Calendar.MONTH, month)
    set(Calendar.DAY_OF_MONTH, day)
}

fun Calendar.setTime(hour: Int, minute: Int) {
    set(Calendar.HOUR_OF_DAY, hour)
    set(Calendar.MINUTE, minute)
}

fun Calendar.formatDateOnly(): String {
    val dateFormat = SimpleDateFormat("EEE, dd MM yyyy", Locale.ENGLISH)
    return dateFormat.format(time)
}

fun Calendar.formatTimeOnly(): String {
    val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return timeFormat.format(this.time)
}

fun Long.formatTimeOnly(): String {
    val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return timeFormat.format(this)
}

fun Long.formatDateOnly(): String {
    val dateFormat = SimpleDateFormat("EEE, dd MM yyyy", Locale.ENGLISH)
    return dateFormat.format(this)
}


fun Context.showTimePicker(
    calendar: Calendar,
    onTimeSet: (hour: Int, minute: Int) -> Unit
) {
    TimePickerDialog(
        this, { _, hour, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            onTimeSet(hour, minute)
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        false
    ).show()
}

fun Context.showDatePicker(
    calendar: Calendar,
    onDateSet: (year: Int, month: Int, day: Int) -> Unit
) {
    DatePickerDialog(
        this,
        { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            onDateSet(year, month, dayOfMonth)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).show()
}