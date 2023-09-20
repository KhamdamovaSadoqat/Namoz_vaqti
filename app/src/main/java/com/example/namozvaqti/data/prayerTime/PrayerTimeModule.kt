package com.example.namozvaqti.data.prayerTime

import com.example.namozvaqti.data.comman.module.NetworkModule
import com.example.namozvaqti.utils.SharedPref
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class PrayerTimeModule {

        @Singleton
        @Provides
        fun provideLoginApi(retrofit: Retrofit): PrayerTimeApi {
            return retrofit.create(PrayerTimeApi::class.java)
        }

        @Singleton
        @Provides
        fun provideLoginRepository(prayerTime: PrayerTimeApi, pref: SharedPref): PrayerTimeRepositoryImp {
            return PrayerTimeRepositoryImp(pref, prayerTime)
        }
}