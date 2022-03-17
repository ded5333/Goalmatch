package com.ded.goalmatch

import retrofit2.Call
import retrofit2.http.GET

interface ApiVideo {
    @GET("/video-api/v3/")
    fun getMatches(): Call<MainFootball>
}