package com.example.todorealmsimple

import android.app.Application
import io.realm.Realm

class TodoSimple : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}