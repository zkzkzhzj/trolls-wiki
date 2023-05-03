package com.trolls.mobile.application

import android.app.Application
import android.content.Context

class TrollsWikiApplication : Application() {
    init {
        instance = this
    }

    companion object {
        lateinit var instance: TrollsWikiApplication

        fun applicationContext(): Context {
            return instance.applicationContext
        }
    }
}
