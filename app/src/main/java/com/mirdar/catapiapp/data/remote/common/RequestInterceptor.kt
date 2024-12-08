package com.mirdar.catapiapp.data.remote.common

import com.mirdar.catapiapp.AppUtils
import okhttp3.Interceptor
import okhttp3.Response


class RequestInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        val newRequest = request.newBuilder()
        newRequest.header("x-api-key", AppUtils.API_KEY)
        val response = chain.proceed(newRequest.build())

        return response
    }
}