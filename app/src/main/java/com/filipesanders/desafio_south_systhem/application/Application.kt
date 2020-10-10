package com.filipesanders.desafio_south_systhem.application

import android.app.Application
import com.filipesanders.desafio_south_systhem.services.RetrofitServices

class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        RetrofitServices.initRetrofit(this)

    }
}