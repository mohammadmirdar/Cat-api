package com.mirdar.catapiapp.data.remote.common

import AppUtils
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject


class RequestInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        val newRequest = request.newBuilder()
        newRequest.header("x-api-key", AppUtils.API_KEY)
        val response = chain.proceed(newRequest.build())

        return response
    }
}