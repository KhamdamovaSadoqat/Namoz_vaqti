package com.example.namozvaqti.data.prayerTime

import com.example.namozvaqti.data.comman.WrappedResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PrayerTimeApi {

    @GET("timings")
    suspend fun prayerTime(
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String,
//        @Query("method") method: Int,
//        @Query("school") school: Int,
    ): Response<WrappedResponse<PrayerTimeResponse>>
}