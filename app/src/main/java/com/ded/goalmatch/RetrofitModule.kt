package com.ded.goalmatch

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

class RetrofitModule {
    fun createClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS)
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.connectTimeout(30, TimeUnit.SECONDS)
        okHttpClient.writeTimeout(30, TimeUnit.SECONDS)
        okHttpClient.addInterceptor(
            object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): Response {
                    val request: Request.Builder = chain.request().newBuilder()
                    return chain.proceed(request.build())
                }
            }
        )
            .addInterceptor(logging)
        return okHttpClient.build()
    }



    fun createApiInfo(): ApiVideo? {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(Config.FOOTBALL_URL)
            .client(createClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ApiVideo::class.java)
    }
}