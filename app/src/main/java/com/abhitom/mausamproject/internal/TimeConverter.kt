package com.abhitom.mausamproject.internal

import java.text.SimpleDateFormat
import java.util.*

class TimeConverter {
    companion object {
        private var timeConverter: TimeConverter? = null

        val instance: TimeConverter
            get() {
                if (timeConverter == null) {
                    timeConverter = TimeConverter()
                }
                return timeConverter as TimeConverter
            }
    }
    fun convertToHour(time:Long): String {
        val timeInDate = Date(time)
        return SimpleDateFormat("HH").format(timeInDate)
    }
    fun convertToFormalTime(time:Long): String {
        val timeInDate = Date(time)
        return SimpleDateFormat("hh:mm a").format(timeInDate)
    }
    fun convertToDay(time:Long):String{
        val timeInDate = Date(time)
        return SimpleDateFormat("EEEE").format(timeInDate)
    }
    fun convertToDate(time:Long):String{
        val timeInDate = Date(time)
        return SimpleDateFormat("dd/MM").format(timeInDate)
    }
}