package com.example.namozvaqti.ui.prayerTime

import com.example.namozvaqti.data.comman.WrappedResponse
import com.example.namozvaqti.data.prayerTime.PrayerTimeResponse
import com.example.namozvaqti.domain.base.BaseResult
import com.example.namozvaqti.domain.prayerTime.PrayerTimeEntity
import com.example.namozvaqti.domain.prayerTime.PrayerTimeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class PrayerTimeViewModel @Inject constructor(
    private val prayerTimeUseCase: PrayerTimeUseCase
) {

    private val prayerTimeState = MutableStateFlow<PrayerTimeState>(PrayerTimeState.Init)
    val mPrayerTimeState: StateFlow<PrayerTimeState> get() = prayerTimeState

    suspend fun prayerTime(){
        prayerTimeUseCase.prayerTime()
            .onStart { prayerTimeState.value = PrayerTimeState.IsLoading(true) }
            .catch {exception ->
                prayerTimeState.value = PrayerTimeState.IsLoading(false)
                prayerTimeState.value = PrayerTimeState.ShowToast(exception.cause?.message.toString())
            }
            .collect {result ->
                prayerTimeState.value = PrayerTimeState.IsLoading(false)
                when(result){
                    is BaseResult.Success -> {
                        prayerTimeState.value = PrayerTimeState.SuccessPrayerTime(result.data)
                    }
                    is BaseResult.Error -> {
                        prayerTimeState.value = PrayerTimeState.ErrorPrayerTime(result.rawResponse)
                    }
                }

            }
    }
}

sealed class PrayerTimeState {
    object Init : PrayerTimeState()
    data class IsLoading(val isLoading: Boolean) : PrayerTimeState()
    data class ShowToast(val message: String) : PrayerTimeState()
    data class SuccessPrayerTime(val prayerTimeEntity: PrayerTimeEntity) : PrayerTimeState()
    data class ErrorPrayerTime(val rawResponse: WrappedResponse<PrayerTimeResponse>) : PrayerTimeState()
}