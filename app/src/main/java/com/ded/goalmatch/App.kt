package com.ded.goalmatch

import android.app.Application

class App:Application() {
    companion object{
        lateinit var api_video: ApiVideo
        var instance:App? = null

    }
    override fun onCreate() {
        super.onCreate()
        if (instance == null) {
            instance = this
        }

        api_video = RetrofitModule().createApiInfo()!!

    }
}