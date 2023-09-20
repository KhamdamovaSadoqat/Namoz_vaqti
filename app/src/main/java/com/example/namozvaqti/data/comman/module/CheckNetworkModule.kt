package com.example.namozvaqti.data.comman.module

import android.content.Context
import com.example.namozvaqti.utils.NetworkHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CheckNetworkModule {

    @Singleton
    @Provides
    fun provideCheckNetworkModule(@ApplicationContext context: Context): NetworkHelper {
        return NetworkHelper(context)
    }
}