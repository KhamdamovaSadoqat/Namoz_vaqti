package com.example.namozvaqti.data.comman

import com.example.namozvaqti.utils.SharedPref
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor constructor(private val pref: SharedPref) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = "Bearer "
        val newRequest = chain.request().newBuilder()
        if (token.isNotEmpty()) {
            newRequest.addHeader("Authorization", token)
        }
        return chain.proceed(newRequest.build())
    }
}