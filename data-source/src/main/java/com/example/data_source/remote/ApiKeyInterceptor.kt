package com.example.data_source.remote

import com.example.utils.constants.Constants
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("x-api-key", Constants.API_KEY)
            .build()

        return chain.proceed(request)
    }

}