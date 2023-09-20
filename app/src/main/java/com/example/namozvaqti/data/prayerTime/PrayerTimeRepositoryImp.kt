package com.example.namozvaqti.data.prayerTime

import com.example.namozvaqti.data.comman.WrappedResponse
import com.example.namozvaqti.domain.prayerTime.PrayerTimeRepository
import com.example.namozvaqti.domain.base.BaseResult
import com.example.namozvaqti.domain.prayerTime.PrayerTimeEntity
import com.example.namozvaqti.utils.SharedPref
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PrayerTimeRepositoryImp @Inject constructor(
    private var shared: SharedPref,
    private val prayerTimeApi: PrayerTimeApi,
) : PrayerTimeRepository {
    override suspend fun prayerTime(): Flow<BaseResult<PrayerTimeEntity, WrappedResponse<PrayerTimeResponse>>> {
        val response = prayerTimeApi.prayerTime(shared.city!!, shared.country!!, 0, 1)
        return flow {
            if (response.isSuccessful) {
                val body = response.body()!!
                // checking body data for not being null
                if (body.data != null) {
                    val prayerTime = PrayerTimeEntity(
                        body.data?.timings?.fajr?:"",
                        body.data?.timings?.sunrise?:"",
                        body.data?.timings?.dhuhr?:"",
                        body.data?.timings?.asr?:"",
                        body.data?.timings?.sunset?:"",
                        body.data?.timings?.maghrib?:"",
                        body.data?.timings?.isha?:"",
                        body.data?.timings?.imsak?:"",
                        body.data?.timings?.midnight?:"",
                        body.data?.timings?.firstthird?:"",
                        body.data?.timings?.lastthird?:"",
                    )
                    emit(BaseResult.Success(prayerTime))
                }
            } else {
                val type = object : TypeToken<WrappedResponse<PrayerTimeResponse>>() {}.type
                val err: WrappedResponse<PrayerTimeResponse> = Gson().fromJson(response.errorBody()!!.charStream(), type)
                err.code = response.code()
                emit(BaseResult.Error(err))
            }
        }
    }
}