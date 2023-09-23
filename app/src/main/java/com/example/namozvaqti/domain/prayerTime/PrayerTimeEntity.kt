package com.example.namozvaqti.domain.prayerTime


data class PrayerTimeEntity (
    val fajr: String,
    val sunrise: String,
    val dhuhr: String,
    val asr: String,
    val sunset: String,
    val maghrib: String,
    val isha: String,
    val imsak: String,
    val midnight: String,
    val firstthird: String,
    val lastthird: String
    )