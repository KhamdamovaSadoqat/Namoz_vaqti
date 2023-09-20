package com.example.namozvaqti.domain.prayerTime

import javax.inject.Inject

class PrayerTimeUseCase @Inject constructor(
    private val prayerTimeRepository: PrayerTimeRepository
) {

    suspend fun prayerTime() =
        prayerTimeRepository.prayerTime()
}