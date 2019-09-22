package com.github.lightverse.coton

import android.app.Application
import com.github.lightverse.baselib.BaseLibApplication

class CoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        BaseLibApplication.onCreate(this)
    }
}
