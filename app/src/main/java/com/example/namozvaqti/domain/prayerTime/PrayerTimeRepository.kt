package com.example.namozvaqti.domain.prayerTime

import com.example.namozvaqti.data.comman.WrappedResponse
import com.example.namozvaqti.data.prayerTime.PrayerTimeResponse
import com.example.namozvaqti.domain.base.BaseResult
import kotlinx.coroutines.flow.Flow

interface PrayerTimeRepository {
    suspend fun prayerTime(): Flow<BaseResult<PrayerTimeEntity, WrappedResponse<PrayerTimeResponse>>>
}